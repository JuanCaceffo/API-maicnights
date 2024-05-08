package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.dto.TicketResult
import ar.edu.unsam.phm.magicnightsback.dto.UserBalanceDTO
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @EntityGraph(attributePaths = ["tickets", "friends", "balances"])
    fun findByUsername(username: String): Optional<User>

    @EntityGraph(attributePaths = [
        "tickets",
        "tickets.show",
        "tickets.show.band",
        "tickets.show.facility",
        "tickets.show.dates",
        "tickets.show.dates.attendees",
        "friends", "balances"
    ])
    override fun findById(id: Long): Optional<User>

    @Query("""
        SELECT u
        FROM User u
        WHERE u.id IN (
            SELECT u2.id
            FROM User u2
            JOIN u2.tickets t
            GROUP BY u2.id
            HAVING COUNT(t) > :ticketsQuantity
        )
    """)
    fun findUsersWithMoreTicketsThan(ticketsQuantity: Int): Iterable<User>

    @Query(
        """SELECT
            u.id AS id,
            bh.amount AS amount,
            bh.timeStamp AS timeStamp
        FROM User u
        INNER JOIN u.balances bh
        WHERE bh.user.id = :id
        ORDER BY bh.id DESC  
        LIMIT 2
        """
    )
    fun allBalances(@Param("id") id: Long): List<UserBalanceDTO>

    @Query(nativeQuery = true, value = "SELECT * FROM public.history_tickets(:userId,:year)")
    fun historyTickets(@Param("userId") userId: Long, @Param("year") year: Int): List<TicketResult>

    @Query("""
    SELECT ti.quantity AS quantity,
     ti.id AS ticketId,
    ti.show_date_id AS showDateId, 
    ti.show_id AS showId, 
    ti.seat AS seat
    FROM public.ticket AS ti
    JOIN public.spectator_tickets AS st ON st.tickets_id = ti.id
    JOIN public.spectator AS s ON s.id = st.user_id
    JOIN public.show_date AS sd ON sd.id = ti.show_date_id
    WHERE s.id = :userId
    """, nativeQuery = true)
    fun getTickets(@Param("userId") userId:Long): List<TicketResult>
}
