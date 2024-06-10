package ar.edu.unsam.phm.magicnightsback.domain.interfaces

import org.springframework.data.jpa.repository.EntityGraph
import java.util.*

interface CustomCrudRepository<T> {
    fun findByName(name: String): Optional<T>
    fun findByNameIsContainingIgnoreCase(name: String): Iterable<T>
}