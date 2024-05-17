package ar.edu.unsam.phm.magicnightsback.data.interfaces

import java.util.*

interface CustomCrudRepository<T> {
    fun findByNameEquals(name: String): Optional<T>
    fun findByNameLike(name: String): Optional<T>
}