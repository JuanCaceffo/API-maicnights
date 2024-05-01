package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.ShowDateError
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val attendees = mutableSetOf<User>()

    @ElementCollection(fetch = FetchType.LAZY)
    val reservedSeats = facility.validSeatTypes().associateWith { 0 }.toMutableMap()

    fun addAttendee(user: User) {
        attendees.add(user)
    }

    fun reserveSeat(seat: SeatTypes, quantity: Int) {
        quantity.throwIfGreaterThan(availableSeatsOf(seat),ShowDateError.EXCEEDED_CAPACITY)
        reservedSeats[seat.name] = (reservedSeats[seat.name]!! + quantity)
    }

    fun releaseSeat(seat: SeatTypes, quantity: Int) {
        reservedSeats[seat.name] = (reservedSeats[seat.name]!! - quantity)
    }

    fun getReservedSeatsOf(seat: SeatTypes) = reservedSeats[seat.name] ?: 0

    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()

    fun availableSeatsOf(seat: SeatTypes): Int {
        return facility.getPlaceCapacity(seat) - getReservedSeatsOf(seat)
    }

    fun totalAvailableSeats(): Int {
        return facility.getTotalSeatCapacity() - getAllReservedSeats()
    }

    fun datePassed(): Boolean = date.isBefore(LocalDateTime.now())

    fun isSoldOut() = totalAvailableSeats() == 0
}