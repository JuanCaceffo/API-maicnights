package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.validateOptionalIsNotNull
import ar.edu.unsam.phm.magicnightsback.dto.TicketDTO
import ar.edu.unsam.phm.magicnightsback.dto.TicketResult
import ar.edu.unsam.phm.magicnightsback.dto.toTicketDTO
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TicketService {

//    @Autowired lateinit var showRepository: ShowRepository
//    @Autowired lateinit var commentService: CommentService
//
//
//    /*Mapeo todos los tickets en uno solo por showDate juntando el precio total*/
//    @Transactional(Transactional.TxType.NEVER)
//    fun getTicketsGroupedByShowDate(user: User, ticketList: List<Ticket>): List<TicketDTO> {
//
//        val distinctTickets = ticketList.distinctBy { it.showDate.id }
//        return distinctTickets.map { uniqueTicket ->
//            val ticketsSameShowDate = ticketList.filter { ticket -> ticket.showDate.id == uniqueTicket.showDate.id }
//            val totalPrice = ticketsSameShowDate.sumOf { ticket -> ticket.price }
//            val quantity = ticketsSameShowDate.sumOf { ticket -> ticket.quantity }
//            val commentsStats = commentService.getCommentStadisticsOfShow(uniqueTicket.show.id)
//            val canBeCommented = commentService.canBeCommented(uniqueTicket.showDate, user,uniqueTicket.show)
//            uniqueTicket.toTicketDTO(commentsStats, user, totalPrice, quantity, canBeCommented)
//        }
//    }
//
//    @Transactional(Transactional.TxType.NEVER)
//    fun getTickets(user: User,ticketResults: List<TicketResult>): List<TicketDTO> {
//        val tickets = ticketResults.map {
//                ticketResult ->
//            val show = ticketResult.getShowId()?.let { validateOptionalIsNotNull(showRepository.findById(it)) } ?: throw BusinessException("El show id ingresado es null")
//            Ticket(
//                show,
//                show.getShowDateById(ticketResult.getShowDateId()),
//                SeatTypes.valueOf(ticketResult.getSeat()),
//                ticketResult.getQuantity()
//            ).apply {
//                id = ticketResult.getTicketId()
//            }
//        }
//        return getTicketsGroupedByShowDate(user,tickets)
//    }
}