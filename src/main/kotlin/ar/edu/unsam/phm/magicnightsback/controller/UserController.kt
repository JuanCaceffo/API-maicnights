package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class UserController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/user/{id}/purchased-tickets")
    fun getUserPurchased(@PathVariable id: Long): List<PurchasedTicketDTO> {
        return userService.getUserPurchased(id)
    }

    @GetMapping("/user/{id}/pending-tickets")
    fun getUserPending(@PathVariable id: Long): List<PendingTicketDTO> {
        return userService.getUserPending(id)
    }

    @GetMapping("/user/{id}/friends")
    fun getUserFriends(@PathVariable id: Long): List<FriendDTO> {
        return userService.getUserFriends(id)
    }

    @GetMapping("/user/{id}/comments")
    fun getUserComments(@PathVariable id: Long): List<CommentDTO> {
        return userService.getUserComments(id)
    }

    @PostMapping("/login")
    fun loginUser(@RequestBody userToLogin: LoginDTO): Long {
        return userService.loginUser(userToLogin)
    }

    @PatchMapping("/update-user")
    fun updateUser(@RequestBody userToUpdate: UserDTO) {
        userService.updateUser(userToUpdate)
    }

    @PatchMapping("/update-user")
    fun getUserCredit(@RequestBody user: UserDTO): Double {
        return userService.getUserCredit(user)
    }
}