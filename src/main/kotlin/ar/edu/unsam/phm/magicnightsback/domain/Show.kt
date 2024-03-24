package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.showError
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
//        dates.add(ShowDate(date,facility.seats.toMutableMap()))
        TODO ("Implementar nueva logica de facility")
    }
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

//TODO: when the logic of facility will changed pass an instance of facility instead of a "avilableSeats"
class ShowDate(val date: LocalDate, val availableSeats: MutableMap<SeatTypes, Int>): RepositoryProps(){

    //TODO: change te logic of the facility to allow add this methods
    fun reserveSeat(seatType: SeatTypes, quantity: Int) {
        availableSeats[seatType] = (availableSeats[seatType]!! - quantity).throwErrorIfNegative(BusinessException(showError.MSG_SETS_UNAVILABLES))
    }
    fun releaseSeat(seatType: SeatTypes, quantity: Int) {
        availableSeats[seatType] = (availableSeats[seatType]!! + quantity)
    }
    fun avilableSetsOf(seatType: SeatTypes) = availableSeats[seatType]

    fun totalCapacity() = availableSeats.values.sum()
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
