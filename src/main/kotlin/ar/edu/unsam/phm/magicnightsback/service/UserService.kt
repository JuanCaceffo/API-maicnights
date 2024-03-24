package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.dto.*
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import javax.naming.AuthenticationException

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    fun getUserPurchased(id: Long): List<PurchasedTicketDTO> {
        TODO("Not yet implemented")
    }

    fun getUserPending(id: Long): List<PendingTicketDTO> {
        TODO("Not yet implemented")
    }

    fun getUserFriends(id: Long): List<FriendDTO> {
        TODO("Not yet implemented")
    }

    fun getUserComments(id: Long): List<CommentDTO> {
        TODO("Not yet implemented")
    }

    fun loginUser(loginUser: LoginDTO): Int {
        return this.userRepository.getLoginUser(loginUser) ?: throw AuthenticationException("El usuario y la contraseña no son validos")
    }

    fun updateUser(loginUser: UserDTO): UserDTO {
        TODO("Not yet implemented")
    }

    fun getUserCredit(user: UserDTO): Double {
        TODO("Not yet implemented")
    }
}