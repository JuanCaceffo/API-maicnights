package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.repository.Iterable

class Show(
    val name: String,
    val band: Band,
    val facility: Facility
) : Iterable() {

    val dates = mutableSetOf<Long>()
    var showImg = "$name.jpg"
    var rentability: RentabilityType = BasePrice()
    val pendingAttendees = mutableListOf<User>()
    val comments = mutableListOf<Comment>()

    fun totalRating() = if (comments.size > 0) comments.sumOf { it.rating } / comments.size else 0


    fun addComments(comment: Comment, showDate: ShowDate){
        validateComment(showDate)
        comments.add(comment)
    }

    fun validateComment(showDate: ShowDate) {
        if(!showDate.datePassed()){
            throw BusinessException(showError.MSG_DATE_NOT_PASSED)
        }
    }
    fun baseCost(): Double = band.cost + facility.cost()
    fun changeRentability(newShowStatus: RentabilityType) {
        this.rentability = newShowStatus
    }
    fun addPendingAttendee(user: User) {
        pendingAttendees.add(user)
    }
    fun removePendingAttendee(user: User) {
        pendingAttendees.remove(user)
    }
    fun addDate(showDateid: Long) {
        dates.add(showDateid)
    }
    fun cost(): Double = baseCost() * rentability.getRentability()
    fun baseTicketPrice() = cost() / facility.getTotalSeatCapacity()
    fun fullTicketPrice(seatType: SeatTypes) = baseTicketPrice() + seatType.price
    fun allTicketPrices() = facility.getAllSeatTypes().map { fullTicketPrice(it) }
//    fun allDates() = dates.map{ it.date }
//    fun soldOutDates() = dates.filter{ it.isSoldOut() }.size
//    fun ticketsSoldOfSeatType(seatType: SeatTypes) = dates.sumOf { it.getReservedSeatsOf(seatType) }
//    fun totalTicketsSold() = facility.getAllSeatTypes().sumOf { ticketsSoldOfSeatType(it) }
//    fun totalSales() = facility.getAllSeatTypes().sumOf { fullTicketPrice(it) * ticketsSoldOfSeatType(it) }
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

interface RentabilityType {
    fun getRentability(): Double
}

class BasePrice : RentabilityType {
    override fun getRentability(): Double = 0.8
}

class FullSale : RentabilityType {
    override fun getRentability(): Double = 1.0
}

class MegaShow : RentabilityType {
    override fun getRentability(): Double = 1.3
}