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
    val dates = mutableSetOf<ShowDate>()
    private var rentability: RentabilityType = BasePrice()

    fun comments() = allAttendees().flatMap { it.comments }.filter{ it.show == this }
    fun totalRating() = if (comments().isNotEmpty()) comments().sumOf { it.rating } / comments().size else 0.0

    fun canBeCommented(user: User) = !isAlreadyCommented(user) && anyShowDatesPassedFor(user)

    private fun isAlreadyCommented(user: User): Boolean = allAttendees().find { showUser -> showUser == user }?.comments?.any { comment -> comment.show == this }
        ?: false

    private fun anyShowDatesPassedFor(user:User) = dates.filter { date -> date.attendees.contains(user) }.any { date -> date.datePassed() }


    fun changeRentability(newShowStatus: RentabilityType) {
        this.rentability = newShowStatus
    }

    fun addDate(date:LocalDateTime) {
        dates.add(ShowDate(date, facility))
    }

    fun getSeatTypes() = facility.seats.map{ it.seatType }

    fun friendsAttendeesProfileImages(user: User) = allAttendees().filter { it.isMyFriend(user) }.map{ it.profileImage }

    fun baseCost(): Double = band.cost + facility.cost()

    private fun cost(seatType: SeatTypes): Double = (baseCost() / facility.getTotalSeatCapacity() ) + seatType.price

    fun ticketPrice(seatType: SeatTypes) = cost(seatType) * rentability.getRentability()

    fun allTicketPrices() = facility.seats.map { ticketPrice(it.seatType) }

    fun allDates() = dates.map{ it.date }.toList().sortedBy { it }

    fun getShowDate(date: LocalDate) = dates.find { it.date.toLocalDate() == date }

    fun allAttendees() = dates.flatMap { it.attendees }.distinct()
//    fun soldOutDates() = dates.filter{ it.isSoldOut() }.size
//    fun ticketsSoldOfSeatType(seatType: SeatTypes) = dates.sumOf { it.getReservedSeatsOf(seatType) }
//    fun totalTicketsSold() = facility.getAllSeatTypes().sumOf { ticketsSoldOfSeatType(it) }
//    fun totalSales() = facility.getAllSeatTypes().sumOf { fullTicketPrice(it) * ticketsSoldOfSeatType(it) }

    //Validations
    private fun validateComment(showDate: ShowDate) {
        if(!showDate.datePassed()){
            throw BusinessException(showError.USER_CANT_COMMENT)
        }
    }

    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }


}