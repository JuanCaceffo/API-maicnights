package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.dto.UserDTO
import ar.edu.unsam.phm.magicnightsback.dto.FriendDTO
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.serializers.*
import ar.edu.unsam.phm.magicnightsback.service.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class UserController {

    @Autowired
    lateinit var userService: UserService

//    @GetMapping("/user/{id}/purchased-tickets")
//    fun getUserPurchased(@PathVariable id: Long): List<PurchasedTicketDTO> {
//        return userService.getUserPurchased(id)
//    }
//
//    @GetMapping("/user/{id}/pending-tickets")
//    fun getUserPending(@PathVariable id: Long): List<PendingTicketDTO> {
//        return userService.getUserPending(id)
//    }

    @GetMapping("/user_profile/{id}/friends")
    fun getUserFriends(@PathVariable id: Long): List<FriendDTO> {
        return userService.getUserFriends(id)
    }

    @DeleteMapping("/user_profile/{userId}/friends/{friendId}")
    fun deleteUserFriend(@PathVariable userId: Long, @PathVariable friendId: Long) {
        userService.deleteUserFriend(userId, friendId)
    }

    @GetMapping("/user_profile/{id}/comments")
    fun getUserComments(@PathVariable id: Long): List<CommentDTO> {
        return userService.getUserComments(id)
    }

    @PostMapping("/login")
    @Operation(summary = "Permite logear un usuario registrado en el sistema")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Ok"),
        ApiResponse(responseCode = "400", description = UserError.BAD_CREDENTIALS, content = arrayOf(Content()) ),
    ])
    fun loginUser(@RequestBody userToLogin: LoginUserDTO): Long {
        return userService.loginUser(userToLogin)
    }

    @GetMapping("/user_profile/{id}")
    fun getUser(@PathVariable id: Long): UserDTO{
        return userService.getUser(id)
    }

    @PutMapping("/user_profile/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: UserDTO) {
        return userService.updateUser(id, user)
    }

    /*@PatchMapping("/update-user")
    fun updateUser(@RequestBody userToUpdate: UserDTO) {
        userService.updateUser(userToUpdate)
    }*/

    @GetMapping("/user_profile/{id}/credit")
    fun getUserCredit(@PathVariable id: Long): Double {
        return userService.getUserCredit(id)
    }

    @PutMapping("/user_profile/{id}/add_credit")
    fun addUserCredit(@PathVariable id: Long, @RequestBody creditToAdd: Map<String, Double>): Double {
        val credit = creditToAdd["credit"]!!
        return userService.addCreditToUser(id, credit)
    }

}