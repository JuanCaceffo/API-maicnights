package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Order(2)
@DependsOn("facilityBoostrap","bandBoostrap")
class ShowBoostrap(
    val showRepository: ShowRepository,
    bandRepository: BandRepository,
    facilityRepository: FacilityRepository
) : InitializingBean {

    private val shows = mapOf(
        "showVP" to Show("Show de la Vela Puerca", bandRepository.getById(0), facilityRepository.getById(0))
    )
    fun createShows() {
        shows.values.forEach { showRepository.create(it) } }

    fun createShowDate() {
        shows["showVP"]!!.addDate(LocalDateTime.now().plusDays(12))
    }

    override fun afterPropertiesSet() {
        println("Show creation process starts")
        createShows()
        createShowDate()
        println("Show creation process ends")
    }
}
