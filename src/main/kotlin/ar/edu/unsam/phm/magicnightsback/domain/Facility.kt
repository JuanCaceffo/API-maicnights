package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps

const val UPPERLEVEL_PRICE = 10000.0
const val LOWERLEVEL_PRICE = 15000.0
const val PULLMAN_PRICE = 10000.0
const val FIELD_PRICE = 15000.0
const val BOX_PRICE = 20000.0

abstract class Facility(
    val name: String,
    val location: Location,
    val seatCapacity: MutableMap<SeatTypes, Int>,
    val seatPrice: MutableMap<SeatTypes, Double>
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
    seatCapacity: MutableMap<SeatTypes, Int>,
    seatPrice: MutableMap<SeatTypes, Double> = mutableMapOf(
        SeatTypes.UPPERLEVEL to UPPERLEVEL_PRICE,
        SeatTypes.FIELD to FIELD_PRICE,
        SeatTypes.BOX to BOX_PRICE
    ),
//    val upperLevelSeatingCapacity: Int, // Platea alta
//    val fieldCapacity: Int, // Campo
//    val boxCapacity: Int, // Palco
    override val fixedPrice: Double,
) : Facility(name, location, seatCapacity, seatPrice) {

//    fun upperLevelSeatingCost() = 10000
//    fun fieldCost() = 15000
//    fun boxCost() = 20000
}

class Theater(
    name: String,
    location: Location,
    seatCapacity: MutableMap<SeatTypes, Int>,
    seatPrice: MutableMap<SeatTypes, Double> = mutableMapOf(
        SeatTypes.LOWERLEVEL to LOWERLEVEL_PRICE,
        SeatTypes.PULLMAN to PULLMAN_PRICE
    ),
//    val pullmanCapacity: Int,
//    val lowerLevelSeatingCapacity: Int, // Platea baja
    val hasGoodAcoustics: Boolean = false
) : Facility(name, location, seatCapacity, seatPrice) {

    override val fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}
class SeatType(quantity: Int, price: Int)
enum class SeatTypes{
    UPPERLEVEL,
    LOWERLEVEL,
    PULLMAN,
    FIELD,
    BOX
}

