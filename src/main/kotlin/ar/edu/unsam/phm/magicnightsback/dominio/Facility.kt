package ar.edu.unsam.phm.magicnightsback.dominio

abstract class Facility (
    val name: String,
    val location: Location
){
    abstract fun fixedCos(): Float
}

class Stadium(
    name: String,
    location: Location,
    val upperLevelSeatingCapacity: Int, // Platea alta
    val fieldCapacity: Int, // Campo
    val boxCapacity: Int, // Palco
    private val fixedCost: Float,
): Facility(name, location) {

    override fun fixedCos() = fixedCost

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

    override fun fixedCos(): Float {
        var fixedCost = 100000.0F

        if(hasGoodAcoustics) {
            fixedCost += 50000
        }

        return fixedCost
    }
}

