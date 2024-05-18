package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.service.TicketService

class Filter<T> {
    private val condiciones = mutableListOf<CondicionesFiltrado<T>>()

    fun addFilterCondition(condicion: CondicionesFiltrado<T>) {
        condiciones.add(condicion)
    }

    fun apply(elemento: T): Boolean {
        val uno = condiciones[0].filter(elemento)
        val dos = condiciones[1].filter(elemento)
        val tres = condiciones[2].filter(elemento)

        return condiciones.all { it.filter(elemento) }
    }
}

interface CondicionesFiltrado<T> {
    fun filter(elemento: T): Boolean
}

abstract class IdBaseFilter<T>(private val ids: List<Long>?) : CondicionesFiltrado<T> {
    protected abstract fun getId(element: T): Long

    override fun filter(element: T): Boolean {
        return ids?.let { it.any { id -> getId(element) == id } } ?: true
    }
}

class BandFilter(ids: List<Long>) : IdBaseFilter<Show>(ids) {
    override fun getId(show: Show): Long = show.band.id
}

class FacilityFilter(ids: List<Long>) : IdBaseFilter<Show>(ids) {
    override fun getId(show: Show): Long = show.facility.id
}


class WithFriends(private val userId: Long, private val ticketService: TicketService) : CondicionesFiltrado<Show> {
    override fun filter(show: Show): Boolean {
        return ticketService.countFriendsAttendingToShow(show.id, userId) > 0
    }
}