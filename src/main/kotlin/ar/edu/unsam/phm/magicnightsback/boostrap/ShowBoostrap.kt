//package ar.edu.unsam.phm.magicnightsback.boostrap
//
//import ar.edu.unsam.phm.magicnightsback.domain.AllSetTypeNames
//import ar.edu.unsam.phm.magicnightsback.domain.Comment
//import ar.edu.unsam.phm.magicnightsback.domain.Show
//import ar.edu.unsam.phm.magicnightsback.domain.TheaterSeatType
//import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
//import org.springframework.beans.factory.InitializingBean
//import org.springframework.context.annotation.DependsOn
//import org.springframework.core.annotation.Order
//import org.springframework.stereotype.Component
//import org.springframework.stereotype.Service
//import java.time.LocalDate
//import java.time.LocalDateTime
//
//@Service
//@Order(2)
//@DependsOn("facilityBoostrap", "bandBoostrap")
//
//class ShowBoostrap(
//    val showRepository: ShowRepository,
//    bandBoostrap: BandBoostrap,
//    facilityBoostrap: FacilityBoostrap,
//) : InitializingBean {
//
//    val shows = mapOf(
//        "LaVelaPuerca_GranRex" to Show().apply {
//            name = "Cachenged!!"
//            band = bandBoostrap.bands["LaVelaPuerca"]
//            facility = facilityBoostrap.facilities["GranRex"]
//        },
//        "LaVelaPuerca_TeatroColon" to Show().apply {
//            name = "Bailanta!!"
//            band = bandBoostrap.bands["LaVelaPuerca"]
//            facility = facilityBoostrap.facilities["TeatroColon"]
//        },
//        "PearlJam_River" to Show().apply {
//            name = "4 You"
//            band = bandBoostrap.bands["PearlJam"]
//            facility = facilityBoostrap.facilities["River"]
//        },
//        "PearlJam_LaBombonera" to Show().apply {
//            name = "4 You"
//            band = bandBoostrap.bands["PearlJam"]
//            facility = facilityBoostrap.facilities["LaBombonera"]
//        },
//        "AcDc_MovistarArena" to Show().apply {
//            name = "Demon of Hell Rise Tour"
//            band = bandBoostrap.bands["AcDc"]
//            facility = facilityBoostrap.facilities["MovistarArena"]
//        },
//        "AcDc_TeatroOpera" to Show().apply {
//            name = "Demon of Hell Rise Tour"
//            band = bandBoostrap.bands["AcDc"]
//            facility = facilityBoostrap.facilities["TeatroOpera"]
//        },
//        "LosRedondos_ClubDePolo" to Show().apply {
//            name = "De ricota"
//            band = bandBoostrap.bands["LosRedondos"]
//            facility = facilityBoostrap.facilities["ClubDePolo"]
//        },
//        "OneDirection_LunaPark" to Show().apply {
//            name = "nameMidnight"
//            band = bandBoostrap.bands["OneDirection"]
//            facility = facilityBoostrap.facilities["LunaPark"]
//        },
//        "Queen_GranRex" to Show().apply {
//            name = "Love of my life"
//            band = bandBoostrap.bands["Queen"]
//            facility = facilityBoostrap.facilities["GranRex"]
//        },
//        "LaVelaPuerca_SmallFacility" to Show().apply {
//            name = "Arriba!"
//            band = bandBoostrap.bands["LaVelaPuerca"]
//            facility = facilityBoostrap.facilities["smallFacility"]
//        }
//    )
//
//
//    fun createShows() {
//        shows.values.forEach{
//            val showInRepo = showRepository.getByName(it.name)
//            if (showInRepo.isPresent) {
//                it.id = showInRepo.get().id
//            } else {
//                showRepository.save(it)
//            }
//        }
//    }
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
////    }
//
//
//    override fun afterPropertiesSet() {
//        println("Show creation process starts")
//        createShows()
////        createShowDates()
////        addAttendees()
//    }
//}
