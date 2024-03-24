package ar.edu.unsam.phm.magicnightsback.domain

interface ISeat {
    val capacity: Int

    fun price(): Double
}

abstract class SeatStadium(override val capacity: Int) : ISeat {

}

abstract class SeatTheater(override val capacity: Int) : ISeat {

}

// Platea alta
class UpperLevelSeating(override val capacity: Int) : SeatStadium(capacity) {
    override fun price(): Double = 10000.0
}

//Campo
class Field(override val capacity: Int) : SeatStadium(capacity) {
    override fun price(): Double = 15000.0
}

// Palco
class Box(override val capacity: Int) : SeatStadium(capacity) {
    override fun price(): Double = 20000.0
}

class Pullman(override val capacity: Int) : SeatTheater(capacity) {
    override fun price(): Double = 10000.0
}

//Platea baja
class LowerLevelSeating(override val capacity: Int) : SeatTheater(capacity) {
    override fun price(): Double = 15000.0
}


