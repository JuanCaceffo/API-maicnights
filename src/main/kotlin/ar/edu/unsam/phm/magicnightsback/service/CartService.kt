package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.validateOptionalIsNotNull
import ar.edu.unsam.phm.magicnightsback.dto.TicketCreateDTO
import ar.edu.unsam.phm.magicnightsback.dto.TicketDTO
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CartService(
    @Autowired val cartRepo: CartRepository,
    @Autowired val userRepo: UserRepository,
    @Autowired val showRepo: ShowRepository,
    @Autowired val userService: UserService
) {

    @Transactional(Transactional.TxType.NEVER)
    fun getCartByUserId(userId:Long) = validateOptionalIsNotNull(cartRepo.findById(userId),"El carrito para el usuario de id ${userId} no fue encontrado")


    @Transactional(Transactional.TxType.NEVER)
    fun getTicketsCart(userId: Long): List<TicketDTO> {
        val cart = getCartByUserId(userId)
        val user = userService.getUserById(userId)
        return  userService.getTicketsGroupedByShowDate(user,cart.getAllTickets())
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun reserveTicket(userId: Long, ticketData: TicketCreateDTO) {
        val cart = getCartByUserId(userId)
        val show = validateOptionalIsNotNull(showRepo.findById(ticketData.showId))
        val showDate = show.getShowDateById(ticketData.showDateId)
        val seat = show.facility.getPlaceBySeatName(ticketData.seatTypeName.name).seat

        cart.reserveTicket(Ticket(show, showDate, seat,ticketData.quantity))
        cartRepo.save(cart)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun removeReserveTickets(userId: Long) {
        val cart = getCartByUserId(userId)
        cart.removeTickets()
        cartRepo.save(cart)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun buyReservedTickets(userId: Long) {
        val cart = getCartByUserId(userId)
        cart.buyReservedTickets()
        cartRepo.save(cart)
    }


    @Transactional(Transactional.TxType.NEVER)
    fun reservedTicketsPrice(userId: Long): Double {
        val cart = getCartByUserId(userId)
        return cart.totalPrice()
    }

    @Transactional(Transactional.TxType.NEVER)
    fun getTicketsSize(userId: Long): Int {
        val cart = getCartByUserId(userId)

        return cart.ticketsSize()
    }
}