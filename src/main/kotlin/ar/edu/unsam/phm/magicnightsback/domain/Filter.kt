package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository

class Filter<T>{
    private val condiciones = mutableListOf<CondicionesFiltrado<T>>()

    fun addFilterCondition(condicion:CondicionesFiltrado<T>){
        condiciones.add(condicion)
    }
    fun apply(elemento: T) = condiciones.all{ it.filtro(elemento)}
}

interface CondicionesFiltrado<T>{
    fun filtro(elemento: T): Boolean
}

class WordFilter(private val keyword: String, val repository: ShowRepository) : CondicionesFiltrado<Show>{
    override fun filtro(elemento: Show): Boolean {
        return if (keyword != "") {
            return elemento in repository.search(keyword)
        } else {
            true
        }
    }
}

//class WithFriends(private val condicion:Boolean, private val userId: Long) : CondicionesFiltrado<Show> {
//    override fun filtro(elemento: Show): Boolean {
//        return if(condicion){
//            elemento.friendsAttending(userId)
//        } else {
//            true
//        }
//    }
//}