package ar.edu.unsam.phm.magicnightsback.boostrap


import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.PlaceRepository
import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class FacilityBootstrap(
    @Autowired
    seatRepository: SeatRepository,
    @Autowired
    seatBootstrap: SeatBootstrap
) : InitializingBean {
    @Autowired
    lateinit var facilityRepository: FacilityRepository

    @Autowired
    lateinit var placeRepository: PlaceRepository



    val pullman = seatRepository.findByName(SeatTypes.PULLMAN.name).get()
    val upperLevel = seatRepository.findByName(SeatTypes.UPPERLEVEL.name).get()
    val lowerLevel = seatRepository.findByName(SeatTypes.LOWERLEVEL.name).get()
    val box = seatRepository.findByName(SeatTypes.BOX.name).get()
    val field = seatRepository.findByName(SeatTypes.FIELD.name).get()

    val Place1 = Place(pullman, 2000)
    val Place2 = Place(lowerLevel, 3000)


    val facilities = mapOf(
        "GranRex" to Theater(
            name = "Gran Rex",
            location = Point(latitude = -34.603542, longitude = -58.378856)
        ).apply {
            hasGoodAcoustics = true
        },
//        "River" to Stadium(
//            name = "River Plate",
//            location = Point(latitude = -34.54612, longitude = -58.45004),
//            1000000.0
//        ).apply {
//            addPlace(upperLevel, 20000)
//            addPlace(field, 15000)
//            addPlace(box, 35000)
//        },
//        "MovistarArena" to Theater(
//            name = "Movistar Arena",
//            location = Point(latitude = -34.63579, longitude = -58.36527)
//        ).apply {
//            addPlace(lowerLevel, 1000)
//            addPlace(pullman, 2000)
//        },
//        "TeatroColon" to Theater(
//            name = "Teatro Colon",
//            location = Point(latitude = -34.60188, longitude = -58.38336)
//        ).apply {
//            addPlace(pullman, 400)
//            addPlace(lowerLevel, 3000)
//        },
//        "LunaPark" to Stadium(
//            name = "Luna Park",
//            location = Point(latitude = -34.60766, longitude = -58.37267),
//            400000.0
//        ).apply {
//            addPlace(upperLevel, 500)
//            addPlace(box, 1000)
//            addPlace(field, 3500)
//        },
//        "ClubDePolo" to Stadium(
//            name = "Club De Polo",
//            location = Point(latitude = -34.57802, longitude = -58.42675),
//            550000.0
//        ).apply {
//            addPlace(upperLevel, 1500)
//            addPlace(box, 2000)
//            addPlace(field, 6500)
//        },
//        "TeatroOpera" to Theater(
//            name = "Teatro Opera",
//            location = Point(latitude = -34.61092, longitude = -58.37287)
//        ).apply {
//            addPlace(lowerLevel, 2000)
//            addPlace(pullman, 3000)
//        },
//        "LaBombonera" to Stadium(
//            name = "La Bombonera",
//            location = Point(latitude = -34.63536, longitude = -58.36419),
//            8500000.0
//        ).apply {
//            addPlace(upperLevel, 10000)
//            addPlace(box, 15000)
//            addPlace(field, 25000)
//        },
//        "smallFacility" to Theater(
//            name = "Teatro Poker",
//            location = Point(latitude = -34.60356, longitude = -58.38013)
//        ).apply {
//            hasGoodAcoustics = true
//            addPlace(lowerLevel, 300)
//            addPlace(pullman, 200)
//        }
    )

    fun createFacilities() {
        facilities.values.forEach {
            it.addPlace(Place1)
            it.addPlace(Place2)
            val facilityInRepo = facilityRepository.findByName(it.name).getOrNull()
            if (facilityInRepo != null) {
                it.id = facilityInRepo.id
            } else {
                facilityRepository.save(it)
                println("Facility ${it.name} created")
            }
        }
    }

    fun places(){
        placeRepository.save(Place1)
        placeRepository.save(Place2)
    }



    override fun afterPropertiesSet() {
        println("Facility creation process starts")
        places()
        createFacilities()
    }
}