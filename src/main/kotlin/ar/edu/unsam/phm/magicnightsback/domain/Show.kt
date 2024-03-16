package ar.edu.unsam.phm.magicnightsback.domain

abstract class Show(
    val band: Band,
    val facility: Facility,
    val seats: MutableMap<SeatTypes, Int> = facility.seatCapacity,
    val valueOfTimesCanBeRepeated: Int
) {
    abstract val nameOfEvent: String

    var rentabilty: rentabilityType = BasePrice()
    fun getBandName() = band.name
    fun cost(): Double = band.price + facility.fixedCost()

    private fun baseTicketPrice() = cost() / availability()

    fun fullTicketPrice(seatType: SeatTypes) = baseTicketPrice() + facility.seatPrice[seatType]!!

    private fun availability() = seats.values.sum()

    fun availableSeatsOf(seatType: SeatTypes) = seats[seatType]
}

class Tour(
    name: String,
    band: Band,
    facility: Facility,
    seats: MutableMap<SeatTypes, Int>,
    valueOfTimesCanBeRepeated: Int
) : Show(
    band, facility, seats,
    valueOfTimesCanBeRepeated
) {
    override val nameOfEvent = name
}

class Concert(
    name: String,
    band: Band,
    facility: Facility,
    seats: MutableMap<SeatTypes, Int>,
    valueOfTimesCanBeRepeated: Int
) : Show(
    band, facility, seats,
    valueOfTimesCanBeRepeated
) {
    override val nameOfEvent = name
}

interface rentabilityType {
    fun getRentability(): Double
}

class BasePrice : rentabilityType {
    override fun getRentability(): Double = 0.8
}

class FullSale : rentabilityType {
    override fun getRentability(): Double = 1.0
}

class MegaShow : rentabilityType {
    override fun getRentability(): Double = 1.3
}
