package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.data.interfaces.CustomCrudRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface ShowRepository : CrudRepository<Show, Long>, CustomCrudRepository<Show> {

    @EntityGraph(
        attributePaths = [
            "band",
            "facility",
            "facility.seats"
        ]
    )
    override fun findById(id: Long): Optional<Show>

    @Query("""
        SELECT S
        FROM Show S
        INNER JOIN S.band B
        INNER JOIN S.facility F
        INNER JOIN ShowDate SD
        LEFT JOIN Ticket TK
    """)
    fun filterShows(
        @Param("bandName") bandName: String?,
        @Param("facilityName") facilityName: String?
    ): Iterable<Show>

//    @Query("SELECT s FROM Show s JOIN s.band b JOIN s.facility f LEFT JOIN s.tickets t WHERE " +
//            "(LOWER(b.name) LIKE LOWER(CONCAT('%', :bandName, '%')) OR :bandName IS NULL OR :bandName = '') " +
//            "AND (LOWER(f.name) LIKE LOWER(CONCAT('%', :facilityName, '%')) OR :facilityName IS NULL OR :facilityName = '') " +
//            "AND (:userList IS NULL OR :userList = '' OR t.user.id IN :userList)")
//    fun getShows(@Param("bandName") bandName: String, @Param("facilityName") facilityName: String, @Param("userList") userList: List<Int>): List<Show>

//
//    @EntityGraph(
//        attributePaths = [
//            "band",
//            "facility",
//            "facility.places",
//            "dates",
//            "dates.attendees"
//        ]
//    )
//    override fun findAll(): MutableIterable<Show>
//
//    @Query (
//        """SELECT s.facility.id
//        FROM Show s
//        JOIN s.dates sd
//        WHERE sd.date < CURRENT_TIMESTAMP
//        GROUP BY s.facility.id
//        HAVING COUNT(DISTINCT s.id) >= 2"""
//    )
//    fun busyFacilities(): MutableIterable<Long>
}
