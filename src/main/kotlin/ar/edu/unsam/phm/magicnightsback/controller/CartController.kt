//package ar.edu.unsam.phm.magicnightsback.controller
//
//import ar.edu.unsam.phm.magicnightsback.dto.TicketCreateDTO
//import ar.edu.unsam.phm.magicnightsback.dto.TicketDTO
//import ar.edu.unsam.phm.magicnightsback.error.FacilityError
//import ar.edu.unsam.phm.magicnightsback.error.UserError
//import ar.edu.unsam.phm.magicnightsback.error.showDateError
//import ar.edu.unsam.phm.magicnightsback.error.showError
//import ar.edu.unsam.phm.magicnightsback.service.CartService
//import io.swagger.v3.oas.annotations.Operation
//import io.swagger.v3.oas.annotations.responses.ApiResponse
//import io.swagger.v3.oas.annotations.responses.ApiResponses
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.web.bind.annotation.*
//
//@RestController
//@CrossOrigin(origins = ["*"])
//@RequestMapping("cart/user")
//class CartController(
//    @Autowired val cartService: CartService
//) {
//
//    @GetMapping("/{userId}/reserved-tickets-price")
//    @Operation(summary = "Permite obtener el precio total de los tickets reservados para un usario")
//    fun getResrvedTicketsTotalPrice(@PathVariable userId: Long): Double{
//        return cartService.reservedTicketsPrice(userId)
//    }
//
//    @GetMapping("/{userId}/reserved-tickets")
//    @Operation(summary = "Permite obtener los tickets reservados agrupados por funcion para un usuario ")
//    fun getUserTicketsCart(@PathVariable userId: Long): List<TicketDTO> {
//        return cartService.getTicketsCart(userId)
//    }
//
//    @PutMapping("/{userId}/remove-reserved-tickets")
//    @Operation(summary = "Permite eliminar todos los tiquets reservados para un usuario")
//    fun removeReservedTickets(@PathVariable userId: Long){
//        cartService.removeReserveTickets(userId)
//    }
//
//    @PutMapping("/{userId}/purchase-reserved-tickets")
//    @Operation(summary = "Permite comprar todos los tickets reservados para un usuario")
//    @ApiResponses(
//        value = [
//            ApiResponse(responseCode = "200", description = "Ok"),
//            ApiResponse(responseCode = "400", description = UserError.MSG_NOT_ENOUGH_CREDIT),
//        ]
//    )
//    fun purchaseReservedTickets(@PathVariable userId: Long){
//        cartService.buyReservedTickets(userId)
//    }
//
//    @PutMapping("/{userId}/reserve-tickets")
//    @Operation(summary = "Permite reservar x cantidad de tiquets de un show para una funcion de ese show y para un tipo de asiento")
//    @ApiResponses(
//        value = [
//            ApiResponse(responseCode = "200", description = "Ok"),
//            ApiResponse(responseCode = "404", description = showError.TICKET_CART_NOT_FOUND),
//            ApiResponse(responseCode = "400", description = showDateError.EXCEEDED_CAPACITY + "<br>"+ FacilityError.INVALID_SEAT_TYPE),
//        ]
//    )
//    fun addReservedTicket(@PathVariable userId: Long, @RequestBody ticketData: TicketCreateDTO){
//        cartService.reserveTicket(userId, ticketData)
//    }
//
//    @GetMapping("/{userid}/tickets-reserved-size")
//    @Operation(summary = "Permite obtener la cantidad de tickets que hay en el carrito de un usuario")
//    fun getReservedTicketSize(@PathVariable userId: Long): Int{
//        return cartService.getTicketsSize(userId)
//    }
//}