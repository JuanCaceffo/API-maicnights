package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.ShowDateError
import jakarta.persistence.*
import java.time.LocalDate
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
    var dates = mutableSetOf<ShowDate>()

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    var rentabilityType: Rentability = Rentability.BASE_PRICE

    @ElementCollection(fetch = FetchType.LAZY)
    val pendingAttendeesIds = mutableListOf<Long>()
    fun geoLocationString() = facility.location.toGeolocation()

    // Cost methods
    fun baseCost(): Double = (band.cost) + (facility.cost())
    fun baseTicketPrice(seat: Seat): Double =
        (facility.let { baseCost() / it.getTotalSeatCapacity() }) + seat.price

    fun ticketPrice(seat: Seat): Double = (baseTicketPrice(seat) * rentabilityType.factor).truncate()
    fun allTicketPrices() = facility.places.map { ticketPrice(it.seat) }

    // ShowDate methods
    fun getSeatTypes() = facility.places.map { it.seat }
    fun getShowDateById(showDateId: Long) = dates.find { it.id == showDateId }

    fun initialDates(newDates: List<LocalDateTime>) {
        newDates.forEach {
            dates.add(ShowDate(it,facility))
        }
    }

    fun addDate(date: LocalDateTime): ShowDate {
        validNewDate(date.toLocalDate())
        val showDate = ShowDate(date, facility)
        dates.add(showDate)
        return showDate
    }

    // Admin Methods
    fun sales(): List<Double> = facility.places.map { ticketPrice(it.seat) * totalTicketsSoldOf(it.seat) }
    fun totalSales(): Double = sales().sumOf { it }
    fun totalTicketsSoldOf(seat: Seat) = dates.sumOf { it.getReservedSeatsOf(seat) }
    fun totalTicketsSold() = getSeatTypes().sumOf { totalTicketsSoldOf(it) }
    fun totalPendingAttendees() = pendingAttendeesIds.size
    fun rentability() = (((totalSales() - baseCost()) / totalSales()) * 100).coerceAtLeast(0.0)
    fun changeRentability(newShowStatus: Rentability) {
        this.rentabilityType = newShowStatus
    }

    fun newDateAvailable(show: Show) = PivotStats.stats.all { it.newDateCondition(show) }
    fun getAllStats(show: Show) = PivotStats.stats.map { it.getStat(show) }

   fun friendsAttendeesProfileImages(user: User) = friendsAttending(user).map { it.profileImgUrl }
   fun friendsAttending(user: User) = allAttendees().filter { it.isMyFriend(user) }

    fun allDates() = dates.map { it.date }.toList().sortedBy { it }

    fun allAttendees() = dates.flatMap { it.attendees }
    fun soldOutDates() = dates.filter { it.isSoldOut() }.size

    //Validations
    fun validNewDate(date: LocalDate) {
        if (date < LocalDate.now()) throw BusinessException(ShowDateError.INVALID_DATE)
        if (dates.any { it.date.toLocalDate() == date }) throw BusinessException(ShowDateError.DATE_ALREADY_EXISTS)
        if (!newDateAvailable(this)) throw BusinessException(ShowDateError.NEW_SHOW_INVALID_CONDITIONS)
    }
}