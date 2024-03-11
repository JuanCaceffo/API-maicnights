package ar.edu.unsam.phm.magicnightsback.domain

abstract class Facility (
    val name: String,
    val location: Location
){
    abstract val fixedPrice: Float

    open fun fixedCos(): Float = fixedPrice + fixedCostVariant()

    open fun fixedCostVariant(): Float = 0F
}

class Stadium(
    name: String,
    location: Location,
    val upperLevelSeatingCapacity: Int, // Platea alta
    val fieldCapacity: Int, // Campo
    val boxCapacity: Int, // Palco
    override val fixedPrice: Float,
): Facility(name, location) {

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
): Facility(name, location) {

    override val fixedPrice: Float = 100000f

    override fun fixedCostVariant(): Float = if (hasGoodAcoustics)  50000f else 0f
}

