package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import org.springframework.data.repository.CrudRepository
import java.util.*

interface SeatRepository : CrudRepository<Seat, Long> {
    fun findByName(seat: String): Optional<Seat>
}