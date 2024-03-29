package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import ar.edu.unsam.phm.magicnightsback.serializers.View
import com.fasterxml.jackson.annotation.JsonView
import java.time.LocalDateTime

class Show(
    @JsonView(View.Iterable.Show.Plain::class, View.Iterable.Show.Purchased::class)
    val name: String,
    val band: Band,
    val facility: Facility,
) : Iterable() {

    @JsonView(View.Iterable.Show.Plain::class, View.Iterable.Show.Purchased::class)
    var showImg = "$name.jpg"

    var rentability: RentabilityType = BasePrice()
    val attendees = mutableListOf<User>()
    val pendingAttendees = mutableListOf<User>()

    @JsonView(View.Iterable.Show.Plain::class, View.Iterable.Show.Purchased::class)
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
    fun addDate(date: LocalDateTime) {
        dates.add(ShowDate(date, facility.getAllSeatTypes()))
    }
    fun availableSeatsOf(date: LocalDateTime, seatType: SeatTypes): Int {
        return facility.getSeatCapacity(seatType) - (getShowDate(date)?.getReservedSeatsOf(seatType) ?: 0)
    }
    fun totalAvailableDateSeatsOf(date: LocalDateTime): Int {
        return facility.getTotalSeatCapacity() - (getShowDate(date)?.getAllReservedSeats() ?: 0)
    }
    fun getShowDate(date: LocalDateTime) = dates.find{ it.date == date}
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
    @JsonView(View.Iterable.Show.Plain::class, View.Iterable.Show.Purchased::class)
    fun totalComments() = comments().size

    fun comments(): MutableList<Comment> {
        return attendees.flatMap { it.comments }
            .filter { it.band == this.band }
            .toMutableList()
    }

    @JsonView(View.Iterable.Show.Plain::class, View.Iterable.Show.Purchased::class)
    fun totalRating(): Double = comments().sumOf { it.rating.toDouble() } / comments().size

    fun hasAvailableDates() = dates.any{ it.date >= LocalDateTime.now() }
}

class ShowDate(val date: LocalDateTime, seats: List<SeatTypes>): Iterable(){
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
