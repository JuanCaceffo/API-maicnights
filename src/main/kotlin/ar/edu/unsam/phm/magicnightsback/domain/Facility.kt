package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps
import org.uqbar.geodds.Point

//SE PUEDE AGREGAR VALIDADOR PARA AHORRAR CODIGO DUPLICADO CADA VEZ Q SE QUIERA NOMBRAR UN TIPO D ASIENTO

interface SeatType {
    val price: Double
}

enum class TheaterSeatType(override val price: Double) : SeatType {
    LOWERLEVEL(15000.0),
    PULLMAN(10000.0)
}

enum class StadiumSeatType(override val price: Double) : SeatType {
    UPPERLEVEL(10000.0),
    FIELD(15000.0),
    BOX(20000.0)
}
class Facility(
    val name: String,
    val location: Point,
    val costStrategy: CostStrategy
) : RepositoryProps() {
    fun cost() = costStrategy.totalCost()
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

interface CostStrategy {
    val fixedPrice: Double
//    fun seatValidation(seatType: SeatType) : Boolean
    fun seatPrice(seatType: SeatType) = seatType.price
    fun totalCost(): Double = fixedPrice + fixedCostVariant()
    fun fixedCostVariant(): Double = 0.0
}

class StadiumStrategy(
    override val fixedPrice : Double,
    val seatCapacity: MutableMap<StadiumSeatType,Int> = mutableMapOf(
        StadiumSeatType.UPPERLEVEL to 0,
        StadiumSeatType.FIELD to 0,
        StadiumSeatType.BOX to 0
    )
) : CostStrategy {
//    override fun seatValidation(seatType: SeatType) : Boolean {
//        if StadiumSeatType.entries.containsKey(seatType)
//    }
}

class TheaterStrategy(
    val hasGoodAcoustics: Boolean = false,
    val seatCapacity: MutableMap<TheaterSeatType,Int> = mutableMapOf(
        TheaterSeatType.LOWERLEVEL to 0,
        TheaterSeatType.PULLMAN to 0
    )
) : CostStrategy {
    override val fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}

