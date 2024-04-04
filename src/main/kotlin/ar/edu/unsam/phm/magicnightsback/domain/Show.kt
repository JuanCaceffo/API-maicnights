package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDate
import java.time.LocalDateTime

class Show(
    val name: String,
    val band: Band,
    val facility: Facility
) : Iterable() {
    var showImg = "${band.name.removeSpaces().lowercase()}.jpg"
    private val pendingAttendees = mutableListOf<User>()
    val dates = mutableSetOf<ShowDate>()
    private var rentability: RentabilityType = BasePrice()

    fun comments() = allAttendees().flatMap { it.comments }.filter{ it.show == this }
    fun totalRating() = if (comments().size > 0) comments().sumOf { it.rating } / comments().size else 0.0

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

    fun getSeatTypes() = facility.seats.map{ it.seatType }

    fun baseCost(): Double = band.cost + facility.cost()

    private fun cost(seatType: SeatTypes): Double = (baseCost() / facility.getTotalSeatCapacity() ) + seatType.price

    fun ticketPrice(seatType: SeatTypes) = cost(seatType) * rentability.getRentability()

    fun allTicketPrices() = facility.seats.map { ticketPrice(it.seatType) }

    fun allDates() = dates.map{ it.date }.toList().sortedBy { it }

    fun allAttendees() = dates.flatMap { it.attendees }
    fun getShowDate(date: LocalDate) = dates.find { it.date.toLocalDate() == date }

    //Validations
    private fun validateComment(showDate: ShowDate) {
        if(!showDate.datePassed()){
            throw BusinessException(showError.MSG_DATE_NOT_PASSED)
        }
    }
}