package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import org.uqbar.geodds.Point

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

class SeatType (
    val seatType: SeatTypes,
    val quantity: Int
) {
    fun price() = seatType.price
}
// POSIBLE REFACTOR: Se puede usar un builder, pero no da el tiempo
class Facility(
    val name: String,
    val location: Point,
    val seatStrategy: SeatStrategy
) : Iterable() {
    val seats: MutableSet<SeatType> = mutableSetOf()
    fun cost() = seatStrategy.totalCost()
    fun getSeat(seat: SeatTypes) = seats.find{ it.seatType == seat }
    fun getSeatCapacity(seat: SeatTypes) = getSeat(seat)?.quantity ?: 0
    fun getTotalSeatCapacity() = seats.sumOf { it.quantity }
    fun addSeatType(seat: SeatType) {
        if (!seatStrategy.seatValidation(seat)) {
            throw BusinessException(FacilityError.INVALID_SEAT_TYPE)
        }
        seats.add(seat)
    }
    fun getAllSeatTypes() = seats.map { it.seatType }
    fun removeSeatType(type: SeatType) { seats.remove(type) }
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

interface SeatStrategy {
    val fixedPrice: Double
    fun seatValidation(seat: SeatType) : Boolean
    fun seatPrice(seatType: SeatType) = seatType.price()
    fun totalCost(): Double = fixedPrice + fixedCostVariant()
    fun fixedCostVariant(): Double = 0.0
}

class StadiumStrategy(
    override val fixedPrice : Double
) : SeatStrategy {
    override fun seatValidation(seat: SeatType) = StadiumSeatType.entries.any{ it == seat.seatType }
}

class TheaterStrategy(
    var hasGoodAcoustics: Boolean = false
) : SeatStrategy {
    override fun seatValidation(seat: SeatType) = TheaterSeatType.entries.any { it == seat.seatType }
    override val fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0

}

