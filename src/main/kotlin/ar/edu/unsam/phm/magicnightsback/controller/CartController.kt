package ar.edu.unsam.phm.magicnightsback.controller

//import ar.edu.unsam.phm.magicnightsback.dto.TicketRequestDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.TicketDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.TicketRequestDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toDTO
import ar.edu.unsam.phm.magicnightsback.service.CartService
import ar.edu.unsam.phm.magicnightsback.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("\${api.cart}/user")
@Tag(name = "Cart", description = "Cart related operations")
class CartController(
    @Autowired
    val cartService: CartService,

    @Autowired
    val userService: UserService
) {
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene el carrito según usuario.")
    fun getUserCart(@PathVariable id: Long): List<TicketDTO> {
        return cartService.getCart(id).map { it.toDTO() }
    }

    @PostMapping("/{id}/add")
    @Operation(summary = "Agrega los tickets al carrito de compras")
    fun addToCart(@PathVariable id: Long, @RequestBody tickets: List<TicketRequestDTO>) {
        userService.validateUserExists(id)
        cartService.addAll(id, tickets)
    }

//    @GetMapping("/{userId}/reserved-tickets-price")
//    @Operation(summary = "Permite obtener el precio total de los tickets reservados para un usario")
//    fun getResrvedTicketsTotalPrice(@PathVariable userId: Long): Double {
//        return cartService.reservedTicketsPrice(userId)
//    }
}


////    @DeleteMapping("/{userId}/remove-reserved-tickets")
////    @Operation(summary = "Permite eliminar todos los tiquets reservados para un usuario")
////    fun removeReservedTickets(@PathVariable userId: Long){
////        cartService.removeReserveTickets(userId)
////    }
////
////    @PatchMapping("/{userId}/buy-reserved-tickets")
////    @Operation(summary = "Permite comprar todos los tickets reservados para un usuario")
////    @ApiResponses(
////        value = [
////            ApiResponse(responseCode = "200", description = "Ok"),
////            ApiResponse(responseCode = "400", description = UserError.MSG_NOT_ENOUGH_CREDIT),
////        ]
////    )
////    fun buyReservedTickets(@PathVariable userId: Long){
////        cartService.buyReservedTickets(userId)
////    }
////
////    @PatchMapping("/{userId}/reserve-tickets")
////    @Operation(summary = "Permite reservar x cantidad de tiquets de un show para una funcion de ese show y para un tipo de asiento")
////    @ApiResponses(
////        value = [
////            ApiResponse(responseCode = "200", description = "Ok"),
////            ApiResponse(responseCode = "404", description = ShowError.TICKET_CART_NOT_FOUND),
////            ApiResponse(responseCode = "400", description = ShowDateError.EXCEEDED_CAPACITY + "<br>"+ FacilityError.INVALID_SEAT_TYPE),
////        ]
////    )
////    fun addReservedTicket(@PathVariable userId: Long, @RequestBody ticketData: TicketCreateDTO){
////        cartService.reserveTicket(userId, ticketData)
////    }
////
////    @GetMapping("/{userId}/tickets-reserved-size")
////    @Operation(summary = "Permite obtener la cantidad de tickets que hay en el carrito de un usuario")
////    fun getReservedTicketSize(@PathVariable userId: Long): Int{
////        return cartService.getTicketsSize(userId)
////    }
////}