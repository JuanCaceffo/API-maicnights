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
        "theaterStrategyWithBadAccustics" to TheaterStrategy(),
        "stadiumStrategyCheap" to StadiumStrategy(10000.0),
        "theaterStrategyWithAccustics" to TheaterStrategy(true)
    )

    val facilities = mapOf(
        "GranRex" to Facility(
            name = "Gran Rex",
            location = Point(-34.60356, -58.38013),
            seatStrategy = seatStrategy["theaterStrategyWithAccustics"]!!
        ), "River" to Facility(
            name = "River Plate",
            location = Point(-34.54612, -58.45004),
            seatStrategy = seatStrategy["stadiumStrategyCheap"]!!,
        ), "MovistarArena" to Facility(
            name = "Movistar Arena",
            location = Point(-34.63579, -58.36527),
            seatStrategy = seatStrategy["theaterStrategyWithBadAccustics"]!!,
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