package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowExtraDataDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.TicketDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toDTO
import ar.edu.unsam.phm.magicnightsback.service.TicketService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("\${api.ticket}")
class TicketController(
    @Autowired
    private val ticketService: TicketService
) {

//    @GetMapping("/user/{userId}")
//    fun findByUserId(@PathVariable userId: Long)
//        = ticketService.findByUserId(userId)

//    @GetMapping("/user/{userId}/cart")
//    fun findUserReservations(@PathVariable userId: Long) = ticketService.findUserReservations(userId)

//    @PostMapping("/reservation")
//    fun reserveTicket(@RequestBody tickets: List<TicketRequestDTO>): Boolean {
//        tickets.all { ticketService.reserve(it.toModel()) }
//    }
}

//
////    @DeleteMapping("/cancel/{ticketId}")
////    fun cancelReservation(@PathVariable ticketId: Long) {
////        ticketService.deleteByUUID(ticketId)
////    }
//
////    @PostMapping("/user/{userId}/buy")
////    fun buyTickets(@PathVariable userId: Long): Boolean {
////        return ticketService.confirmBuy(userId)
////    }
//}