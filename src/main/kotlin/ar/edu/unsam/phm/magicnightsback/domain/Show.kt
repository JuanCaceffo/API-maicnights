package ar.edu.unsam.phm.magicnightsback.domain

abstract class Show(
    val band: Band,
    val facility: Facility,
    val valueOfTimesCanBeRepeated: Int
) {
    abstract val nameOfEvent: String
    val seats: MutableMap<SeatTypes, Int> = facility.seatCapacity.toMutableMap()
    var rentabilty: rentabilityType = BasePrice()
    private fun availability() = seats.values.sum()
    private fun baseTicketPrice() = cost() / availability()
    fun getBandName() = band.name
    fun cost(): Double = band.price + facility.fixedCost()
    fun fullTicketPrice(seatType: SeatTypes) = baseTicketPrice() + facility.seatPrice[seatType]!!
    fun availableSeatsOf(seatType: SeatTypes) = seats[seatType]
    fun reserveSeat(seatType: SeatTypes) {
        seats[seatType] = seats[seatType]!! - 1
    }
    fun releaseSeat(seatType: SeatTypes) {
        seats[seatType] = seats[seatType]!! + 1
    }
}

class Tour(
    name: String,
    band: Band,
    facility: Facility,
    valueOfTimesCanBeRepeated: Int
) : Show(
    band, facility,
    valueOfTimesCanBeRepeated
) {
    override val nameOfEvent = name
}

class Concert(
    name: String,
    band: Band,
    facility: Facility,
    valueOfTimesCanBeRepeated: Int
) : Show(
    band, facility,
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
