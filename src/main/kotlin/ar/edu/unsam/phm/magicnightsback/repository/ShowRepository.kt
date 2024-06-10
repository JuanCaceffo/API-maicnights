package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Show
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ShowRepository : MongoRepository<Show, String> {
    override fun findById(id: String): Optional<Show>
    override fun findAll(): MutableList<Show>
    fun findByName(name: String): Optional<Show>

//    @Query("""
//        SELECT
//            SD.id
//        FROM ShowDate SD
//        WHERE SD.show.id = :id
//    """)
//    fun showDateIdsByShowId(@Param("id") id: Long): Iterable<Long>

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
