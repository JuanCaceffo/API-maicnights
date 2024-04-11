package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.dto.TicketCreateDTO
import ar.edu.unsam.phm.magicnightsback.dto.TicketDTO
import ar.edu.unsam.phm.magicnightsback.error.NotFoundException
import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CartService(
    @Autowired val cartRepo: CartRepository,
    @Autowired val userRepo: UserRepository,
    @Autowired val showRepo: ShowRepository,
    @Autowired val userService: UserService
) {


    fun getTicketsCart(userId: Long): List<TicketDTO> {
        val cart = cartRepo.getCardFor(userId)
        val user = userRepo.getById(userId)
        return  userService.getTicketsGroupedByShowDate(user,cart.getAllTickets())
    }

    fun reserveTicket(userId: Long, ticketData: TicketCreateDTO) {
        val cart = cartRepo.getCardFor(userId)
        val show = showRepo.getById(ticketData.showId)
        val showDate = show.getShowDate(ticketData.date) ?: throw NotFoundException(
            showError.TICKET_CART_NOT_FOUND
        )
        val seatType = show.facility.getSeat(ticketData.seatTypeName).seatType

        showDate.reserveSeat(seatType, ticketData.quantity)
        cart.reserveTicket(Ticket(show, showDate, seatType, ticketData.seatPrice,ticketData.quantity))
    }

    fun removeReserveTickets(userId: Long) {
        val cart = cartRepo.getCardFor(userId)
        cart.removeTickets()
    }

    fun buyReservedTickets(userId: Long) {
        val cart = cartRepo.getCardFor(userId)
        cart.buyReservedTickets()
    }

    fun reservedTicketsPrice(userId: Long): Double {
        val cart = cartRepo.getCardFor(userId)

        return cart.getAllTickets().sumOf { ticket -> ticket.price() }
    }

    fun getTicketsSize(userId: Long): Int {
        val cart = cartRepo.getCardFor(userId)

        return cart.ticketsSize()
    }
}