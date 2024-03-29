package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Band
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Order(1)
class ShowBoostrap(
    val showRepository: ShowRepository,
    val facilityRepository: FacilityRepository
) : InitializingBean {

    private val bands = listOf(
        Band("La Vela Puerca",10000.0),

    )
    private val shows = listOf(
        Show("Show de la Vela Puerca", bands[0], facilityRepository.getById(0))
    )
    fun createShows() {
        shows.forEach { show -> showRepository.create(show) } }

    override fun afterPropertiesSet() {
        createShows()
    }
}
