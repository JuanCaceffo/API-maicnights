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
import java.time.LocalDateTime

@Service
class CartService(
    val showDateService: ShowDateService,
    val seatService: SeatService,
    val userService: UserService,
    val ticketService: TicketService
) {
    private val cart: HashMap<Long, MutableList<Ticket>> = HashMap()

    
    fun getCart(userId: Long): List<Ticket> {
        return cart[userId] ?: emptyList()
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun addAll(userId: Long, ticketsRequested: List<TicketRequestDTO>) {
        val user = userService.findByIdOrError(userId)
        val userCart = cart.getOrPut(userId) { mutableListOf() }

        ticketsRequested.forEach { tkt ->
            val showDate = showDateService.findByIdOrError(tkt.showDateId)
            val seat = seatService.findByIdOrError(tkt.seatId)

            validateReservation(showDate, seat, tkt.quantity)

            repeat(tkt.quantity) {
                userCart.add(Ticket(user, showDate, seat).apply {
                    price = showDate.show.currentTicketPrice(seat)
                })
            }
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun buyAll(userId: Long) {
        userService.findByIdOrError(userId).modifyBalance(totalPrice(userId))
        cart[userId]?.forEach {
            it.buyDate = LocalDateTime.now()
        }
        cart[userId]?.forEach {
            seatService.updateUsedCapacityById(it.seat.id, 1)
            ticketService.save(it)
        }
        clearAll(userId)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun clearAll(userId: Long) {
        cart[userId]?.clear()
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun totalPrice(userId: Long): Double {
        return cart[userId]?.sumOf { it.price } ?: 0.0
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