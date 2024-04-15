package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Show
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ShowRepository : CrudRepository<Show, Long>{
    fun findByName(name: String): Optional<Show>
}