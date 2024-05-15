package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.dto.TicketRequestDTO
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.CreationError
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CartService(
    val showDateService: ShowDateService,
    val seatService: SeatService,
    val userService: UserService,
    val ticketService: TicketService
) {
    private val cart: HashMap<Long, MutableList<Ticket>> = HashMap()

    @Transactional(Transactional.TxType.NEVER)
    fun getCart(userId: Long): List<Ticket> {
        return cart[userId] ?: emptyList()
    }

    @Transactional(Transactional.TxType.NEVER)
    fun addAll(userId: Long, ticketsRequested: List<TicketRequestDTO>) {
        val user = userService.findByIdOrError(userId)
        val userCart = cart.getOrPut(userId) { mutableListOf() }

        ticketsRequested.forEach { tkt ->
            val showDate = showDateService.findByIdOrError(tkt.showDateId)
            val seat = seatService.findByIdOrError(tkt.seatId)

            validateReservation(showDate, seat, tkt.quantity)

            repeat(tkt.quantity) {
                userCart.add(Ticket(user, showDate, seat))
            }
        }
    }

    private fun seatReservations(seatType: SeatTypes) =
        cart.values.flatMap { tickets -> tickets.filter { it.seat.type == seatType } }.count()

    private fun validateReservation(showDate: ShowDate, seat: Seat, quantityToReservate: Int) {
        if (showDate.beenStaged())
            throw BusinessException(CreationError.ALREADY_PASSED)

        if (seat.available().minus(seatReservations(seat.type) + quantityToReservate) < 0) {
            throw BusinessException(CreationError.NO_CAPACITY)
        }
    }
}

//

////
////    fun removeFromCart(userId: Long, ticketId: Long) {
////        cart[userId]?.removeIf { it.id == ticketId }
////    }
////

//
////    fun buyCart(userId: Long): Boolean {
////        val user = userService.findOrErrorById(userId)
////
////        cart[userId]?.forEach {
////            it.buyDate = LocalDateTime.now()
////            it.price = it.showDate.currentPrice(it.seat)
////        }
////
////        cart[userId]?.all { ticketService.save() }
////    }
//
//
////    @Transactional(Transactional.TxType.NEVER)
////    fun getCartByUserId(userId:Long) = validateOptionalIsNotNull(cartRepo.findById(userId),"El carrito para el usuario de id ${userId} no fue encontrado")
////
////
////    @Transactional(Transactional.TxType.NEVER)
////    fun getTicketsCart(userId: Long): List<TicketDTO> {
////        val user = userService.findById(userId)
////        val tickets = cartRepo.getReservedTickets(userId)
////        return  ticketService.getTicketsGroupedByShowDate(user,tickets)
////    }
////
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun reserveTicket(userId: Long, ticketData: TicketCreateDTO) {
////        val cart = getCartByUserId(userId)
////        val show = validateOptionalIsNotNull(showRepo.findById(ticketData.showId))
////        val showDate = show.getShowDateById(ticketData.showDateId)
////        val seat = show.facility.getPlaceBySeatName(ticketData.seatTypeName.name).seatType
////
////        cart.reserveTicket(Ticket(show, showDate, seat,ticketData.quantity))
////        cartRepo.save(cart)
////    }
////
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun removeReserveTickets(userId: Long) {
////        val cart = getCartByUserId(userId)
////        cart.removeTickets()
////        cartRepo.save(cart)
////    }
////
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun buyReservedTickets(userId: Long) {
////        val cart = getCartByUserId(userId)
////        cart.buyReservedTickets()
////        cartRepo.save(cart)
////    }
////
////    @Transactional(Transactional.TxType.NEVER)
////    fun reservedTicketsPrice(userId: Long): Double {
////        val cart = getCartByUserId(userId)
////        return cart.totalPrice()
////    }
////
////    @Transactional(Transactional.TxType.NEVER)
////    fun getTicketsSize(userId: Long): Int {
////        val cart = getCartByUserId(userId)
////        return cart.ticketsSize()
////    }
//}