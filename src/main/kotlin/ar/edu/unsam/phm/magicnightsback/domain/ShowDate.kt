package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.showDateError
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class ShowDate(
    @Column
    val date: LocalDateTime,
    @ManyToOne
    val facility: Facility
) {
    @Id
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY)
    val attendees = mutableSetOf<User>()
    @ElementCollection(fetch = FetchType.LAZY)
    val reservedSeats = facility.validSeatTypes().associateWith { 0 }.toMutableMap()

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
        return facility.getPlaceCapacity(seatType) - getReservedSeatsOf(seatType)
    }

    fun totalAvailableSeats(): Int {
        return facility.getTotalSeatCapacity() - getAllReservedSeats()
    }

    fun datePassed(): Boolean = date.isBefore(LocalDateTime.now())

    fun isSoldOut() = totalAvailableSeats() == 0
}