package ar.edu.unsam.phm.magicnightsback.domain
abstract class Show (val band : Band, val facility: Facility, val valueOfTimesCanBeRepeated : Int) {
    abstract val nameOfEvent : String
    var showStatus : rentabilityType = BasePrice()
    fun getBandName() = band.name
    fun baseCost(): Double = band.price + facility.fixedCos()
    fun cost() : Double = baseCost() * showStatus.getRentability()
    fun changeRentability(newShowStatus:rentabilityType){
        this.showStatus = newShowStatus
    }
}

class Tour(val name: String, band: Band, facility: Facility, valueOfTimesCanBeRepeated: Int) : Show(band, facility,
    valueOfTimesCanBeRepeated) {
    override val nameOfEvent = name
}
class Concert(val name: String, band: Band, facility: Facility, valueOfTimesCanBeRepeated: Int): Show(band, facility,valueOfTimesCanBeRepeated) {
    override val nameOfEvent = name
}
interface rentabilityType{
    fun getRentability(): Double
}
class BasePrice : rentabilityType{
    override fun getRentability(): Double = 0.8
}
class FullSale: rentabilityType{
    override fun getRentability(): Double = 1.0
}
class MegaShow: rentabilityType{
    override fun getRentability(): Double = 1.3
}
