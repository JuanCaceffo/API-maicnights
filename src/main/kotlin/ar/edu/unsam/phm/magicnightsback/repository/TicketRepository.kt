package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import java.util.*

interface TicketRepository: CrudRepository<Ticket, Long> {
    fun findByDateIs(date: LocalDateTime): Optional<Ticket>
    fun findByUserId(userId: Long): Iterable<Ticket>
//    fun findByUserIdAndStatusIs(userId: UUID, status: TicketStatus): Iterable<Ticket>
    fun countBySeatAndShowDateId(seat:SeatTypes, showDateId: Long) : Int
}