package ar.edu.unsam.phm.magicnightsback.domain.factory

import ar.edu.unsam.phm.magicnightsback.domain.Point
import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.Stadium
import ar.edu.unsam.phm.magicnightsback.domain.Theater
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import org.springframework.stereotype.Component

enum class FacilityFactoryTypes {
    BIGSTADIUM, MEDIUMSTADIUM, THEATER, BADTHEATER, SMALLTHEATER
}

@Component
class FacilityFactory {
    fun createFacility(type: FacilityFactoryTypes) = when (type) {
        FacilityFactoryTypes.BIGSTADIUM -> BigStadium().build()
        FacilityFactoryTypes.MEDIUMSTADIUM -> MediumStadium().build()
        FacilityFactoryTypes.THEATER -> GoodTheater().build()
        FacilityFactoryTypes.BADTHEATER -> BadTheater().build()
        FacilityFactoryTypes.SMALLTHEATER -> SmallTheater().build()
    }
}

class BigStadium(
) : FactoryObject<Stadium> {
    override fun build() = Stadium(
        name = "River Plate",
        fixedPrice = 1000000.0,
        location = Point(latitude = -34.54612, longitude = -58.45004),
        seats = listOf(
            Seat(SeatTypes.UPPERLEVEL, 20000),
            Seat(SeatTypes.FIELD, 35000),
            Seat(SeatTypes.BOX, 15000)
        )
    )
}

class MediumStadium(
) : FactoryObject<Stadium> {
    override fun build() = Stadium(
        name = "La Bombonera",
        fixedPrice = 700000.0,
        location = Point(latitude = -34.63536, longitude = -58.36419),
        seats = listOf(
            Seat(SeatTypes.UPPERLEVEL, 10000),
            Seat(SeatTypes.FIELD, 25000),
            Seat(SeatTypes.BOX, 15000)
        )
    )
}

class GoodTheater(
) : FactoryObject<Theater> {
    override fun build() = Theater(
        name = "Teatro Colón",
        location = Point(latitude = -34.60188, longitude = -58.38336),
        seats = listOf(
            Seat(SeatTypes.LOWERLEVEL, 8000),
            Seat(SeatTypes.PULLMAN, 2000)
        )
    ).apply {

        hasGoodAcoustics = true
    }
}

class BadTheater(
) : FactoryObject<Theater> {
    override fun build() = Theater(
        name = "Gran Rex",
        location = Point(latitude = -34.603542, longitude = -58.378856),
        seats = listOf(
            Seat(SeatTypes.LOWERLEVEL, 3000),
            Seat(SeatTypes.PULLMAN, 1000)
        )
    )
}

class SmallTheater(
) : FactoryObject<Theater> {
    override fun build() = Theater(
        name = "Teatro Peque",
        location = Point(latitude = -34.193847, longitude = -36.144562),
        seats = listOf(
            Seat(SeatTypes.LOWERLEVEL, 2)
        )
    )
}