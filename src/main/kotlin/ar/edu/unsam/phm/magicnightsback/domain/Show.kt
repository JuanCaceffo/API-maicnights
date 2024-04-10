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
    val pendingAttendees = mutableListOf<User>()
    val dates = mutableSetOf<ShowDate>()
    var rentabilityType: RentabilityType = BasePrice()

    fun rentability() = if (totalSales() != 0.0) (((totalSales() - baseCost()) / totalSales()) * 100) else 0.0

    fun comments() = allAttendees().flatMap { it.comments }.filter{ it.show == this }
    fun totalRating() = if (comments().isNotEmpty()) comments().sumOf { it.rating } / comments().size else 0.0

    fun canBeCommented(user: User) = !isAlreadyCommented(user) && anyShowDatesPassedFor(user)

    private fun isAlreadyCommented(user: User): Boolean = allAttendees().find { showUser -> showUser == user }?.comments?.any { comment -> comment.show == this }
        ?: false

    private fun anyShowDatesPassedFor(user:User) = dates.filter { date -> date.attendees.contains(user) }.any { date -> date.datePassed() }


    fun changeRentability(newShowStatus: RentabilityType) {
        this.rentabilityType = newShowStatus
    }

    fun addDate(date:LocalDateTime) {
        dates.add(ShowDate(date, facility))
    }
    fun friendsAttendeesProfileImages(user: User) = friendsAttending(user.id).map{ it.profileImage }
    fun friendsAttending(userId: Long) = allAttendees().filter { it.isMyFriend(userId) }

    fun getSeatTypes() = facility.seats.map{ it.seatType }

    fun baseCost(): Double = band.cost + facility.cost()

    private fun cost(seatType: SeatTypes): Double = if (facility.getTotalSeatCapacity() != 0) ((baseCost() / facility.getTotalSeatCapacity() ) + seatType.price) else (baseCost() / seatType.price)

    fun ticketPrice(seatType: SeatTypes): Double = cost(seatType) * rentabilityType.getRentability()

    fun allTicketPrices() = facility.seats.map { ticketPrice(it.seatType) }

    fun allDates() = dates.map{ it.date }.toList().sortedBy { it }

    fun allAttendees() = dates.flatMap { it.attendees }
    fun soldOutDates() = dates.filter{ it.isSoldOut() }.size
    fun ticketsSoldOfSeatType(seatType: SeatTypes) = dates.sumOf { it.getReservedSeatsOf(seatType) }
    fun totalTicketsSold() = facility.getAllSeatTypes().sumOf { ticketsSoldOfSeatType(it) }
    fun totalSales(): Double = facility.getAllSeatTypes().sumOf { ticketPrice(it) * ticketsSoldOfSeatType(it) }
    fun getShowDate(date: LocalDate) = dates.find { it.date.toLocalDate() == date }

    //Validations
    private fun validateComment(showDate: ShowDate) {
        if(!showDate.datePassed()){
            throw BusinessException(showError.USER_CANT_COMMENT)
        }
    }
}