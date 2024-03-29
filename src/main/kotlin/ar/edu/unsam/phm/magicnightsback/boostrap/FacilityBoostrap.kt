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

    private val seatStrategy = mapOf(
        "theaterStrategyWithBadAccustics" to TheaterStrategy(),
        "stadiumStrategyCheap" to StadiumStrategy(10000.0),
        "theaterStrategyWithAccustics" to TheaterStrategy(true)
    )

    private val facilities = mapOf(
        "GranRex" to Facility(
            name = "Gran Rex",
            location = Point(-34.60356, -58.38013),
            seatStrategy = seatStrategy["theaterStrategyWithAccustics"]!!
        ), "River" to Facility(
            name = "River Plate",
            location = Point(-34.54612, -58.45004),
            seatStrategy = seatStrategy["stadiumStrategyCheap"]!!,
        ), "Boca" to Facility(
            name = "Boca Juniors",
            location = Point(-34.63579, -58.36527),
            seatStrategy = seatStrategy["theaterStrategyWithBadAccustics"]!!,
        )
    )

    fun createFacilities() {
        facilities.values.forEach { facility -> facilityRepository.apply { create(facility) } }
    }

    override fun afterPropertiesSet() {
        println("Facility creation process starts")
        this.createFacilities()
        println("Facility creation process ends")
    }
}