package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Band
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.interfaces.CustomCrudRepository
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BandRepository : CrudRepository<Band, Long>, CustomCrudRepository<Band>
