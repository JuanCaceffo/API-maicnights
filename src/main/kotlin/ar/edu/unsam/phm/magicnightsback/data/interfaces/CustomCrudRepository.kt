package ar.edu.unsam.phm.magicnightsback.data.interfaces

import java.util.*

interface CustomCrudRepository<T> {
    fun findByName(name: String): Optional<T>
}