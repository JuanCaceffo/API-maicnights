package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

interface FacilityRepository : CrudRepository<Facility, Long>{
    fun getByName(name: String): Optional<Facility>
}