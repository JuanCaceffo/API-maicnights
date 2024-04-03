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

    //TODO: validar que pueda reservar la cantidad de asientos
    fun reserveSeat(seatTypeName: AllSetTypeNames, quantity: Int) {
        reservedSeats[seatTypeName.name] = (reservedSeats[seatTypeName.name]!! + quantity).throwIfGreaterThan(availableSeatsOf(seatTypeName),showDateError.EXCEEDED_CAPACITY)
    }

    fun releaseSeat(seatTypeName: AllSetTypeNames, quantity: Int) {
        reservedSeats[seatTypeName.name] = (reservedSeats[seatTypeName.name]!! - quantity)
    }

    fun getReservedSeatsOf(seatTypeName: AllSetTypeNames) = reservedSeats[seatTypeName.name] ?: 0

    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()

    fun availableSeatsOf(seatTypeName: AllSetTypeNames): Int {
        return facility.getSeatCapacity(seatTypeName) - getReservedSeatsOf(seatTypeName)
    }

    fun totalAvailableSeatsOf(): Int {
        return facility.getTotalSeatCapacity() - getAllReservedSeats()
    }

    fun datePassed() = date < LocalDateTime.now()

    fun isSoldOut() = totalAvailableSeatsOf() == 0
}