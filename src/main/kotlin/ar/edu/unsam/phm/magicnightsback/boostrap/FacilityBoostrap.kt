package ar.edu.unsam.phm.magicnightsback.boostrap


import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.uqbar.geodds.Point

@Component
@Order(1)
class FacilityBoostrap(
    val facilityRepository: FacilityRepository
) : InitializingBean {
    val upperLevel = SeatType(StadiumSeatType.UPPERLEVEL, 300)
    val field = SeatType(StadiumSeatType.FIELD , 1000)
    val box = SeatType(StadiumSeatType.BOX ,200)
    val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL, 500)
    val pullman = SeatType(TheaterSeatType.PULLMAN,300)

    private val seatStrategy = mapOf(
        "theaterStrategyWithBadAccoustics" to TheaterStrategy(),
        "stadiumStrategyCheap" to StadiumStrategy(10000.0),
        "theaterStrategyWithAccoustics" to TheaterStrategy(true),
        "stadiumStrategyExpensive" to StadiumStrategy(80000.0),
    )

    val facilities = mapOf(
        "GranRex" to Facility(
            name = "Gran Rex",
            location = Point(-34.60356, -58.38013),
            seatStrategy = seatStrategy["theaterStrategyWithAccoustics"]!!
        ),
        "River" to Facility(
            name = "River Plate",
            location = Point(-34.54612, -58.45004),
            seatStrategy = seatStrategy["stadiumStrategyCheap"]!!,
        ),
        "MovistarArena" to Facility(
            name = "Movistar Arena",
            location = Point(-34.63579, -58.36527),
            seatStrategy = seatStrategy["theaterStrategyWithBadAccoustics"]!!,
        ),
        "TeatroColon" to Facility(
            name = "Teatro Colon",
            location = Point(-34.60188, -58.38336),
            seatStrategy = seatStrategy["theaterStrategyWithAccoustics"]!!
        ),
        "LunaPark" to Facility(
            name = "Luna Park",
            location = Point(-34.60766, -58.37267),
            seatStrategy = seatStrategy["stadiumStrategyCheap"]!!
        ),
        "HipodromoDePalermo" to Facility(
            name = "Hipodromo de Palermo",
            location = Point(-34.57802, -58.42675),
            seatStrategy = seatStrategy["stadiumStrategyExpensive"]!!
        ),
        "TeatroOpera" to Facility(
            name = "Teatro Opera",
            location = Point(-34.61092, -58.37287),
            seatStrategy = seatStrategy["theaterStrategyWithAccoustics"]!!
        ),
        "LaBombonera" to Facility(
            name = "La Bombonera",
            location = Point(-34.63536, -58.36419),
            seatStrategy = seatStrategy["stadiumStrategyExpensive"]!!
        )



    )

    fun addSeats() {
        facilities["GranRex"]!!.apply {
            addSeatType(pullman)
            addSeatType(lowerLevel)
        }
        facilities["River"]!!.apply {
            addSeatType(upperLevel)
            addSeatType(field)
            addSeatType(box)
        }
        facilities["MovistarArena"]!!.apply {
            addSeatType(pullman)
            addSeatType(lowerLevel)
        }
        facilities["TeatroColon"]!!.apply {
            addSeatType(pullman)
            addSeatType(lowerLevel)
        }
        facilities["HipodromoDePalermo"]!!.apply {
            addSeatType(upperLevel)
            addSeatType(field)
            addSeatType(box)
        }
        facilities["TeatroOpera"]!!.apply {
            addSeatType(pullman)
            addSeatType(lowerLevel)
        }
        facilities["LaBombonera"]!!.apply {
            addSeatType(upperLevel)
            addSeatType(field)
            addSeatType(box)
        }
    }

    fun createFacilities() {
        facilities.values.forEach { facility -> facilityRepository.apply { create(facility) } }
    }

    override fun afterPropertiesSet() {
        println("Facility creation process starts")
        createFacilities()
        addSeats()
        println("Facility creation process ends")
    }
}