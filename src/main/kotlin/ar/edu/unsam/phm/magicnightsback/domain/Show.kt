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

    fun getSeatTypes() = facility.seats.map{ it.seatType }

    fun friendsAttendeesProfileImages(userId: Long?) = userId?.let{allAttendees().filter { it.isMyFriend(userId) }.map{ it.profileImage }} ?: listOf()

    private fun baseCost(): Double = (band.cost + facility.cost()) / facility.getTotalSeatCapacity()

    private fun cost(seatType: SeatTypes): Double = baseCost() + seatType.price

    fun ticketPrice(seatType: SeatTypes) = cost(seatType) * rentability.getRentability()

    fun allTicketPrices() = facility.seats.map { ticketPrice(it.seatType) }

    fun allDates() = dates.map{ it.date }.toList().sortedBy { it }

    fun getShowDate(date: LocalDate) = dates.find { it.date.toLocalDate() == date }

    private fun allAttendees() = dates.flatMap { it.attendees }
//    fun soldOutDates() = dates.filter{ it.isSoldOut() }.size
//    fun ticketsSoldOfSeatType(seatType: SeatTypes) = dates.sumOf { it.getReservedSeatsOf(seatType) }
//    fun totalTicketsSold() = facility.getAllSeatTypes().sumOf { ticketsSoldOfSeatType(it) }
//    fun totalSales() = facility.getAllSeatTypes().sumOf { fullTicketPrice(it) * ticketsSoldOfSeatType(it) }

    //Validations
    private fun validateComment(showDate: ShowDate) {
        if(!showDate.datePassed()){
            throw BusinessException(showError.MSG_DATE_NOT_PASSED)
        }
    }

    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}