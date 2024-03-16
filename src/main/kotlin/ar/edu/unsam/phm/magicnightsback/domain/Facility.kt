package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps

abstract class Facility(
    val name: String,
    val location: Location,
    open val seats: Collection<ISeat>
) : RepositoryProps() {
    abstract val fixedPrice: Double

    open fun fixedCost(): Double = fixedPrice + fixedCostVariant()

    open fun fixedCostVariant(): Double = 0.0

    fun getSeatPrice(seatType: String?  = null): Double {
        var price: Double

        if(seatType != null) {
            price = seats.filter { it::class.simpleName == seatType }.first().price()
        }
        else {
            price = seats.sumOf { it.price() }
        }

        return price
    }

    fun getSeatCapacity(seatType: String? = null): Int {
        var capacity: Int

        if(seatType != null) {
            capacity = seats.filter { it::class.simpleName == seatType }.first().capacity
        }
        else {
            capacity = seats.sumOf { it.capacity }
        }

        return capacity
    }

    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

class Stadium(
    name: String,
    location: Location,
    override val seats: Collection<SeatStadium>,
    override val fixedPrice: Double,
) : Facility(name, location, seats) {

}

class Theater(
    name: String,
    location: Location,
    override val seats: Collection<SeatTheater>,
    val hasGoodAcoustics: Boolean = false,
) : Facility(name, location, seats) {

    override val fixedPrice: Double = 100000.0

    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}

