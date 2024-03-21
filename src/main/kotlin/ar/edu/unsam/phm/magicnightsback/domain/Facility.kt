package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps
import org.uqbar.geodds.Point

abstract class Facility(
    val name: String,
    val location: Location,
    val point: Point,
    val seatCapacity: MutableMap<SeatTypes, Int>
) : RepositoryProps() {
    abstract val fixedPrice: Double
    open fun fixedCost(): Double = fixedPrice + fixedCostVariant()
    open fun fixedCostVariant(): Double = 0.0
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

class Stadium(
    name: String,
    location: Location,
    point: Point,
    seatCapacity: MutableMap<SeatTypes, Int>,
    override val fixedPrice: Double,
) : Facility(name, location, point, seatCapacity) {
}

class Theater(
    name: String,
    location: Location,
    point: Point,
    seatCapacity: MutableMap<SeatTypes, Int>,
    val hasGoodAcoustics: Boolean = false
) : Facility(name, location, point, seatCapacity) {

    override val fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}

enum class SeatTypes(val price: Double) {
    UPPERLEVEL(10000.0),
    LOWERLEVEL(15000.0),
    PULLMAN(10000.0),
    FIELD(15000.0),
    BOX(20000.0)
}

