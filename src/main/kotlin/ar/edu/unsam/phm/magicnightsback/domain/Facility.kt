package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.data.constants.ColumnLength
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import jakarta.annotation.Nullable
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
abstract class Facility(
    @Column(length = ColumnLength.MEDIUM, nullable = false, unique = true)
    val name: String,
    @Embedded
    val location: Point
) {
    @Id
    var id: Long = 0

    var cost = 0.0

    abstract var fixedPrice: Double
    fun fixedCostVariant(): Double = 0.0

    @ElementCollection
    @CollectionTable(
        name = "seats",
        joinColumns = [JoinColumn(name = "facility_id", referencedColumnName = "id")]
    )
    @MapKeyColumn(name = "type")
    @Column(name = "quantity")
    val seats = mutableMapOf<SeatTypes, Int>()

    fun setSeatCapacity(seat: SeatTypes, capacity: Int) {
        validateSeatType(seat)
        seats[seat] = capacity
    }

    fun updateCost() {
        cost = fixedPrice + fixedCostVariant()
    }

    fun seatCapacityOf(seat: SeatTypes) = seats[seat]

    //TODO: validar si el asiento existe dentro de la facility
//    fun getPlaceBySeatName(seatName: String): Place {
//        validateSeatType(seatName)
//        return places.find { it.seatType.name == seatName }!!
//    }
//    fun getPlaceCapacity(seat: SeatTypes) = getPlaceBySeatName(seat.name).capacity

//    fun getTotalSeatCapacity() = places.sumOf { it.capacity }

    abstract fun validSeatTypes(): List<SeatTypes>

    private fun validateSeatType(seat: SeatTypes) {
        if (seat !in validSeatTypes()) {
            throw BusinessException(CreationError.INVALID_SEAT_TYPE(seat.name))
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
