package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface TicketRepository : CrudRepository<Ticket, Long> {
    //    fun findByDateIs(date: LocalDateTime): Optional<Ticket>
    @EntityGraph(
        attributePaths = [
            "showDate",
            "showDate.show",
            "showDate.show.facility",
            "showDate.show.facility.seats",
            "showDate.show.band"
        ]
    )
    fun findByUserId(userId: Long): Iterable<Ticket>

    fun findByShowDateShowId(showId: Long): Iterable<Ticket>


    @Query(
        """
            SELECT COUNT(DISTINCT tk.user.id) 
                FROM Ticket tk
                INNER JOIN tk.showDate sd
                WHERE sd.show.id = :showId
                AND tk.user.id IN (
                    SELECT f.id
                    FROM User u
                    INNER JOIN u.friends f
                    WHERE u.id = :userId
                )
        """
    )
    fun countFriendsByShow(
        @Param("showId") showId: Long,
        @Param("userId") userId: Long
    ): Optional<Int>

    @Query(
        """
            SELECT
                TK.user.profileImgUrl AS user_images
                FROM Ticket TK
                INNER JOIN TK.showDate sd
                WHERE sd.show.id = :showId
                AND TK.user.id IN (
                    SELECT f.id
                    FROM User U
                    INNER JOIN U.friends f
                    WHERE U.id = :userId
                )
                ORDER BY user_images ASC
                LIMIT 3
        """
    )
    fun getTopFriendsImages(
        @Param("showId") showId: Long,
        @Param("userId") userId: Long
    ): Iterable<String>

    @Query(
        """
            SELECT 
                SUM(T.price) 
                FROM Ticket T
                INNER JOIN ShowDate SD
                ON SD.id = T.showDate.id
                WHERE SD.show.id = :showId
        """
    )
    fun totalShowSales(@Param("showId") showId: Long): Optional<Double>

    @Query("""
       SELECT 
        COUNT(*) AS taken_capacity
        FROM Ticket TK        
        WHERE TK.showDate.id = :id        
    """)
    fun showDateTakenCapacity(@Param("id") id:Long): Int

////    fun findByUserIdAndStatusIs(userId: UUID, status: TicketStatus): Iterable<Ticket>

}