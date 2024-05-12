package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.exceptions.BadArgumentException
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.CreationError
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.utils.notNegative
import jakarta.persistence.*
import java.time.LocalDateTime

//@Entity
//class ShowDate(
//    @Column
//    val date: LocalDateTime,
//    @ManyToOne
//    private val show: Show
//) {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long = 0
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
//    val attendees = mutableSetOf<User>()
//
//    @ElementCollection(fetch = FetchType.LAZY)
//    val reservedSeats = show.facility.validSeatTypes().associateWith { 0 }.toMutableMap()
//
//    fun addAttendee(user: User) {
//        attendees.add(user)
//    }
//
//    fun reserveSeat(seat: SeatTypes, quantity: Int) {
//        quantity.throwIfGreaterThan(availableSeatsOf(seat), ShowDateError.EXCEEDED_CAPACITY)
//        reservedSeats[seat.name] = (reservedSeats[seat.name]!! + quantity)
//    }
//
//    fun releaseSeat(seat: SeatTypes, quantity: Int) {
//        reservedSeats[seat.name] = (reservedSeats[seat.name]!! - quantity)
//    }
//
//    fun getReservedSeatsOf(seat: SeatTypes) = reservedSeats[seat.name] ?: 0
//
//    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()
//
//    fun availableSeatsOf(seat: SeatTypes): Int {
//        return show.facility.getPlaceCapacity(seat) - getReservedSeatsOf(seat)
//    }
//
//    fun totalAvailableSeats(): Int {
//        return show.facility.getTotalSeatCapacity() - getAllReservedSeats()
//    }
//
//    fun datePassed(): Boolean = date.isBefore(LocalDateTime.now())
//
//    fun isAttendee(user: User) = attendees.any { it.id == user.id }
//
//    fun isSoldOut() = totalAvailableSeats() == 0
//}

@Entity
data class ShowDate(
    @ManyToOne
    val show: Show,
    val date: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    val soldOut: Boolean = false


    // Costs
    fun baseCost(): Double = (show.band.cost).plus(show.facility.cost)
    fun baseCostPerSeat(seat: Seat) = baseCost() / seatCapacityOf(seat)
    fun baseSeatCost(seat: Seat) = baseCostPerSeat(seat) + seat.price
    fun currentPrice(seat: Seat) = (baseSeatCost(seat) * show.rentability.factor)


    // Availability
    fun beenStaged(): Boolean = date.isBefore(LocalDateTime.now())
    private fun getSeat(seat: Seat) =
        show.facility.seats.firstOrNull { it.type == seat.type }
    fun seatCapacityOf(seat: Seat) =
        getSeat(seat)?.capacity ?: throw BusinessException(FindError.NOT_FOUND(seat.id, seat.type.name))

    fun availableOf(seat: Seat, quantity: Int) =
        seatCapacityOf(seat)
            .minus(quantity)

    // Validations
    fun validateBeenStaged() {
        if (beenStaged()) {
            throw BusinessException(CreationError.ALREADY_PASSED)
        }
    }

    fun validateAvailability(seat: Seat, quantity: Int) =
        availableOf(seat, quantity).notNegative(throw BadArgumentException(CreationError.NO_CAPACITY))
}