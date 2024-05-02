package ar.edu.unsam.phm.magicnightsback.interfaces

import org.springframework.data.jpa.repository.Query
import java.util.*

interface CustomCrudRepository<T> {
    fun findByName(name: String): Optional<T>
}