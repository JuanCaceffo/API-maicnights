package ar.edu.unsam.phm.magicnightsback.service


import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class TicketService(
    @Autowired
    private val ticketRepository: TicketRepository,
    @Autowired
    private val showDateService: ShowDateService,
    @Autowired
    private val userService: UserService
) {
    // Read methods
    @Transactional(Transactional.TxType.NEVER)
    fun findAll(): List<Ticket> = ticketRepository.findAll().map { it }

    @Transactional(Transactional.TxType.NEVER)
    fun findByUUID(id: Long): Ticket? =
        ticketRepository.findById(id).getOrNull()

    @Transactional(Transactional.TxType.NEVER)
    fun findOrErrorByUUID(id: Long): Ticket =
        findByUUID(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, Ticket::class.stringMe()))

    @Transactional(Transactional.TxType.NEVER)
    fun findByDate(date: LocalDateTime): Ticket? = ticketRepository.findByDateIs(date).getOrNull()

    @Transactional(Transactional.TxType.NEVER)
    fun findUserTicketsBy(userId: Long): List<Ticket> = ticketRepository.findByUserId(userId).map { it }

//    @Transactional(Transactional.TxType.NEVER)
//    fun findUserReservations(userId: Long): List<Ticket> =
//        ticketRepository.findByUserIdAndStatusIs(userId, TicketStatus.RESERVED).map { it }

    // Read count methods
    @Transactional(Transactional.TxType.NEVER)
    fun boughtBy(seatType: SeatTypes, showDateId: Long): Int =
        ticketRepository.countBySeatAndShowDateId(seatType, showDateId)

    // Create methods
    //@Transactional(Transactional.TxType.REQUIRED)
//    fun reserve(ticket: Ticket): Ticket {
//        val showDate = validateReservation(ticket)
//
//        return ticketRepository.save(
//            ticket.apply {
//                status = TicketStatus.RESERVED
//                price = showDate.currentPrice(ticket.seat)
//            }
//        )
//    }

//    @Transactional(Transactional.TxType.REQUIRED)
//    fun confirmBuy(userId: Long): Boolean {
//        val reservedTickets = findUserReservations(userId)
//        val total = reservedTickets.sumOf { it.price }
//        userService.modifyBalance(userId, total)
//        return reservedTickets.all { updateToPurchased(it).status == TicketStatus.BOUGHT }
//    }

//    @Transactional(Transactional.TxType.NEVER)
//    fun totalToPay(userId: Long) = findUserReservations(userId).sumOf { it.price }

    // Delete Methods
    @Transactional(Transactional.TxType.REQUIRED)
    fun deleteByUUID(id: Long) {
        val foundTicket = findOrErrorByUUID(id)
        ticketRepository.delete(foundTicket)
    }

//    @Transactional(Transactional.TxType.REQUIRED)
//    fun updateToPurchased(ticket: Ticket): Ticket {
//        ticket.updateToPurchased()
//        return ticketRepository.save(ticket)
//    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun validateReservation(ticket: Ticket): ShowDate {
        val showDate = showDateService
            .findShowDateOrErrorByUUID(ticket.showDateId)

        showDate.validateReservation(ticket, boughtBy(ticket.seat, ticket.showDateId))
        return showDate
    }
}
