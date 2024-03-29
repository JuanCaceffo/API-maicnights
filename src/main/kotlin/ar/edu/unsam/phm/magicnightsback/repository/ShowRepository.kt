package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Show
import org.springframework.stereotype.Repository

@Repository
class ShowRepository : CustomRepository<Show>() {
    fun getAvailable(): List<Show> {
        return elements.values.filter { it.hasAvailableDates() }
    }
}