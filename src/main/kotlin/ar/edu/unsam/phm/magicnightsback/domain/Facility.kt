package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps

abstract class Facility(
    val name: String,
    val location: Location
) : RepositoryProps() {
    abstract val fixedPrice: Double

    open fun fixedCost(): Double = fixedPrice + fixedCostVariant()

    open fun fixedCostVariant(): Double = 0.0
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

class Stadium(
    name: String,
    location: Location,
    val upperLevelSeatingCapacity: Int, // Platea alta
    val fieldCapacity: Int, // Campo
    val boxCapacity: Int, // Palco
    override val fixedPrice: Double,
) : Facility(name, location) {

    fun upperLevelSeatingCost() = 10000
    fun fieldCost() = 15000
    fun boxCost() = 20000
}

class Theater(
    name: String,
    location: Location,
    val pullmanCapacity: Int,
    val lowerLevelSeatingCapacity: Int, // Platea baja
    val hasGoodAcoustics: Boolean = false
) : Facility(name, location) {

    override val fixedPrice: Double = 100000.0

    override fun fixedCostVariant(): Double = if (hasGoodAcoustics) 50000.0 else 0.0
}

