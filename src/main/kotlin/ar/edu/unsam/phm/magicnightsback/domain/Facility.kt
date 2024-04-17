package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import jakarta.annotation.Nullable
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
abstract class Facility(
    @Column(length = 100)
    val name: String,
    @Embedded
    val location: Point
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val places: MutableSet<Place> = mutableSetOf()

    @Transient
    lateinit var validSeatTypes: List<String>

    abstract var fixedPrice: Double
    abstract fun fixedCostVariant(): Double
    fun cost() = fixedPrice + fixedCostVariant()

    fun addPlace(seat: Seat, capacity: Int) {
        validateSeatType(seat.name)
        places.add(Place(seat, capacity = capacity))
    }

    private fun getPlaceBySeatType(seatType: SeatTypes) = places.find { it.seat.type == seatType }
    fun getPlaceCapacity(seatType: SeatTypes) = getPlaceBySeatType(seatType)?.let { it.capacity } ?: 0

    fun getTotalSeatCapacity() = places.sumOf { it.capacity }
    fun validateSeatType(name: String) {
        if (name !in validSeatTypes) {
            throw BusinessException(FacilityError.INVALID_SEAT_TYPE)
        }
    }
}

@Entity
@DiscriminatorValue("Stadium")
class Stadium(
    name: String,
    location: Point,
    override var fixedPrice: Double
) : Facility(name, location) {
    init {
        validSeatTypes = listOf(SeatTypes.BOX.name, SeatTypes.UPPERLEVEL.name, SeatTypes.FIELD.name)
        require(fixedPrice >= 0) { throw BusinessException(FacilityError.NEGATIVE_PRICE) }
    }

    override fun fixedCostVariant(): Double = 0.0
}

@Entity
@DiscriminatorValue("Theater")
class Theater(name: String, location: Point) : Facility(name, location) {
    init {
        validSeatTypes = listOf(SeatTypes.PULLMAN.name, SeatTypes.LOWERLEVEL.name)
    }

    @Nullable
    var hasGoodAcoustics: Boolean = false
    override var fixedPrice: Double = 100000.0
    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}
