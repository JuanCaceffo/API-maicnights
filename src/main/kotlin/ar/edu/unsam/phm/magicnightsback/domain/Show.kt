package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps
import java.time.LocalDate
class Show(
    val name: String,
    val band: Band,
    val facility: Facility,
) : RepositoryProps() {
    var rentability: RentabilityType = BasePrice()
    val attendees = mutableListOf<User>()
    val pendingAttendees = mutableListOf<User>()
    val dates: MutableList<ShowDate> = mutableListOf()

    fun baseCost(): Double = band.cost + facility.cost()
    fun changeRentability(newShowStatus: RentabilityType) {
        this.rentability = newShowStatus
    }
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
    fun cost(): Double = baseCost() * rentability.getRentability()

    fun baseTicketPrice() = cost() / facility.getTotalSeatCapacity()
    fun fullTicketPrice(seatType: SeatTypes) = baseTicketPrice() + seatType.price
    fun addDate(date: LocalDate) {
        dates.add(ShowDate(date, facility.getAllSeatTypes()))
    }
    fun availableSeatsOf(date: LocalDate, seatType: SeatTypes): Int {
        return facility.getSeatCapacity(seatType) - (getShowDate(date)?.getReservedSeatsOf(seatType) ?: 0)
    }
    fun totalAvailableDateSeatsOf(date: LocalDate): Int {
        return facility.getTotalSeatCapacity() - (getShowDate(date)?.getAllReservedSeats() ?: 0)
    }
    fun getShowDate(date: LocalDate) = dates.find{ it.date == date}
    fun getAllShowDates() = dates.map{it.date}
    fun removeDate(date: ShowDate) {
        dates.remove(date)
    }
    fun addAttendee(user: User) {
        attendees.add(user)
    }

    fun addPendingAttendee(user: User) {
        pendingAttendees.add(user)
    }

    fun comments(): MutableList<Comment> {
        return attendees.flatMap { it.comments }
            .filter { it.band == this.band }
            .toMutableList()
    }

    fun totalRating(): Double = comments().sumOf { it.rating.toDouble() } / comments().size
}

class ShowDate(val date: LocalDate, seats: List<SeatTypes>): RepositoryProps(){
    val reservedSeats = seats.associateWith { 0 }.toMutableMap()
    fun reserveSeat(seatType: SeatTypes, quantity: Int) {
        reservedSeats[seatType] = (reservedSeats[seatType]!! + quantity)
    }
    fun releaseSeat(seatType: SeatTypes, quantity: Int) {
        reservedSeats[seatType] = (reservedSeats[seatType]!! - quantity)
    }
    fun getReservedSeatsOf(seatType: SeatTypes) = reservedSeats[seatType] ?: 0

    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()
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
