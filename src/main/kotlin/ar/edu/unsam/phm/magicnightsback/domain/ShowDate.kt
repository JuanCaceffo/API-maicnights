package ar.edu.unsam.phm.magicnightsback.domain

import java.time.LocalDateTime

class ShowDate(
    val date: LocalDateTime,
    val facility: Facility
) {
    val attendees = mutableListOf<User>()
    val reservedSeats = facility.getAllSeatTypes().associateWith { 0 }.toMutableMap()

    fun addAttendee(user: User) {
        attendees.add(user)
    }

    //TODO: validar que pueda reservar la cantidad de asientos
    fun reserveSeat(seatType: SeatTypes, quantity: Int) {
        reservedSeats[seatType] = (reservedSeats[seatType]!! + quantity)
    }

    fun releaseSeat(seatType: SeatTypes, quantity: Int) {
        reservedSeats[seatType] = (reservedSeats[seatType]!! - quantity)
    }

    fun getReservedSeatsOf(seatType: SeatTypes) = reservedSeats[seatType] ?: 0

    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()

    fun availableSeatsOf(seatTypeName: AllSetTypeNames): Int {
        return facility.getSeatCapacity(seatTypeName) - getReservedSeatsOf(facility.getSeat(seatTypeName).seatType)
    }

    fun totalAvailableSeatsOf(): Int {
        return facility.getTotalSeatCapacity() - getAllReservedSeats()
    }

    fun datePassed() = date < LocalDateTime.now()

    fun isSoldOut() = totalAvailableSeatsOf() == 0
}