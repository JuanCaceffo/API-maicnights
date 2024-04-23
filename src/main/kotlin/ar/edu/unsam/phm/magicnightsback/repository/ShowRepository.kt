package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.interfaces.CustomCrudRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShowRepository : CrudRepository<Show, Long>, CustomCrudRepository<Show> {
    @EntityGraph(attributePaths = ["facility", "band", "facility.places", "dates", "dates.reservedSeats", "dates.attendees", "pendingAttendeesIds"])
    override fun findById(id: Long): Optional<Show>

    @EntityGraph(attributePaths = ["facility", "band", "facility.places", "dates", "dates.attendees"])
    override fun findAll(): MutableIterable<Show>
}
