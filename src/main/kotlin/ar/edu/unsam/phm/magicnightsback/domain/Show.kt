package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.enums.Rentability
import ar.edu.unsam.phm.magicnightsback.utils.removeSpaces
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document


@Document("Shows")
data class Show(
    val name: String,
    val bandId: Long,
    val facilityId: Long
) {
    @Id
    lateinit var id: String
    var rentability = Rentability.BASE_PRICE
    @Transient
    lateinit var band: Band
    @Transient
    lateinit var facility: Facility
    var pendingAttendees = 0
    var clicks_quantity: Long = 0L

// Seat methods
    fun haveSeat(seat: Seat) = facility.seats.any { it.id == seat.id }
    fun currentTicketPrice(seat: Seat) = baseSeatCost(seat) * rentability.factor

    fun baseCost() = (band.cost).plus(facility.cost())
    fun imgUrl() = "${band.name.removeSpaces().lowercase()}.jpg"
    fun addPendingAttendee() { pendingAttendees += 1 }
    private fun baseCostPerSeat() = baseCost() / facility.totalCapacity()
    private fun baseSeatCost(seat: Seat) = baseCostPerSeat() + seat.price
    fun allTicketPrices() = facility.seats.map { currentTicketPrice(it) }
    fun changeRentability(newRentability: Rentability) {
        rentability = newRentability
    }

    fun totalCapacity() = facility.totalCapacity()
    fun addClick() { clicks_quantity += 1 }
}
