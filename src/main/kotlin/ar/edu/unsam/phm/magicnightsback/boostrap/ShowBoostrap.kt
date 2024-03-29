package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
@DependsOn("bandBoostrap","facilityBoostrap")
class ShowBoostrap (
    val showRepository: ShowRepository,
    bandRepository: BandRepository,
    facilityRepository: FacilityRepository
) : InitializingBean {
    private val shows = mapOf(
        "SmallShow" to Show("SmallShow",bandRepository.getById(0),facilityRepository.getById(0)),
        "BigShow" to Show("BigShow",bandRepository.getById(1),facilityRepository.getById(1)),
        "BestSmallShow" to Show("BestSmallShow",bandRepository.getById(2),facilityRepository.getById(2)),
    )

    fun createShows() {
        shows.values.forEach { showRepository.apply { create(it) } }
    }

    override fun afterPropertiesSet() {
        println("Show creation process starts")
        createShows()
        println("Show creation process ends")
    }
}