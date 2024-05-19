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

    fun imgUrl() = "${band.name.removeSpaces().lowercase()}.jpg"
    // Seat methods
    fun haveSeat(seat: Seat) = facility.seats.any { it.id == seat.id }
    fun currentTicketPrice(seat: Seat) = baseSeatCost(seat) * rentability.factor
    private fun baseCost(): Double = (band.cost).plus(facility.cost())
    private fun baseCostPerSeat() = baseCost() / facility.totalCapacity()
    private fun baseSeatCost(seat: Seat) = baseCostPerSeat() + seat.price
    fun allTicketPrices() = facility.seats.map { currentTicketPrice(it) }
    fun changeRentability(newRentability: Rentability) {
        rentability = newRentability
    }
}
