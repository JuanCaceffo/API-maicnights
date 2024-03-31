package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDateTime

class Show(
    val name: String,
    val band: Band,
    val facility: Facility,
) : Iterable() {
    val dates = mutableListOf<ShowDate>()
    var showImg = "$name.jpg"
    var rentability: RentabilityType = BasePrice()
    val pendingAttendees = mutableListOf<User>()

    fun allComments() = dates.flatMap { it.comments }
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
    fun addDate(date: LocalDateTime) {
        dates.add(ShowDate(date, facility))
    }
    fun cost(): Double = baseCost() * rentability.getRentability()
    fun baseTicketPrice() = cost() / facility.getTotalSeatCapacity()
    fun fullTicketPrice(seatType: SeatTypes) = baseTicketPrice() + seatType.price
    fun allTicketPrices() = facility.getAllSeatTypes().map { fullTicketPrice(it) }
    fun allDates() = dates.map{ it.date }
    fun soldOutDates() = dates.filter{ it.isSoldOut() }.size
    fun ticketsSoldOfSeatType(seatType: SeatTypes) = dates.sumOf { it.getReservedSeatsOf(seatType) }
    fun totalTicketsSold() = facility.getAllSeatTypes().sumOf { ticketsSoldOfSeatType(it) }
    fun totalSales() = facility.getAllSeatTypes().sumOf { fullTicketPrice(it) * ticketsSoldOfSeatType(it) }
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
