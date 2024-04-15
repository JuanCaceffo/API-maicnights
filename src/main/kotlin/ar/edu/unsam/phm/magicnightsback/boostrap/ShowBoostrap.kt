package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ShowBoostrap(
    @Autowired
    bandRepository: BandRepository,
    @Autowired
    facilityRepository: FacilityRepository,
    @Autowired
    bandBoostrap: BandBoostrap,
    @Autowired
    facilityBoostrap: FacilityBoostrap
) : InitializingBean {

    @Autowired
    lateinit var showRepository: ShowRepository


    val shows = mapOf(
        "LaVelaPuerca_GranRex" to Show(name = "Cachenged!!").apply {
            band = bandRepository.findByName("La Vela Puerca").getOrNull()
            facility = facilityRepository.findByName("Gran Rex").getOrNull()
        },
        "LaVelaPuerca_TeatroColon" to Show(name = "Bailanta!!").apply {
            band = bandRepository.findByName("La Vela Puerca").getOrNull()
            facility = facilityRepository.findByName("Teatro Colon").getOrNull()
        },
        "PearlJam_River" to Show(name = "4 You").apply {
            band = bandRepository.findByName("Pearl Jam").getOrNull()
            facility = facilityRepository.findByName("River Plate").getOrNull()
        },
        "PearlJam_LaBombonera" to Show(name = "4 You").apply {
            band = bandRepository.findByName("Pearl Jam").getOrNull()
            facility = facilityRepository.findByName("La Bombonera").getOrNull()
        },
        "AcDc_MovistarArena" to Show(name = "Demon of Hell Rise Tour").apply {
            band = bandRepository.findByName("Ac/Dc").getOrNull()
            facility = facilityRepository.findByName("Movistar Arena").getOrNull()
        },
        "AcDc_TeatroOpera" to Show(name = "Demon of Hell Rise Tour").apply {

            band = bandRepository.findByName("Ac/Dc").getOrNull()
            facility = facilityRepository.findByName("Teatro Opera").getOrNull()
        },
        "LosRedondos_ClubDePolo" to Show(name = "Le ricote").apply {
            band = bandRepository.findByName("Los Redondos").getOrNull()
            facility = facilityRepository.findByName("Club De Polo").getOrNull()
        },
        "OneDirection_LunaPark" to Show(name = "nameMidnight").apply {
            band = bandRepository.findByName("One Direction").getOrNull()
            facility = facilityRepository.findByName("Luna Park").getOrNull()
        },
        "Queen_GranRex" to Show(name = "Love of my life").apply {
            band = bandRepository.findByName("Queen").getOrNull()
            facility = facilityRepository.findByName("Gran Rex").getOrNull()
        },
        "LaVelaPuerca_SmallFacility" to Show(name = "Arriba!").apply {
            band = bandRepository.findByName("La Vela Puerca").getOrNull()
            facility = facilityRepository.findByName("Teatro Poker").getOrNull()
        }
    )

    fun createShows() {
        shows.values.forEach {
            it.validate()
            val showInRepo = showRepository.findByName(it.name).getOrNull()
            if (showInRepo != null) {
                it.id = showInRepo.id
            } else {
                showRepository.save(it)
                println("Show ${it.name} created")
            }
        }
    }

    @DependsOn("bandBoostrap", "facilityBoostrap")
    override fun afterPropertiesSet() {
        println("Show creation process starts")
        createShows()
//        createShowDates()
//        addAttendees()
    }
}


//

//
////    fun createShowDates() {
////        val generalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231")
////        shows["LaVelaPuerca_GranRex"]!!.apply {
////            repeat(2) { addDate(generalDateTime.minusDays(3 + it.toLong())) }
////            repeat(3) { addDate(generalDateTime.minusDays(11 + it.toLong())) }
////        }
////        shows["LaVelaPuerca_TeatroColon"]!!.apply {
////            repeat(2) { addDate(generalDateTime.minusDays(5 + it.toLong())) }
////            repeat(3) { addDate(generalDateTime.plusDays(14 + it.toLong())) }
////        }
////        shows["PearlJam_River"]!!.apply {
////            repeat(1) { addDate(generalDateTime.minusDays(7 + it.toLong())) }
////            repeat(1) { addDate(generalDateTime.plusDays(10 + it.toLong())) }
////        }
////        shows["PearlJam_LaBombonera"]!!.apply {
////            repeat(3) { addDate(generalDateTime.minusDays(10 + it.toLong())) }
////            repeat(3) { addDate(generalDateTime.plusDays(12 + it.toLong())) }
////        }
////        shows["AcDc_MovistarArena"]!!.apply {
////            repeat(2) { addDate(generalDateTime.minusDays(2 + it.toLong())) }
////            repeat(1) { addDate(generalDateTime.plusDays(13 + it.toLong())) }
////        }
////        shows["AcDc_TeatroOpera"]!!.apply {
////            repeat(2) { addDate(generalDateTime.minusDays(4 + it.toLong())) }
////            repeat(3) { addDate(generalDateTime.plusDays(15 + it.toLong())) }
////        }
////        shows["LosRedondos_ClubDePolo"]!!.apply {
////            repeat(1) { addDate(generalDateTime.minusDays(8 + it.toLong())) }
////            repeat(3) { addDate(generalDateTime.plusDays(9 + it.toLong())) }
////        }
////        shows["OneDirection_LunaPark"]!!.apply {
////            repeat(2) { addDate(generalDateTime.minusDays(6 + it.toLong())) }
////            repeat(4) { addDate(generalDateTime.plusDays(16 + it.toLong())) }
////        }
////        shows["Queen_GranRex"]!!.apply {
////            repeat(2) { addDate(generalDateTime.minusDays(1 + it.toLong())) }
////            repeat(3) { addDate(generalDateTime.minusDays(17 + it.toLong())) }
////        }
////        shows["LaVelaPuerca_SmallFacility"]!!.apply {
////            repeat(1) { addDate(generalDateTime.minusDays(1 + it.toLong())) }
////            repeat(1) { addDate(generalDateTime.plusDays(17 + it.toLong())) }
////        }
////    }
////
////    fun addAttendees() {
////        shows["LaVelaPuerca_SmallFacility"]!!.apply {
////            dates.first().apply{
////                reservedSeats[AllSetTypeNames.PULLMAN.name] = 100
////                reservedSeats[AllSetTypeNames.LOWERLEVEL.name] = 50
////            }
////            dates.last().apply{
////                reservedSeats[AllSetTypeNames.PULLMAN.name] = 500
////                reservedSeats[AllSetTypeNames.LOWERLEVEL.name] = 300
////            }
////        }
//    }

