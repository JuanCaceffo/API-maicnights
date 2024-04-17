package ar.edu.unsam.phm.magicnightsback.repository


import ar.edu.unsam.phm.magicnightsback.domain.Place
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PlaceRepository : CrudRepository<Place, Long> {

    @EntityGraph(attributePaths = ["seat"])
    override fun findAll(): Iterable<Place>

    @EntityGraph(attributePaths = ["seat"])
    override fun findById(id: Long): Optional<Place>
}