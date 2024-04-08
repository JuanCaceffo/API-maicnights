package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.showDateError
import java.time.LocalDateTime

class ShowDate(
    val date: LocalDateTime,
    val facility: Facility
) {
    val attendees = mutableSetOf<User>()
    val reservedSeats = facility.seatStrategy.allowedSeatsNames().associateWith { 0 }.toMutableMap()

    fun addAttendee(user: User) {
        attendees.add(user)
    }

    fun reserveSeat(seatType: SeatTypes, quantity: Int) {
        reservedSeats[seatType.name] = (reservedSeats[seatType.name]!! + quantity).throwIfGreaterThan(availableSeatsOf(seatType),showDateError.EXCEEDED_CAPACITY).toInt()
    }

    fun releaseSeat(SeatType: SeatTypes, quantity: Int) {
        reservedSeats[SeatType.name] = (reservedSeats[SeatType.name]!! - quantity)
    }

    fun getReservedSeatsOf(seatType: SeatTypes) = reservedSeats[seatType.name] ?: 0

    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()

    fun availableSeatsOf(seatType: SeatTypes): Int {
        return facility.getSeatCapacity(seatType) - getReservedSeatsOf(seatType)
    }

    fun totalAvailableSeatsOf(): Int {
        return facility.getTotalSeatCapacity() - getAllReservedSeats()
    }

    fun datePassed(): Boolean = date.isBefore(LocalDateTime.now()) as Boolean


    fun isSoldOut() = totalAvailableSeatsOf() == 0
}