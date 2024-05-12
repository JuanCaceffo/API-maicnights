package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface SeatRepository: CrudRepository<Seat, Long> {
    fun findSeatByTypeIs(type: SeatTypes): Optional<Seat>
}