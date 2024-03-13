package ar.edu.unsam.phm.magicnightsback.domain

abstract class Show(val band: Band, val facility: Facility, val valueOfTimesCanBeRepeated: Int) {
    abstract val nameOfEvent: String
    var rentabilty: rentabilityType = BasePrice()
    fun getBandName() = band.name
    fun costOfTheShow(): Double = band.price + facility.fixedCost()
}

class Tour(val name: String, band: Band, facility: Facility, valueOfTimesCanBeRepeated: Int) : Show(
    band, facility,
    valueOfTimesCanBeRepeated
) {
    override val nameOfEvent = name
}

class Concert(val name: String, band: Band, facility: Facility, valueOfTimesCanBeRepeated: Int) :
    Show(band, facility, valueOfTimesCanBeRepeated) {
    override val nameOfEvent = name
}

interface rentabilityType {
    fun getRentability(): Double
}

class BasePrice : rentabilityType {
    override fun getRentability(): Double = 0.8
}

class FullSale : rentabilityType {
    override fun getRentability(): Double = 1.0
}

class MegaShow : rentabilityType {
    override fun getRentability(): Double = 1.3
}
