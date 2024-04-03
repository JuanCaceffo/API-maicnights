package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository

class Filter<T>{
    private val condiciones = mutableListOf<CondicionesFiltrado<T>>()

    fun addFilterCondition(condicion:CondicionesFiltrado<T>){
        condiciones.add(condicion)
    }
    fun apply(elemento: T) = condiciones.all{ it.filter(elemento)}
}

interface CondicionesFiltrado<T>{
    fun filter(elemento: T): Boolean
}

class BandFilter(private val keyword: String, val repository: ShowRepository) : CondicionesFiltrado<Show>{
    override fun filter(elemento: Show): Boolean {
        return if (keyword != "") {
            return elemento in repository.elements.values.filter{ it.band.validSearchCondition(keyword) }
        } else {
            true
        }
    }
}

class FacilityFilter(private val keyword: String, val repository: ShowRepository) : CondicionesFiltrado<Show>{
    override fun filter(elemento: Show): Boolean {
        return if (keyword != "") {
            return elemento in repository.elements.values.filter{ it.facility.validSearchCondition(keyword) }
        } else {
            true
        }
    }
}

class WithFriends(private val condition:Boolean, private val userId: Long) : CondicionesFiltrado<Show> {
    override fun filter(elemento: Show): Boolean {
        return if(condition){
            elemento.friendsAttending(userId).isNotEmpty()
        } else {
            true
        }
    }
}