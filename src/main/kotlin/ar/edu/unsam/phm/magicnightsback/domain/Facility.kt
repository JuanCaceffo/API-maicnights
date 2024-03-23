package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps
import org.uqbar.geodds.Point

//SE PUEDE AGREGAR VALIDADOR PARA AHORRAR CODIGO DUPLICADO CADA VEZ Q SE QUIERA NOMBRAR UN TIPO D ASIENTO

interface SeatTypes {
    val price: Double
}

enum class TheaterSeatType(override val price: Double) : SeatTypes {
    LOWERLEVEL(15000.0),
    PULLMAN(10000.0)
}

enum class StadiumSeatType(override val price: Double) : SeatTypes {
    UPPERLEVEL(10000.0),
    FIELD(15000.0),
    BOX(20000.0)
}

//enum class SeatTypes {
//    LOWERLEVEL,
//    PULLMAN,
//    UPPERLEVEL,
//    FIELD,
//    BOX
//}

class SeatType (
    val seatType: SeatTypes,
    val quantity: Int
) {
    fun price() = seatType.price
}

class Facility(
    val name: String,
    val location: Point,
    val costStrategy: CostStrategy
) : RepositoryProps() {
    val seats: MutableSet<SeatType> = mutableSetOf()
    fun cost() = costStrategy.totalCost()
    fun addSeatType(type: SeatType) { seats.add(type) }
    fun removeSeatType(type: SeatType) { seats.remove(type) }
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

interface CostStrategy {
    val fixedPrice: Double
//    fun seatValidation(seatType: SeatType) : Boolean
    fun seatPrice(seatType: SeatType) = seatType.price()
    fun totalCost(): Double = fixedPrice + fixedCostVariant()
    fun fixedCostVariant(): Double = 0.0
}

class StadiumStrategy(
    override val fixedPrice : Double
) : CostStrategy

class TheaterStrategy(
    val hasGoodAcoustics: Boolean = false
) : CostStrategy {
    override val fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}

