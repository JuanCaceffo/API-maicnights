package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.serializers.*
import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.UserError
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun getTicketsCart(userId: Long): List<TicketCartDTO>{
        val user = userRepository.getById(userId)
        return user.cart.map { ticket -> (ticket.toCartDTO(userId)) }
    }
    fun getUserFriends(id: Long): List<FriendDTO> {
        val friends = this.userRepository.getFriends(id)
        return friends.map { userFriend -> userFriend.toFriendDTO() }
    }

    fun getUserComments(id: Long): List<CommentDTO> {
        TODO("Not yet implemented")
    }

    fun loginUser(loginUser: LoginUserDTO): Long {
        return this.userRepository.getLoginUser(loginUser) ?: throw AuthenticationException(UserError.BAD_CREDENTIALS)
    }

    fun getUser(id: Long): UserDTO {
        return this.userRepository.getById(id).toDTO()
    }

    fun deleteUserFriend(userId: Long, friendId: Long) {
        this.userRepository.getById(userId).removeFriendById(friendId)
    }

    fun getUserCredit(id: Long): Double {
        return this.userRepository.getById(id).credit
    }

    fun addCreditToUser(id: Long, creditToAdd: Double): Double {
        this.userRepository.addCredit(id, creditToAdd)

        return userRepository.getById(id).credit
    }

    fun updateUser(id:Long, loginUser: UserDTO) {
        val userToUpdate = this.userRepository.getById(id)

        userToUpdate.name = loginUser.name
        userToUpdate.surname = loginUser.surname

        this.userRepository.update(userToUpdate)
    }
}