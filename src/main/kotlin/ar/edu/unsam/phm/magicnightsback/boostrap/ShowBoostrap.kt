package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
@Order(2)
@DependsOn("facilityBoostrap", "bandBoostrap")

class ShowBoostrap(
    val showRepository: ShowRepository,
    bandBoostrap: BandBoostrap,
    facilityBoostrap: FacilityBoostrap,
) : InitializingBean {

    val shows = mapOf(
        "LaVelaPuerca_GranRex" to Show(
            "Cachenged!!",
            bandBoostrap.bands["LaVelaPuerca"]!!,
            facilityBoostrap.facilities["GranRex"]!!
        ),
        "LaVelaPuerca_TeatroColon" to Show(
            "Cachenged!!",
            bandBoostrap.bands["LaVelaPuerca"]!!,
            facilityBoostrap.facilities["TeatroColon"]!!
        ),
        "PearlJam_River" to Show(
            "4 You",
            bandBoostrap.bands["PearlJam"]!!,
            facilityBoostrap.facilities["River"]!!
        ),
        "PearlJam_LaBombonera" to Show(
            "4 You",
            bandBoostrap.bands["PearlJam"]!!,
            facilityBoostrap.facilities["LaBombonera"]!!
        ),
        "AcDc_MovistarArena" to Show(
            "Demon of Hell Rise Tour",
            bandBoostrap.bands["AcDc"]!!,
            facilityBoostrap.facilities["MovistarArena"]!!
        ),
        "AcDc_TeatroOpera" to Show(
            "Demon of Hell Rise Tour",
            bandBoostrap.bands["AcDc"]!!,
            facilityBoostrap.facilities["TeatroOpera"]!!
        ),
        "LosRedondos_HipodromoDePalermo" to Show(
            "De ricota",
            bandBoostrap.bands["LosRedondos"]!!,
            facilityBoostrap.facilities["ClubDePolo"]!!
        ),
        "OneDirection_LunaPark" to Show(
            "Midnight",
            bandBoostrap.bands["OneDirection"]!!,
            facilityBoostrap.facilities["LunaPark"]!!
        ),
        "Queen_GranRex" to Show(
            "Love of my life",
            bandBoostrap.bands["Queen"]!!,
            facilityBoostrap.facilities["GranRex"]!!
        )
    )


    fun createShows() {
        shows.values.forEach { showRepository.apply { create(it) } }
    }

    fun createShowDates() {
        val generalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231")
        shows["LaVelaPuerca_GranRex"]!!.apply {
            repeat(2) { addDate(generalDateTime.minusDays(3 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.minusDays(11 + it.toLong())) }
        }
        shows["LaVelaPuerca_TeatroColon"]!!.apply {
            repeat(2) { addDate(generalDateTime.minusDays(5 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(14 + it.toLong())) }
        }
        shows["PearlJam_River"]!!.apply {
            repeat(1) { addDate(generalDateTime.minusDays(7 + it.toLong())) }
            repeat(1) { addDate(generalDateTime.plusDays(10 + it.toLong())) }
        }
        shows["PearlJam_LaBombonera"]!!.apply {
            repeat(3) { addDate(generalDateTime.minusDays(10 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(12 + it.toLong())) }
        }
        shows["AcDc_MovistarArena"]!!.apply {
            repeat(2) { addDate(generalDateTime.minusDays(2 + it.toLong())) }
            repeat(1) { addDate(generalDateTime.plusDays(13 + it.toLong())) }
        }
        shows["AcDc_TeatroOpera"]!!.apply {
            repeat(2) { addDate(generalDateTime.minusDays(4 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(15 + it.toLong())) }
        }
        shows["LosRedondos_HipodromoDePalermo"]!!.apply {
            repeat(1) { addDate(generalDateTime.minusDays(8 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(9 + it.toLong())) }
        }
        shows["OneDirection_LunaPark"]!!.apply {
            repeat(2) { addDate(generalDateTime.minusDays(6 + it.toLong())) }
            repeat(4) { addDate(generalDateTime.plusDays(16 + it.toLong())) }
        }
        shows["Queen_GranRex"]!!.apply {
            repeat(2) { addDate(generalDateTime.minusDays(1 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.minusDays(17 + it.toLong())) }
        }
    }


    override fun afterPropertiesSet() {
        println("Show creation process starts")
        createShows()
        createShowDates()
        println("Show creation process ends")
    }
}
