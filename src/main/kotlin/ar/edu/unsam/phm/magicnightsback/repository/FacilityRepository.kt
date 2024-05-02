package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import ar.edu.unsam.phm.magicnightsback.interfaces.CustomCrudRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface FacilityRepository : CrudRepository<Facility, Long>, CustomCrudRepository<Facility> {
    @EntityGraph(attributePaths = ["places"])
    override fun findById(id: Long): Optional<Facility>

    @EntityGraph(attributePaths = ["places"])
    override fun findAll(): Iterable<Facility>
}