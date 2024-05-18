package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import ar.edu.unsam.phm.magicnightsback.domain.interfaces.CustomCrudRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository

interface FacilityRepository : CrudRepository<Facility, Long>, CustomCrudRepository<Facility> {
    @EntityGraph(attributePaths = ["seats"])
    override fun findByNameIsContainingIgnoreCase(name: String): Iterable<Facility>
}