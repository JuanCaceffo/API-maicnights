package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.interfaces.CustomCrudRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShowRepository : CrudRepository<Show, Long>, CustomCrudRepository<Show> {
    @EntityGraph(attributePaths = ["facility", "band", "facility.places", "facility.places.seat"])
    override fun findById(id: Long): Optional<Show>

    @EntityGraph(attributePaths = ["facility", "band", "facility.places", "facility.places.seat"])
    override fun findAll(): MutableIterable<Show>
}