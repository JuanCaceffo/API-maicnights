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
    private val dates: MutableList<ShowDate> = mutableListOf()

    fun baseCost(): Double = band.cost + facility.fixedCost()
    fun changeRentability(newShowStatus: RentabilityType) {
        this.rentability = newShowStatus
    }

    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }

    fun cost(): Double = baseCost() * rentability.getRentability()

    //TODO: Preguntar si el negocio pide que dividamos por la capcidad total o por los asientos disponibles por funcion
    //Parte del enunciado:
    /*Por una parte tenemos el costo de una entrada y el precio de la misma. El costo de cada entrada
    se calcula como el costo fijo de la locación y de la banda (varía para cada show), todo esto dividido
    la cantidad de plazas totales para acceder al concierto. Adicionalmente se suma más el costo diferencial
    de cada ubicación. */
    fun baseTicketPrice() = cost() / facility.fullCapacity()
    fun fullTicketPrice(seatType: SeatTypes) = baseTicketPrice() + seatType.price
    fun addDate(date: LocalDate) {
        dates.add(ShowDate(date,facility.seatCapacity.toMutableMap()))
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

    fun opinions(): MutableList<Opinion> {
        return attendees.flatMap { it.opinions }
            .filter { it.band == this.band }
            .toMutableList()
    }

    fun totalRating(): Double = opinions().sumOf { it.rating.toDouble() } / opinions().size
}

//TODO: when the logic of facility will changed pass an instance of facility instead of a "avilableSeats"
class ShowDate(val date: LocalDate, val availableSeats: MutableMap<SeatTypes, Int>): RepositoryProps(){

    //TODO: change te logic of the facility to allow add this methods
    fun reserveSeat(seatType: SeatTypes, quantity: Int) {
        availableSeats[seatType] = availableSeats[seatType]!! - quantity
    }
    fun releaseSeat(seatType: SeatTypes, quantity: Int) {
        availableSeats[seatType] = availableSeats[seatType]!! + quantity
    }
    fun avilableSetsOf(seatType: SeatTypes) = availableSeats[seatType]

    fun fullCapacity() = availableSeats.values.sum()
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
