package ar.edu.unsam.phm.magicnightsback.repository


import ar.edu.unsam.phm.magicnightsback.domain.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @EntityGraph(attributePaths = ["friends"])
    override fun findById(id: Long): Optional<User>

    @EntityGraph(attributePaths = ["friends"])
    fun findByUsername(username: String): Optional<User>

    @EntityGraph(attributePaths = ["friends"])
    fun findByUsernameAndPassword(username: String, password: String): Optional<User>


    //    @EntityGraph(attributePaths = [
//        "tickets",
//        "tickets.show",
//        "tickets.show.band",
//        "tickets.show.facility",
//        "tickets.show.dates",
//        "tickets.show.dates.attendees",
//        "friends", "balances"
//    ])
//    override fun findById(id: Long): Optional<User>
//
//    @Query("""
//        SELECT u
//        FROM User u
//        WHERE u.id IN (
//            SELECT u2.id
//            FROM User u2
//            JOIN u2.tickets t
//            GROUP BY u2.id
//            HAVING COUNT(t) > :ticketsQuantity
//        )
//    """)
//    fun findUsersWithMoreTicketsThan(ticketsQuantity: Int): Iterable<User>
//
//    @Query(
//        """SELECT
//            u.id AS id,
//            bh.amount AS amount,
//            bh.timeStamp AS timeStamp
//        FROM User u
//        INNER JOIN u.balances bh
//        WHERE bh.user.id = :id
//        ORDER BY bh.id DESC
//        LIMIT 2
//        """
//    )
//    fun allBalances(@Param("id") id: Long): List<UserBalanceDTO>
//
//    @Query(nativeQuery = true, value = "SELECT * FROM public.history_tickets(:userId,:year)")
//    fun historyTickets(@Param("userId") userId: Long, @Param("year") year: Int): List<TicketResult>
//
//
//    @Query("""
//        SELECT ti
//        FROM User u
//        INNER JOIN u.tickets ti
//        WHERE u.id = :userId
//    """)
//    fun getTickets(@Param("userId") userId:Long): List<Ticket>
//
    fun existsUserById(userId: Long): Boolean
}
