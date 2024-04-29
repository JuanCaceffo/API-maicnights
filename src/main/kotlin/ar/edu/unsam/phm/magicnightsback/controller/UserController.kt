package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.dto.UserDTO
//import ar.edu.unsam.phm.magicnightsback.dto.FriendDTO
//import ar.edu.unsam.phm.magicnightsback.dto.TicketCreateDTO
//import ar.edu.unsam.phm.magicnightsback.error.FacilityError
//import ar.edu.unsam.phm.magicnightsback.dto.PurchasedTicketDTO
//import ar.edu.unsam.phm.magicnightsback.error.showDateError
//import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.service.*
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/api/user")
@Tag(name = "User", description = "User related operations")
class UserController {
    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var commentService: CommentService

    @GetMapping("/validate")
    @Operation(summary = "Valida el tipo de usuario")
    fun isAdmin(@RequestParam(required = true) userId: Long):Boolean{
        return userService.validateUser(userId)
    }
//
//    @GetMapping("/{userId}/purchased_tickets")
//    @Operation(summary = "Permite obtener todos los tickets por funcion comprados por el usuario")
//    fun getUserPurchasedTickets(@PathVariable userId: Long): List<PurchasedTicketDTO> {
//        return userService.getUserPurchasedTickets(userId)
//    }
    @GetMapping("/{id}/friends")
    fun getUserFriends(@PathVariable id: Long): List<FriendDTO> {
        return userService.getUserFriends(id)
    }

    @DeleteMapping("/{userId}/remove-friend/{friendId}")
    fun deleteUserFriend(@PathVariable userId: Long, @PathVariable friendId: Long): List<FriendDTO> {
        return userService.deleteUserFriend(userId, friendId)
    }

    @GetMapping("/{userId}/comments")
    fun getUserComments(@PathVariable userId: Long): List<CommentDTO> {
        return commentService.getUserComments(userId)
    }


    @PutMapping("/{userId}/create-comment")
    @Operation(summary = "Permite crear un comentario hacia un show")
    fun createComment(@RequestBody comment: CommentDTO,  @PathVariable userId: Long){
        commentService.addComment(comment)
    }


    @Operation(summary = "Permite logear un usuario registrado en el sistema")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Ok"),
            ApiResponse(responseCode = "400", description = UserError.BAD_CREDENTIALS, content = arrayOf(Content())),
        ]
    )
    @PostMapping("/login")
    fun authenticate(@RequestBody request: LoginUserDTO): Long {
        val user = userService.authenticate(request.username, request.password)
        return user.id
    }


    @GetMapping("/{id}/data")
    @Operation(summary = "Permite obtener la data del perfil del usuario")
    fun getUser(@PathVariable id: Long): UserDTO {
        val user = userService.findById(id)
        return user.toDTO()
    }

    @PutMapping("/{id}/update")
    @Operation(summary = "Permite actualizar la data del usuario")
    fun updateUser(@PathVariable id: Long, @RequestBody user: UserDTO): UserDTO {
        val updatedUserDTO = userService.updateUser(id, user)

        return updatedUserDTO
    }

    @GetMapping("/{id}/credit")
    @Operation(summary = "Permite obtener los creditos del usuario")
    fun getUserCredit(@PathVariable id: Long): Double {
        return userService.getUserCredit(id)
    }


    @PutMapping("/{id}/add_credit")
    @Operation(summary = "Permite actualizar los creditos del usuario")
    fun updateUserCredit(@PathVariable id: Long, @RequestBody creditToAdd: Double): Double {
        return userService.updateUserCredit(id, creditToAdd)
    }
}