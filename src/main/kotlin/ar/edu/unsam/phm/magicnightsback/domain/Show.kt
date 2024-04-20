package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.ShowError
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Show(
    @Column(length = 40)
    var name: String,

    @ManyToOne
    var band: Band,

    @ManyToOne
    var facility: Facility,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(length = 100)
    var imgUrl = "${band.name.removeSpaces().lowercase()}.jpg"

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val dates = mutableSetOf<ShowDate>()

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    var rentabilityType: Rentability = Rentability.BASE_PRICE

    @ElementCollection(fetch = FetchType.LAZY)
    val pendingAttendeesIds = mutableListOf<Long>()

    // Cost methods
    fun baseCost(): Double = (band.cost ?: 0.0) + (facility.cost() ?: 0.0)

    fun baseTicketPrice(seat: Seat): Double =
        (facility.let { baseCost() / it.getTotalSeatCapacity() } ?: 0.0) + seat.price

    fun ticketPrice(seat: Seat): Double = (baseTicketPrice(seat) * rentabilityType.factor).truncate()

    fun allTicketPrices() = facility.places.map { ticketPrice(it.seat) }

    // Admin Methods
    fun sales(): List<Double> = facility.places.map { ticketPrice(it.seat) * totalTicketsSoldOf(it.seat) }
    fun totalSales(): Double = sales().sumOf { it }
    fun totalTicketsSoldOf(seat: Seat) = dates.sumOf { it.getReservedSeatsOf(seat) }
    fun totalPendingAttendees() = pendingAttendeesIds.size
    fun rentability() = (((totalSales() - baseCost()) / totalSales()) * 100).coerceAtLeast(0.0)


    //fun canBeCommented(user: User) = !isAlreadyCommented(user) && anyShowDatesPassedFor(user)

//    private fun isAlreadyCommented(user: User): Boolean =
//        allAttendees().find { showUser -> showUser == user }?.comments?.any { comment -> comment.show == this }
//            ?: false

//    private fun anyShowDatesPassedFor(user: User) =
//        dates.filter { date -> date.attendees.contains(user) }.any { date -> date.datePassed() }

    fun changeRentability(newShowStatus: Rentability) {
        this.rentabilityType = newShowStatus
    }

    fun geoLocationString() = facility.location.toGeolocation()


//    fun validate() {
//        band?.let {} ?: throw BusinessException(ShowError.BAND_ERROR)
//        facility?.let {} ?: throw BusinessException(ShowError.FACILITY_ERROR)
//    }

    fun addDate(date: LocalDateTime) {
        dates.add(ShowDate(date, facility))
    }

//   fun friendsAttendeesProfileImages(user: User) = friendsAttending(user.id).map { it.profileImage }
//   fun friendsAttending(userId: Long) = allAttendees().filter { it.isMyFriend(userId) }

//   fun getSeatTypes() = facility.seats.map { it.seatType }

//   fun allTicketPrices() = facility.seats.map { ticketPrice(it.seatType) }

    fun allDates() = dates.map { it.date }.toList().sortedBy { it }

    fun allAttendees() = dates.flatMap { it.attendees }
    fun soldOutDates() = dates.filter { it.isSoldOut() }.size
//  fun ticketsSoldOfSeatType(seatType: SeatTypes) = dates.sumOf { it.getReservedSeatsOf(seatType) }
//  fun totalTicketsSold() = facility.getAllSeatTypes().sumOf { ticketsSoldOfSeatType(it) }
//    fun totalSales(): Double = facility.getAllSeatTypes().sumOf { ticketPrice(it) * ticketsSoldOfSeatType(it) }
//    fun getShowDate(date: LocalDate) = dates.find { it.date.toLocalDate() == date }

    //Validations
//    private fun validateComment(showDate: ShowDate) {
//        if (!showDate.datePassed()) {
//            throw BusinessException(showError.USER_CANT_COMMENT)
//        }
//    }
}