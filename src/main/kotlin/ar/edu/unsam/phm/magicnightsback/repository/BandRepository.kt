package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.data.interfaces.CustomCrudRepository
import ar.edu.unsam.phm.magicnightsback.domain.Band
import org.springframework.data.repository.CrudRepository

interface BandRepository : CrudRepository<Band, Long>, CustomCrudRepository<Band>
