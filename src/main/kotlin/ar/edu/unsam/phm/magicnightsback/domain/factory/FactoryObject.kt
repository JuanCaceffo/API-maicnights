package ar.edu.unsam.phm.magicnightsback.domain.factory

import ar.edu.unsam.phm.magicnightsback.domain.*

interface FactoryObject<T> {
    fun build(): T
}

interface FactoryBulk<T> {
    fun buildBulk(quantity: Int): Set<T>
}

interface FactoryShowDate : FactoryObject<ShowDate>, FactoryBulk<ShowDate> {
    val show: Show
}

interface FactoryTickey : FactoryObject<Ticket> {
    val user: User
    val showDate: ShowDate
    val seat: Seat
}