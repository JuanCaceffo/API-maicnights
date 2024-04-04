package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.helpers.removeSpaces
import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDateTime

class Show(
    val name: String,
    val band: Band,
    val facility: Facility
) : Iterable() {
    var showImg = "${band.name.removeSpaces().lowercase()}.jpg"
    val comments = mutableListOf<Comment>()
    private val pendingAttendees = mutableListOf<User>()
    val dates = mutableSetOf<ShowDate>()
    private var rentability: RentabilityType = BasePrice()

    fun totalRating() = if (comments.size > 0) comments.sumOf { it.rating } / comments.size else 0.0

    fun addComments(comment: Comment, showDate: ShowDate){
        validateComment(showDate)
        comments.add(comment)
    }

    fun changeRentability(newShowStatus: RentabilityType) {
        this.rentability = newShowStatus
    }

    fun addPendingAttendee(user: User) {
        pendingAttendees.add(user)
    }

    fun removePendingAttendee(user: User) {
        pendingAttendees.remove(user)
    }

    fun addDate(date:LocalDateTime) {
        dates.add(ShowDate(date, facility))
    }

    fun friendsAttendeesProfileImages(userId: Long?) = userId?.let{friendsAttending(userId).map{ it.profileImage }} ?: listOf()

    fun friendsAttending(userId: Long) = allAttendees().filter { it.isMyFriend(userId) }
    private fun baseCost(): Double = band.cost + facility.cost()

    private fun cost(): Double = baseCost() * rentability.getRentability()

    private fun baseTicketPrice() = cost() / facility.getTotalSeatCapacity()

    fun fullTicketPrice(seatType: SeatTypes) = baseTicketPrice() + seatType.price

    fun allTicketPrices() = facility.getAllSeatTypes().map { fullTicketPrice(it) }

    fun allDates() = dates.map{ it.date }.toList().sortedBy { it }

    private fun allAttendees() = dates.flatMap { it.attendees }

    //Validations
    private fun validateComment(showDate: ShowDate) {
        if(!showDate.datePassed()){
            throw BusinessException(showError.MSG_DATE_NOT_PASSED)
        }
    }
}