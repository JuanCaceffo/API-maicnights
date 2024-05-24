package ar.edu.unsam.phm.magicnightsback.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
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

@Document("ShowDates")
data class ShowDate(
    var show: Show,
    val date: LocalDateTime
) {
    @Id
    lateinit var id: String

    val seatOcupation = show.facility.seats.map { SeatOcupation(it.id) }.toSet()

    // Availability
    fun isSoldOut() = seatOcupation.all { it.available() == 0 }
    fun available(reservation: Int = 0): Map<Seat, Int> = seatOcupation.associate { it.seat to it.available() - reservation }
    fun modifyOcupation(seat: Seat, quantity: Int = 1) {
        seatOcupation.first { it.seat == seat }.modifyUsedCapacity(quantity)
    }

    fun beenStaged(): Boolean = date.isBefore(LocalDateTime.now())
}