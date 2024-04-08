package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.showDateError
import java.time.LocalDateTime

class ShowDate(
    val date: LocalDateTime,
    val facility: Facility
) {
    val attendees = mutableListOf<User>()
    val reservedSeats = facility.seatStrategy.allowedSeatsNames().associateWith { 0 }.toMutableMap()

    fun addAttendee(user: User) {
        attendees.add(user)
    }

    fun reserveSeat(seatType: SeatTypes, quantity: Int) {
        quantity.throwIfGreaterThan(availableSeatsOf(seatType),showDateError.EXCEEDED_CAPACITY)
        reservedSeats[seatType.name] = (reservedSeats[seatType.name]!! + quantity)
    }

    fun releaseSeat(seatType: SeatTypes, quantity: Int) {
        reservedSeats[seatType.name] = (reservedSeats[seatType.name]!! - quantity)
    }

    fun getReservedSeatsOf(seatType: SeatTypes) = reservedSeats[seatType.name] ?: 0

    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()

    fun availableSeatsOf(seatType: SeatTypes): Int {
        return facility.getSeatCapacity(seatType) - getReservedSeatsOf(seatType)
    }

    fun totalAvailableSeats(): Int {
        return facility.getTotalSeatCapacity() - getAllReservedSeats()
    }

    fun datePassed(): Boolean = date.isBefore(LocalDateTime.now()) as Boolean

    fun isSoldOut() = totalAvailableSeats() == 0
}