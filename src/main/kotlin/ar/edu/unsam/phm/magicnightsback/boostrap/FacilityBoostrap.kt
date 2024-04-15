package ar.edu.unsam.phm.magicnightsback.boostrap


import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class FacilityBoostrap : InitializingBean {
    @Autowired
    lateinit var facilityRepository: FacilityRepository

    val facilities = mapOf(
        "GranRex" to Theater(
            name = "Gran Rex",
            location = Point(latitude = -34.603542, longitude = -58.378856)
        ).apply {
            hasGoodAcoustics = true
            addPlace(Seat(SeatTypes.PULLMAN), 2000)
            addPlace(Seat(SeatTypes.LOWERLEVEL), 3000)
        },
        "River" to Stadium(
            name = "River Plate",
            location = Point(latitude = -34.54612, longitude = -58.45004),
            1000000.0
        ).apply {
            addPlace(Seat(SeatTypes.UPPERLEVEL), 20000)
            addPlace(Seat(SeatTypes.FIELD), 15000)
            addPlace(Seat(SeatTypes.BOX), 35000)
        },
        "MovistarArena" to Theater(
            name = "Movistar Arena",
            location = Point(latitude = -34.63579, longitude = -58.36527)
        ).apply {
            addPlace(Seat(SeatTypes.LOWERLEVEL), 1000)
            addPlace(Seat(SeatTypes.PULLMAN), 2000)
        },
        "TeatroColon" to Theater(
            name = "Teatro Colon",
            location = Point(latitude = -34.60188, longitude = -58.38336)
        ).apply {
            addPlace(Seat(SeatTypes.PULLMAN), 400)
            addPlace(Seat(SeatTypes.LOWERLEVEL), 3000)
        },
        "LunaPark" to Stadium(
            name = "Luna Park",
            location = Point(latitude = -34.60766, longitude = -58.37267),
            400000.0
        ).apply {
            addPlace(Seat(SeatTypes.UPPERLEVEL), 500)
            addPlace(Seat(SeatTypes.BOX), 1000)
            addPlace(Seat(SeatTypes.FIELD), 3500)
        },
        "ClubDePolo" to Stadium(
            name = "Club De Polo",
            location = Point(latitude = -34.57802, longitude = -58.42675),
            550000.0
        ).apply {
            addPlace(Seat(SeatTypes.UPPERLEVEL), 1500)
            addPlace(Seat(SeatTypes.BOX), 2000)
            addPlace(Seat(SeatTypes.FIELD), 6500)
        },
        "TeatroOpera" to Theater(
            name = "Teatro Opera",
            location = Point(latitude = -34.61092, longitude = -58.37287)
        ).apply {
            addPlace(Seat(SeatTypes.LOWERLEVEL), 2000)
            addPlace(Seat(SeatTypes.PULLMAN), 3000)
        },
        "LaBombonera" to Stadium(
            name = "La Bombonera",
            location = Point(latitude = -34.63536, longitude = -58.36419),
            8500000.0
        ).apply {
            addPlace(Seat(SeatTypes.UPPERLEVEL), 10000)
            addPlace(Seat(SeatTypes.BOX), 15000)
            addPlace(Seat(SeatTypes.FIELD), 25000)
        },
        "smallFacility" to Theater(
            name = "Teatro Poker",
            location = Point(latitude = -34.60356, longitude = -58.38013)
        ).apply {
            hasGoodAcoustics = true
            addPlace(Seat(SeatTypes.LOWERLEVEL), 300)
            addPlace(Seat(SeatTypes.PULLMAN), 200)
        }
    )

    fun createFacilities() {
        facilities.values.forEach {
            val facilityInRepo = facilityRepository.findByName(it.name).getOrNull()
            if (facilityInRepo != null) {
                it.id = facilityInRepo.id
            } else {
                facilityRepository.save(it)
                println("Facility ${it.name} created")
            }
        }
    }

    @DependsOn("seatTypesBoostrap")
    override fun afterPropertiesSet() {
        println("Facility creation process starts")
        createFacilities()
    }
}