package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.service.TicketService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("\${api.ticketURL}")
class TicketController(
    @Autowired
    private val ticketService: TicketService
) {

    @GetMapping("/user/{userId}")
    fun findUserTicketsByID(@PathVariable userId: Long) = ticketService.findUserTicketsBy(userId)

    @GetMapping("/user/{userId}/cart")
    fun findUserReservations(@PathVariable userId: Long) = ticketService.findUserReservations(userId)

    @PostMapping("/reservation")
    fun reserveTicket(@RequestBody ticket: TicketRequestDTO): TicketResponseDTO {
        val ticket = ticket.toModel()
        val reservation = ticketService.reserve(ticket)
        return reservation.toResponseDTO()
    }

    @DeleteMapping("/cancel/{ticketId}")
    fun cancelReservation(@PathVariable ticketId: Long) {
        ticketService.deleteByUUID(ticketId)
    }

    @PostMapping("/user/{userId}/buy")
    fun buyTickets(@PathVariable userId: Long): Boolean {
        return ticketService.confirmBuy(userId)
    }
}