package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import ar.edu.unsam.phm.magicnightsback.data.interfaces.CustomCrudRepository
import org.springframework.data.repository.CrudRepository

interface FacilityRepository : CrudRepository<Facility, Long>, CustomCrudRepository<Facility>