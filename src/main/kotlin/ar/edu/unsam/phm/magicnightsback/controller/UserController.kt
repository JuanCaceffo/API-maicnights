package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.dto.UserDTO
import ar.edu.unsam.phm.magicnightsback.dto.FriendDTO
import ar.edu.unsam.phm.magicnightsback.dto.TicketCartDTO
import ar.edu.unsam.phm.magicnightsback.dto.TicketCreateDTO
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import ar.edu.unsam.phm.magicnightsback.dto.PurchsedTicketDTO
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.error.showDateError
import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.service.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("user")
class UserController {

    @
    lateinit var userService: UserService

    @GetMapping("/{userId}/reserved-tickets-total-rice")
    fun getResrvedTicketsTotalPrice(@PathVariable userId: Long): Double{
        return userService.reservedTicketsPrice(userId)
    }

    @GetMapping("/{userId}/reserved-tickets")
    @Operation(summary = "Permite obtener los tickets por show que el usuario tiene reservados en el carrito")
    fun getUserTicketsCart(@PathVariable userId: Long): List<TicketCartDTO> {
        return userService.getTicketsCart(userId)
    }

    @PutMapping("/{userId}/reserve-tickets")
    @Operation(summary = "Permite reservar x cantidad de tiquets de un show para una funcion de ese show y para un tipo de asiento")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Ok"),
            ApiResponse(responseCode = "404", description = showError.TICKET_CART_NOT_FOUND),
            ApiResponse(responseCode = "400", description = showDateError.EXCEEDED_CAPACITY + "<br>"+ FacilityError.INVALID_SEAT_TYPE),
        ]
    )
    fun addReservedTicket(@PathVariable userId: Long, @RequestBody ticketData: TicketCreateDTO){
        userService.reserveTicket(userId, ticketData)
    }

    @PutMapping("/{userId}/remove-reserved-tickets")
    @Operation(summary = "Permite eliminar todos los tiquets reservados")
    fun removeReservedTickets(@PathVariable userId: Long){
        userService.removeReserveTickets(userId)
    }

    @PutMapping("/{userId}/purchase-reserved-tickets")
    @Operation(summary = "Permite comprar todos los tickets reservados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Ok"),
            ApiResponse(responseCode = "400", description = UserError.MSG_NOT_ENOUGH_CREDIT),
        ]
    )
    fun purchaseReservedTickets(@PathVariable userId: Long){
        userService.purchaseReservedTickets(userId)
    }
    @GetMapping("/{userId}/purchased_tickets")
    fun getUserPurchasedTickets(@PathVariable userId: Long): List<PurchsedTicketDTO> {
        return userService.getUserPurchasedTickets(userId)
    }
    @GetMapping("/{id}/friends")
    fun getUserFriends(@PathVariable id: Long): List<FriendDTO> {
        return userService.getUserFriends(id)
    }

    @DeleteMapping("/{userId}/remove-friend/{friendId}")
    fun deleteUserFriend(@PathVariable userId: Long, @PathVariable friendId: Long) {
        userService.deleteUserFriend(userId, friendId)
    }

    @GetMapping("/{id}/comments")
    fun getUserComments(@PathVariable id: Long): List<CommentDTO> {
        return userService.getUserComments(id)
    }

    @PostMapping("/login")
    @Operation(summary = "Permite logear un usuario registrado en el sistema")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Ok"),
            ApiResponse(responseCode = "400", description = UserError.BAD_CREDENTIALS, content = arrayOf(Content())),
        ]
    )
    fun loginUser(@RequestBody userToLogin: LoginUserDTO): Long {
        return userService.loginUser(userToLogin)
    }

    @GetMapping("/{id}/data")
    @Operation(summary = "Permite obtener la data del perfil del usuario")
    fun getUser(@PathVariable id: Long): UserDTO {
        return userService.getUser(id)
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Permite actualizar la data del usuario")
    fun updateUser(@PathVariable id: Long, @RequestBody user: UserDTO) {
        return userService.updateUser(id, user)
    }

    @GetMapping("/{id}/credit")
    @Operation(summary = "Permite obtener los creditos del usuario")
    fun getUserCredit(@PathVariable id: Long): Double {
        return userService.getUserCredit(id)
    }

    @PutMapping("/{id}/add_credit")
    @Operation(summary = "Permite actualizar los creditos del usuario")
    fun addUserCredit(@PathVariable id: Long, @RequestBody creditToAdd: Double): Double {
        return userService.addCreditToUser(id, creditToAdd)
    }

}