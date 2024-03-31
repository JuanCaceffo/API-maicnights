package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(2)
@DependsOn("facilityBoostrap", "bandBoostrap")

class ShowBoostrap(
    val showRepository: ShowRepository,
    bandBoostrap: BandBoostrap,
    facilityBoostrap: FacilityBoostrap
) : InitializingBean {

    val shows = mapOf(
        "SmallShow" to Show("SmallShow",bandBoostrap.bands["LaVelaPuerca"]!!,facilityBoostrap.facilities["GranRex"]!!),
        "BigShow" to Show("BigShow",bandBoostrap.bands["PearlJam"]!!,facilityBoostrap.facilities["River"]!!),
        "BestSmallShow" to Show("BestSmallShow",bandBoostrap.bands["AcDc"]!!,facilityBoostrap.facilities["Boca"]!!),
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
