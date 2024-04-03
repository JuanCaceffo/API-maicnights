package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import ar.edu.unsam.phm.magicnightsback.error.InternalServerError
import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import org.uqbar.geodds.Point

interface SeatTypes {
    val price: Double
}


/*
TODO: podria llegar a hacerse un refactor en como esta pensado esto ya que UPPERLEVEL Y FIELD tienen
el mismo precio que PULLMAN Y LOWERLEVEL
*/

enum class TheaterSeatType(override val price: Double) : SeatTypes {
    LOWERLEVEL(15000.0),
    PULLMAN(10000.0)
}

enum class StadiumSeatType(override val price: Double) : SeatTypes {
    UPPERLEVEL(10000.0),
    FIELD(15000.0),
    BOX(20000.0)
}

//TODO: buscar una forma mas adecuada
enum class AllSetTypeNames {
    UPPERLEVEL,
    FIELD,
    BOX,
    LOWERLEVEL,
    PULLMAN
}

class SeatType(
    val seatType: SeatTypes,
    private val enumAllSeatType: AllSetTypeNames,
    val quantity: Int
) : Iterable() {
    val name = enumAllSeatType.name
    fun price() = seatType.price
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

class Facility(
    val name: String,
    val location: Point,
    val seatStrategy: SeatStrategy
) : Iterable() {
    val seats: MutableSet<SeatType> = mutableSetOf()
    fun cost() = seatStrategy.totalCost()
    fun getSeat(seatTypeName: AllSetTypeNames): SeatType =
        seats.find { it.name == seatTypeName.name } ?: throw InternalServerError(FacilityError.INVALID_SEAT_TYPE)

    fun getSeatCapacity(seat: AllSetTypeNames) = getSeat(seat).quantity
    fun getTotalSeatCapacity() = seats.sumOf { it.quantity }
    fun addSeatType(seat: SeatType) {
        thorwInvalidSeatType(seat.name, BusinessException(FacilityError.INVALID_SEAT_TYPE))
        seats.add(seat)
    }

    fun getAllSeatTypes() = seats.map { it.seatType }
    fun removeSeatType(type: SeatType) {
        seats.remove(type)
    }

    //VALIDATIONS

    fun thorwInvalidSeatType(seatTypeName: String, ex: RuntimeException) {
        if (!seatStrategy.allowedSeat(seatTypeName)) {
            throw ex
        }
    }

    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

interface SeatStrategy {
    val fixedPrice: Double
    fun allowedSeat(seatTypeName: String): Boolean

    fun seatPrice(seatType: SeatType) = seatType.price()
    fun totalCost(): Double = fixedPrice + fixedCostVariant()
    fun fixedCostVariant(): Double = 0.0
}

class StadiumStrategy(
    override val fixedPrice: Double
) : SeatStrategy {
    override fun allowedSeat(seatTypeName: String) = StadiumSeatType.entries.any { it.name == seatTypeName }
}

class TheaterStrategy(
    var hasGoodAcoustics: Boolean = false
) : SeatStrategy {
    override fun allowedSeat(seatTypeName: String) = TheaterSeatType.entries.any { it.name == seatTypeName }

    override val fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0

}

