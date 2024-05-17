package ar.edu.unsam.phm.magicnightsback.domain

class Filter<T> {
    private val condiciones = mutableListOf<CondicionesFiltrado<T>>()

    fun addFilterCondition(condicion: CondicionesFiltrado<T>) {
        condiciones.add(condicion)
    }

    fun apply(elemento: T) = condiciones.all { it.filter(elemento) }
}

interface CondicionesFiltrado<T> {
    fun filter(elemento: T): Boolean
}

abstract class IdBaseFilter<T>(private val id: Long?) : CondicionesFiltrado<T> {
    protected abstract fun getId(element: T): Long

    override fun filter(element: T): Boolean {
        return id?.let { getId(element) == it } ?: true
    }
}

class BandFilter(id: Long?) : IdBaseFilter<Show>(id) {
    override fun getId(show: Show): Long = show.band.id
}

class FacilityFilter(id: Long?) : IdBaseFilter<Show>(id) {
    override fun getId(show: Show): Long = show.facility.id
}


class WithFriends(private val friends: Set<User>?) : CondicionesFiltrado<Show> {
    override fun filter(show: Show): Boolean {
        return friends?.let { it.isNotEmpty() } ?: true
    }
}