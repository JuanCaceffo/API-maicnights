package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Order(2)
@DependsOn("facilityBoostrap", "bandBoostrap")

class ShowBoostrap(
    val showRepository: ShowRepository,
    bandBoostrap: BandBoostrap,
    facilityBoostrap: FacilityBoostrap
) : InitializingBean {

    val shows = mapOf(
        "SmallShow" to Show(
            "Cachenged!!",
            bandBoostrap.bands["LaVelaPuerca"]!!,
            facilityBoostrap.facilities["GranRex"]!!
        ),
        "BigShow" to Show("4 You", bandBoostrap.bands["PearlJam"]!!, facilityBoostrap.facilities["River"]!!),
        "BestSmallShow" to Show(
            "Demon of Hell Rise Tour",
            bandBoostrap.bands["AcDc"]!!,
            facilityBoostrap.facilities["MovistarArena"]!!
        ),
    )

    fun createShows() {
        shows.values.forEach { showRepository.apply { create(it) } }
    }

    fun createShowDates() {
        val generalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231")
        shows["SmallShow"]!!.apply {
            repeat(5) { addDate(generalDateTime.plusDays(11 + it.toLong())) }
        }
        shows["BigShow"]!!.apply {
            addDate(generalDateTime.minusDays(3))
            repeat(3) { addDate(generalDateTime.plusDays(11 + it.toLong())) }
        }
        shows["BestSmallShow"]!!.apply {
            repeat(2) { addDate(generalDateTime.minusDays(3 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(11 + it.toLong())) }
        }
    }

    override fun afterPropertiesSet() {
        println("Show creation process starts")
        createShows()
        createShowDates()
        println("Show creation process ends")
    }
}
