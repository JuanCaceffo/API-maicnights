//package ar.edu.unsam.phm.magicnightsback.domain
//
//class Filter<T>{
//    private val condiciones = mutableListOf<CondicionesFiltrado<T>>()
//
//    fun addFilterCondition(condicion:CondicionesFiltrado<T>){
//        condiciones.add(condicion)
//    }
//    fun apply(elemento: T) = condiciones.all{ it.filter(elemento)}
//}
//
//interface CondicionesFiltrado<T>{
//    fun filter(elemento: T): Boolean
//}
//
//class BandFilter(private val keyword: String) : CondicionesFiltrado<Show>{
//    override fun filter(elemento: Show): Boolean {
//        return if (keyword != "") {
//            return Comparar.parcial(keyword, listOf(elemento.band.name))
//        } else {
//            true
//        }
//    }
//}
//
//class FacilityFilter(private val keyword: String) : CondicionesFiltrado<Show>{
//    override fun filter(elemento: Show): Boolean {
//        return if (keyword != "") {
//            return Comparar.parcial(keyword, listOf(elemento.facility.name))
//        } else {
//            true
//        }
//    }
//}
//
//class WithFriends(private val condition:Boolean, private val userId: Long) : CondicionesFiltrado<Show> {
//    override fun filter(elemento: Show): Boolean {
//        return if(condition){
//            elemento.friendsAttending(userId).isNotEmpty()
//        } else {
//            true
//        }
//    }
//}