package ar.edu.unsam.phm.magicnightsback.domain


import ar.edu.unsam.phm.magicnightsback.data.constants.ColumnLength
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import ar.edu.unsam.phm.magicnightsback.error.ShowDateError
import ar.edu.unsam.phm.magicnightsback.utils.validateIsNotBefore
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

//@Entity
//class ShowDate(
//    @Column
//    val date: LocalDateTime,
//    @ManyToOne
//    private val show: Show
//) {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long = 0
//
//    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
//    val attendees = mutableSetOf<User>()
//
//    @ElementCollection(fetch = FetchType.LAZY)
//    val reservedSeats = show.facility.validSeatTypes().associateWith { 0 }.toMutableMap()
//
//    fun addAttendee(user: User) {
//        attendees.add(user)
//    }
//
//    fun reserveSeat(seat: SeatTypes, quantity: Int) {
//        quantity.throwIfGreaterThan(availableSeatsOf(seat), ShowDateError.EXCEEDED_CAPACITY)
//        reservedSeats[seat.name] = (reservedSeats[seat.name]!! + quantity)
//    }
//
//    fun releaseSeat(seat: SeatTypes, quantity: Int) {
//        reservedSeats[seat.name] = (reservedSeats[seat.name]!! - quantity)
//    }
//
//    fun getReservedSeatsOf(seat: SeatTypes) = reservedSeats[seat.name] ?: 0
//
//    fun getAllReservedSeats() = reservedSeats.map { it.value }.sum()
//
//    fun availableSeatsOf(seat: SeatTypes): Int {
//        return show.facility.getPlaceCapacity(seat) - getReservedSeatsOf(seat)
//    }
//
//    fun totalAvailableSeats(): Int {
//        return show.facility.getTotalSeatCapacity() - getAllReservedSeats()
//    }
//
//    fun datePassed(): Boolean = date.isBefore(LocalDateTime.now())
//
//    fun isAttendee(user: User) = attendees.any { it.id == user.id }
//
//    fun isSoldOut() = totalAvailableSeats() == 0
//}

@Entity
data class ShowDate(
    @ManyToOne
    val show: Show,
    val date: LocalDateTime
) {
    @Id
    val id: Long = 0

//    @OneToMany(mappedBy = "showDate", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
//    private val tickets: MutableSet<Ticket> = mutableSetOf()

    @Enumerated(EnumType.STRING)
    @Column(length = ColumnLength.SMALL)
    var rentability = Rentability.BASE_PRICE

    fun changeRentability(newRentability: Rentability) {
        rentability = newRentability
    }

    // Costs
    fun baseCost(): Double = (show.band.cost).plus(show.facility.cost)
    fun baseCostPerSeat(seat: SeatTypes) = baseCost() / seatCapacityOf(seat)
    fun baseSeatCost(seat: SeatTypes) = baseCostPerSeat(seat) + seat.price
    fun currentPrice(seat: SeatTypes) = (baseSeatCost(seat) * rentability.factor)


    // Availability
    fun seatCapacityOf(seatType: SeatTypes) =
        show.facility.seats[seatType]
            ?: throw IllegalArgumentException("SeatType not found")
    fun availableOf(seatType: SeatTypes, totalSold: Int) =
        seatCapacityOf(seatType)
            .minus(totalSold)
            //.notNegative(BusinessException(CreationError.NO_CAPACITY))

    // Validations
    fun validateReservation(ticket: Ticket, totalSold: Int): ShowDate {
        date.validateIsNotBefore()
        availableOf(ticket.seat, totalSold)
        return this
    }

}