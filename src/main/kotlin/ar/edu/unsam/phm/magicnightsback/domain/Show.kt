package ar.edu.unsam.phm.magicnightsback.domain


abstract class Show (val band : Band, val facility: Facility, val stadium: String) {
    val nameOfTheBand = band.name
    val nameOfStadium =  stadium
    abstract val nameOfEvent : String


    fun costOfTheShow() : Double = band.price + facility.fixedCos()
}

class Tour(val name: String, band: Band, facility: Facility, stadium: String) : Show(band, facility, stadium) {
    override val nameOfEvent = name

}

class Concert(val name: String, band: Band, facility: Facility, stadium: String): Show(band, facility, stadium) {
    override val nameOfEvent = name

}