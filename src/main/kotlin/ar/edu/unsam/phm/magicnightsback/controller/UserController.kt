package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.dto.*
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import ar.edu.unsam.phm.magicnightsback.service.TicketService
import ar.edu.unsam.phm.magicnightsback.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User related operations")
class UserController(
    @Autowired
    var userService: UserService,

    @Autowired
    var ticketService: TicketService,

    @Autowired
    var showService: ShowService
) {
    @GetMapping("/{id}")
    @Operation(summary = "User profile by id.")
    fun findById(@PathVariable id: Long) =
        userService.findByIdOrError(id).toDTO()

    @GetMapping("/{id}/bought_tickets")
    @Operation(summary = "Reuturns a list of bought tickets.")
    fun findBoughtTicketsByUserId(@PathVariable id: Long): List<TicketDTO> {
        return ticketService.findByUserId(id).map { it.toDTO(showService.getShowExtraData(it.showDate.show.id, id)) }
    }

    @GetMapping("/{id}/balance")
    @Operation(summary = "User balance by id.")
    fun getBalance(@PathVariable id: Long) = userService.getBalance(id)

    @Operation(summary = "User authentitcation.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Ok"),
            ApiResponse(responseCode = "400", description = FindError.BAD_CREDENTIALS, content = arrayOf(Content())),
        ]
    )
    @PostMapping("/login")
    fun authenticate(@RequestBody request: LoginUserDTO): LoginUserResponseDTO {
        val user = userService.authenticate(request.username, request.password)
        return user.loginResponseDTO()
    }

    @GetMapping("/{id}/friends")
    fun findUserFriends(@PathVariable id: Long): List<FriendDTO> {
        return userService.findUserFriends(id)
    }

    @DeleteMapping("/{userId}/friend/{friendId}")
    fun deleteUserFriend(@PathVariable userId: Long, @PathVariable friendId: Long): List<FriendDTO> {
        return userService.deleteUserFriend(userId, friendId)
    }

    @PatchMapping("/{id}/update")
    @Operation(summary = "Permite actualizar la data del usuario")
    fun updateUser(@PathVariable id: Long, @RequestBody user: UserUpdateDTO): UserDTO {
        val updatedUserDTO = userService.updateUser(id, user)
        return updatedUserDTO
    }

    @PatchMapping("/{id}/modify_balance")
    @Operation(summary = "Permite actualizar los creditos del usuario")
    fun updateUserBalance(@PathVariable id: Long, @RequestBody newBalance: Double): Double {
        return userService.updateUserBalance(id, newBalance)
    }
}

////
////    @GetMapping("{id}/balance_report")
////    fun userBalanceReport(@PathVariable id: Long): List<UserBalanceDTO> {
////        return userService.getBalances(id)
////    }
////
////    @GetMapping("{userId}/history-tickets/year/{year}")
////    @Operation(summary = "Permite obtener los tikcets que compró un usuario en determinado año")
////    fun getHistoryTickets(@PathVariable userId: Long, @PathVariable year: Int) = userService.historyTickets(userId,year)
////
////    @GetMapping("/users_with_more_tickets_than/{n}")
////    @Operation(summary = "Permite obtener la cantidad de usuarios con mas de N tickets")
////    fun findUsersWithMoreTicketsThan(@PathVariable n: Int): List<UserDTO> {
////        return userService.findUsersWithMoreTicketsThan(n)
////    }
//}