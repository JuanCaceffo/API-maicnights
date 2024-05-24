package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface TicketRepository : CrudRepository<Ticket, Long> {
    //    fun findByDateIs(date: LocalDateTime): Optional<Ticket>
    fun findByUserId(userId: Long): Iterable<Ticket>

    fun findByShowId(showId: String): Iterable<Ticket>

    @Query(
        """
            SELECT COUNT(DISTINCT tk.user.id) 
                FROM Ticket tk
                WHERE tk.showId = :showId
                AND tk.user.id IN (
                    SELECT f.id
                    FROM User u
                    INNER JOIN u.friends f
                    WHERE u.id = :userId
                )
        """
    )
    fun countFriendsByShow(
        @Param("showId") showId: String,
        @Param("userId") userId: Long
    ): Optional<Int>

    @Query(
        """
            SELECT
                TK.user.profileImgUrl AS user_images
                FROM Ticket TK
                WHERE TK.showId = :showId
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
        @Param("showId") showId: String,
        @Param("userId") userId: Long
    ): Iterable<String>

    @Query(
        """
            SELECT 
                SUM(T.price) 
                FROM Ticket T
                WHERE T.showId = :showId
        """
    )
    fun totalShowSales(@Param("showId") showId: String): Optional<Double>

    @Query("""
       SELECT 
        COUNT(*) AS taken_capacity
        FROM Ticket TK        
        WHERE TK.showDateId = :id        
    """)
    fun showDateTakenCapacity(@Param("id") id:String): Int


    @Query("""
       SELECT 
        COUNT(*) AS taken_capacity
        FROM Ticket TK        
        WHERE TK.showId = :id        
    """)
    fun showTakenCapacity(@Param("id") id:Long): Optional<Int>

    @Query("""
       SELECT 
        COUNT(*) AS taken_capacity
        FROM Ticket TK
        WHERE TK.showId = :showId AND TK.seat.id = :seatId       
    """)
    fun showTakenCapacitybySeatId(@Param("showId") showId:Long,@Param("seatId") seatId:Long): Optional<Int>

////    fun findByUserIdAndStatusIs(userId: UUID, status: TicketStatus): Iterable<Ticket>
//    fun countBySeatAndShowDateId(seat:SeatTypes, showDateId: String) : Int
}
