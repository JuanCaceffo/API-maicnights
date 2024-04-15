package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface FacilityRepository : CrudRepository<Facility, Long>{
    fun findByName(name: String): Optional<Facility>
}