package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.interfaces.CustomCrudRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
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

    @EntityGraph(
        attributePaths = [
            "band",
            "facility",
            "facility.seats"
        ]
    )
    override fun findAll(): Iterable<Show>

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
