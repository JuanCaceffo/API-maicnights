package ar.edu.unsam.phm.magicnightsback.repository


import ar.edu.unsam.phm.magicnightsback.domain.Place
import org.springframework.data.repository.CrudRepository

interface PlaceRepository : CrudRepository<Place, Long>