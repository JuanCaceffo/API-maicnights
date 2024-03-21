package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps

abstract class Show(
    val band: Band,
    val facility: Facility,
    val name: String
) : RepositoryProps() {
    var rentability: RentabilityType = BasePrice()
    abstract fun changeRentability(newShowStatus: RentabilityType)
    abstract fun cost(): Double
    abstract fun opinions() : MutableList<Opinion>
    fun totalRating(): Double = opinions().sumOf { it.rating.toDouble() } / opinions().size
}

class Concert(
    name: String,
    band: Band,
    facility: Facility
) : Show(
    band, facility, name
) {
    val availableSeats: MutableMap<SeatTypes, Int> = facility.seatCapacity.toMutableMap()
    val attendees = mutableListOf<User>()
    val pendingAttendees = mutableListOf<User>()
    fun baseCost(): Double = band.cost + facility.fixedCost()
    override fun changeRentability(newShowStatus: RentabilityType) {
        this.rentability = newShowStatus
    }

    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun cost(): Double = baseCost() * rentability.getRentability()
    fun baseTicketPrice() = cost() / availability()
    fun availability() = availableSeats.values.sum()
    fun fullTicketPrice(seatType: SeatTypes) = baseTicketPrice() + seatType.price
    fun availableSeatsOf(seatType: SeatTypes) = availableSeats[seatType]

    fun reserveSeat(seatType: SeatTypes, quantity: Int) {
        availableSeats[seatType] = availableSeats[seatType]!! - quantity
    }

    fun releaseSeat(seatType: SeatTypes, quantity: Int) {
        availableSeats[seatType] = availableSeats[seatType]!! + quantity
    }

    fun addAttendee(user: User) {
        attendees.add(user)
    }

    fun addPendingAttendee(user: User) {
        pendingAttendees.add(user)
    }

    override fun opinions(): MutableList<Opinion> {
        return attendees.flatMap { it.opinions }
            .filter { it.band == this.band }
            .toMutableList()
    }
}

class Tour(
    name: String,
    band: Band,
    facility: Facility
) : Show(
    band, facility, name
) {
    private val concerts = mutableListOf<Concert>()
    override fun cost(): Double = concerts.sumOf { it.cost() }
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun changeRentability(newShowStatus: RentabilityType) {
        concerts.forEach { it.rentability = newShowStatus }
    }

    fun addConcert(concert: Concert) {
        concerts.add(concert)
    }

    fun removeConcert(concert: Concert) {
        concerts.remove(concert)
    }

    override fun opinions(): MutableList<Opinion> = concerts.flatMap { it.opinions() }.toMutableList()
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
