package ar.edu.unsam.phm.magicnightsback.domain.interfaces

import java.util.*

interface CustomCrudRepository<T> {
    fun findByNameEquals(name: String): Optional<T>
    fun findByNameIsContainingIgnoreCase(name: String): Iterable<T>
}