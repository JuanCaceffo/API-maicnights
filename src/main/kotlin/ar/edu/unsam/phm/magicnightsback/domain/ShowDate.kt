package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDateTime

class ShowDate(
    val date: LocalDateTime,
    val show: Show) : Iterable() {

    val attendees = mutableListOf<User>()
    val pendingAttendees = mutableListOf<User>()
    val reservedSeats = show.facility.getAllSeatTypes().associateWith { 0 }.toMutableMap()
    val comments = mutableListOf<Comment>()

    fun totalRating() = comments.sumOf { it.rating } / comments.size

    fun addComments(comment: Comment){
        validateComment()
        comments.add(comment)
    }

    fun validateComment() {
        if(!datePassed()){
            throw BusinessException(showError.MSG_DATE_NOT_PASSED)
        }
    }

    fun addAttendee(user: User) {
        attendees.add(user)
    }

    fun addPendingAttendee(user: User) {
        pendingAttendees.add(user)
    }

    fun friendsAttending(user: User) = user.friends.filter{ attendees.contains(it) }

    fun reserveSeat(seatType: SeatTypes, quantity: Int) {
        reservedSeats[seatType] = (reservedSeats[seatType]!! + quantity)
    }

    fun releaseSeat(seatType: SeatTypes, quantity: Int) {
        reservedSeats[seatType] = (reservedSeats[seatType]!! - quantity)
    }

    fun getReservedSeatsOf(seatType: SeatTypes) = reservedSeats[seatType] ?: 0

    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()
    fun availableSeatsOf(seatType: SeatTypes): Int {
        return show.facility.getSeatCapacity(seatType) - getReservedSeatsOf(seatType)
    }

    fun totalAvailableDateSeatsOf(): Int {
        return show.facility.getTotalSeatCapacity() - getAllReservedSeats()
    }

    fun datePassed() = date < LocalDateTime.now()
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}