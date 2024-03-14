package ar.edu.unsam.phm.magicnightsback.domain
abstract class Show (val band : Band, val facility: Facility, val valueOfTimesCanBeRepeated : Int) {
    abstract val nameOfEvent : String
    var showStatus : RentabilityType = BasePrice()
    fun getBandName() = band.name
    fun baseCost(): Double = band.cost + facility.fixedCost()
    fun cost() : Double = baseCost() * showStatus.getRentability()
    fun changeRentability(newShowStatus:RentabilityType){
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
interface RentabilityType{
    fun getRentability(): Double
}
class BasePrice : RentabilityType{
    override fun getRentability(): Double = 0.8
}
class FullSale: RentabilityType{
    override fun getRentability(): Double = 1.0
}
class MegaShow: RentabilityType{
    override fun getRentability(): Double = 1.3
}
