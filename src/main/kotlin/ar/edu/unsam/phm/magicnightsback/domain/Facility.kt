package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.data.constants.ColumnLength
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.FacilityError
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import jakarta.annotation.Nullable
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
abstract class Facility(
    @Column(length = ColumnLength.MEDIUM, nullable = false, unique = true)
    var name: String,
    @Embedded
    var location: Point,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "facility_seats",
        joinColumns = [JoinColumn(name = "facility_id")],
        inverseJoinColumns = [JoinColumn(name = "seat_id")]
    )
    val seats: List<Seat>
) {
    init {
        seats.forEach { validateSeatType(it) }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    abstract var fixedPrice: Double
    fun fixedCostVariant(): Double = 0.0
    fun cost() = fixedPrice + fixedCostVariant()
    fun totalCapacity() = seats.sumOf { it.maxCapacity }
    abstract fun validSeatTypes(): List<SeatTypes>
    fun validateSeatType(seat: Seat) {
        if (seat.type !in validSeatTypes()) {
            throw BusinessException(FindError.INVALID_SEAT_TYPE(this::class.toString()))
        }
    }
}

@Entity
@DiscriminatorValue("Stadium")
class Stadium(
    name: String,
    location: Point,
    seats: List<Seat>,
    @Column(nullable = false)
    override var fixedPrice: Double
) : Facility(name, location, seats) {
    init {
        require(fixedPrice >= 0) { throw BusinessException(FacilityError.NEGATIVE_PRICE) }
    }

    override fun validSeatTypes() = listOf(SeatTypes.BOX, SeatTypes.UPPERLEVEL, SeatTypes.FIELD)

    override fun fixedCostVariant(): Double = 0.0
}

@Entity
@DiscriminatorValue("Theater")
class Theater(
    name: String,
    location: Point,
    seats: List<Seat>,
    @Nullable
    var hasGoodAcoustics: Boolean = false
) : Facility(name, location, seats) {
    override fun validSeatTypes() = listOf(SeatTypes.PULLMAN, SeatTypes.LOWERLEVEL)

    override var fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}
