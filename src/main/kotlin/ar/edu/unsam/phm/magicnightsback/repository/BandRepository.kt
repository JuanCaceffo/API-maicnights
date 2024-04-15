package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Band
import org.springframework.data.repository.CrudRepository
import java.util.*

interface BandRepository : CrudRepository<Band, Long> {
    fun findByName(name: String): Optional<Band>
}
