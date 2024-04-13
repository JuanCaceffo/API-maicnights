package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import org.uqbar.geodds.Point

interface SeatTypes {
    val price: Double
    val name: String
}

/*
TODO: podria llegar a hacerse un refactor en como esta pensado esto ya que UPPERLEVEL Y FIELD tienen
el mismo precio que PULLMAN Y LOWERLEVEL
*/

enum class TheaterSeatType(override val price: Double,) : SeatTypes {
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
    val quantity: Int
) {
    val name = seatType.name
    fun price() = seatType.price
}

@Entity
class Facility{
    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(length = 40)
    var name: String = ""

//    @Column
//    val location: Point = Point(0,0)

//    val seatStrategy: SeatStrategy
//
//    val seats: MutableSet<SeatType> = mutableSetOf()
    fun cost() = 0.0//seatStrategy.totalCost()
//    fun getSeat(seatTypeName: AllSetTypeNames): SeatType =
//        seats.find { it.name == seatTypeName.name } ?: throw BusinessException(FacilityError.INVALID_SEAT_TYPE)
//
//    fun getAllSeatTypes() = seats.map {it.seatType}
//
//    fun getSeatCapacity(seatType: SeatTypes) = getSeat(AllSetTypeNames.valueOf(seatType.name)).quantity
//    fun getTotalSeatCapacity(): Int = seats.sumOf { it.quantity }
//    fun addSeatType(seat: SeatType) {
//        thorwInvalidSeatType(seat.name, BusinessException(FacilityError.INVALID_SEAT_TYPE))
//        seats.add(seat)
//    }
//
//    fun removeSeatType(type: SeatType) {
//        seats.remove(type)
//    }
//
//    //VALIDATIONS
//    fun thorwInvalidSeatType(seatTypeName: String, ex: RuntimeException) {
//        if (!seatStrategy.allowedSeat(seatTypeName)) {
//            throw ex
//        }
//    }
}

interface SeatStrategy {
    val fixedPrice: Double
    fun allowedSeat(seatTypeName: String): Boolean
    fun allowedSeatsNames(): List<String>
    fun seatPrice(seatType: SeatType) = seatType.price()
    fun totalCost(): Double = fixedPrice + fixedCostVariant()
    fun fixedCostVariant(): Double = 0.0
}

class StadiumStrategy(
    override val fixedPrice: Double
) : SeatStrategy {
    override fun allowedSeat(seatTypeName: String) = StadiumSeatType.entries.any { it.name == seatTypeName }
    override fun allowedSeatsNames(): List<String> = StadiumSeatType.entries.map { it.name }

}

class TheaterStrategy(
    var hasGoodAcoustics: Boolean = false
) : SeatStrategy {
    override fun allowedSeat(seatTypeName: String) = TheaterSeatType.entries.any { it.name == seatTypeName }
    override fun allowedSeatsNames(): List<String> = TheaterSeatType.entries.map { it.name }

    override val fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0

}

