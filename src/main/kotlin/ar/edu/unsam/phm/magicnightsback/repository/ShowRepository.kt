package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.interfaces.CustomCrudRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShowRepository : CrudRepository<Show, Long>, CustomCrudRepository<Show> {
    @EntityGraph(
        attributePaths = [
            "band",
            "facility",
            "facility.places",
            "dates",
            "dates.reservedSeats",
            "dates.attendees",
            "pendingAttendeesIds"
        ]
    )
    override fun findById(id: Long): Optional<Show>

    @EntityGraph(
        attributePaths = [
            "band",
            "facility",
            "facility.places",
            "dates",
            "dates.attendees"
        ]
    )
    override fun findAll(): MutableIterable<Show>

    @Query (
        """SELECT s.facility.id
        FROM Show s
        JOIN s.dates sd
        WHERE sd.date < CURRENT_TIMESTAMP
        GROUP BY s.facility.id
        HAVING COUNT(DISTINCT s.id) >= 2"""
    )
    fun busyFacilities(): MutableIterable<Long>
}
