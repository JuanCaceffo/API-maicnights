package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.data.constants.ColumnLength
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.CreationError
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
    var location: Point
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var cost = 0.0

    abstract var fixedPrice: Double
    fun fixedCostVariant(): Double = 0.0

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val seats = mutableSetOf<Seat>()

    @PrePersist
    @PreUpdate
    fun updateCost() {
        cost = fixedPrice + fixedCostVariant()
    }

    fun addSeat( seat: Seat) {
        validateSeatType(seat)
        seats.add(seat)
    }

    abstract fun validSeatTypes(): List<SeatTypes>

    private fun validateSeatType(seat: Seat) {
        if (seat.type !in validSeatTypes()) {
            throw BusinessException(CreationError.INVALID_SEAT_TYPE(this::class.toString()))
        }
        if (seat.capacity == 0) {
            throw BusinessException(FindError.ZERO_CAPACITY)
        }
    }
}

@Entity
@DiscriminatorValue("Stadium")
class Stadium(
    name: String,
    location: Point,
    @Column(nullable = false)
    override var fixedPrice: Double
) : Facility(name, location) {
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
    @Nullable
    var hasGoodAcoustics: Boolean = false
) : Facility(name, location) {
    override fun validSeatTypes() = listOf(SeatTypes.PULLMAN, SeatTypes.LOWERLEVEL)

    override var fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}
