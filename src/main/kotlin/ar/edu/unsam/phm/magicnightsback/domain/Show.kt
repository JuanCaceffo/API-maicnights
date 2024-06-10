package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.enums.Rentability
import ar.edu.unsam.phm.magicnightsback.utils.removeSpaces
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document


//
//    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
//    var dates = mutableSetOf<ShowDate>()
//
//    @Enumerated(EnumType.STRING)
//    @Column(length = 40)
//    var rentabilityType: Rentability = Rentability.BASE_PRICE
//
//    @ElementCollection(fetch = FetchType.LAZY)
//    val pendingAttendeesIds = mutableListOf<Long>()
//    fun geoLocationString() = facility.location.toGeolocation()
//
//    // Cost methods
//    fun baseCost(): Double = (band.cost) + (facility.cost())
//    fun baseTicketPrice(seat: SeatTypes): Double =
//        (facility.let { baseCost() / it.getTotalSeatCapacity() }) + seat.price
//
//    fun ticketPrice(seat: SeatTypes): Double = (baseTicketPrice(seat) * rentabilityType.factor).truncate()
//    fun allTicketPrices() = facility.places.map { ticketPrice(it.seatType) }
//
//    // ShowDate methods
//    fun getSeatTypes() = facility.places.map { it }
//    fun getShowDateById(showDateId: Long) =
//        dates.find { it.id == showDateId } ?: throw NotFoundException(ShowDateError.MSG_DATE_NOT_FOUND)
//
//    fun initialDates(newDates: List<LocalDateTime>) {
//        newDates.forEach {
//            dates.add(ShowDate(it, this))
//        }
//    }
//
//    fun addDate(date: LocalDateTime): ShowDate {
//        validNewDate(date.toLocalDate())
//        val showDate = ShowDate(date, this)
//        dates.add(showDate)
//        return showDate
//    }
//
//    // Admin Methods
//    fun sales(): List<Double> = facility.places.map { ticketPrice(it.seatType) * totalTicketsSoldOf(it.seatType) }
//    fun totalSales(): Double = sales().sumOf { it }
//    fun totalTicketsSoldOf(seat: SeatTypes) = dates.sumOf { it.getReservedSeatsOf(seat) }
//    fun totalTicketsSold() = getSeatTypes().sumOf { totalTicketsSoldOf(it.seatType) }
//    fun totalPendingAttendees() = pendingAttendeesIds.size
//    fun rentability() = (((totalSales() - baseCost()) / totalSales()) * 100).coerceAtLeast(0.0)
//    fun changeRentability(newShowStatus: Rentability) {
//        this.rentabilityType = newShowStatus
//    }
//
//    fun getAllStats(show: Show) = PivotStats.stats.map { it.getStat(show) }
//
//    //Friends Methods
//    fun friendsAttendeesProfileImages(user: User): List<String> = friendsAttending(user).map { it.profileImgUrl }
//
//    fun friendsAttending(user: User) = allAttendees().filter { user.isMyFriend(it) }
//
//    // Dates Methods
//    fun allDates() = dates.map { it.date }.toList().sortedBy { it }
//    fun allDatesWithIds() = dates.map { it.toShowDateDTO() }.sortedBy { it.date }
//    fun allAttendees() = dates.flatMap { it.attendees }.toSet()
//    fun soldOutDates() = dates.filter { it.isSoldOut() }.size


@Document("Shows")
data class Show(
    val name: String,
    val bandId: Long,
    val facilityId: Long
) {
    @Id
    lateinit var id: String
    var rentability = Rentability.BASE_PRICE
    var bandName = ""
    var facilityName = ""
    @Transient
    lateinit var band: Band
    @Transient
    lateinit var facility: Facility
    var pendingAttendees = 0
    var clicks_quantity: Long = 0L
    //TODO: evaluar posibles problemas con actualizacion de costos de banda y facility
    var cost: Double = 0.0

// Seat methods
    fun haveSeat(seat: Seat) = facility.seats.any { it.id == seat.id }
    fun currentTicketPrice(seat: Seat) = baseSeatCost(seat) * rentability.factor
    fun initBaseCost() {
        cost = (band.cost).plus(facility.cost())
    }
    fun imgUrl() = "${band.name.removeSpaces().lowercase()}.jpg"
    fun addPendingAttendee() { pendingAttendees += 1 }
    private fun baseCostPerSeat() = cost / facility.totalCapacity()
    fun clearPendingAttendees() {
        pendingAttendees = 0
    }
    private fun baseSeatCost(seat: Seat) = baseCostPerSeat() + seat.price
    fun allTicketPrices() = facility.seats.map { currentTicketPrice(it) }
    fun changeRentability(newRentability: Rentability) {
        rentability = newRentability
    }
    fun totalCapacity() = facility.totalCapacity()
    fun addClick() { clicks_quantity += 1 }
}
