package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.dto.UserDTO
import ar.edu.unsam.phm.magicnightsback.dto.toDTO
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
//    fun getUserPurchased(id: Long): List<PurchasedTicketDTO> {
//        TODO("Not yet implemented")
//    }
//
//    fun getUserPending(id: Long): List<PendingTicketDTO> {
//        TODO("Not yet implemented")
//    }

    /*fun getUserFriends(id: Long): List<FriendDTO> {
        TODO("Not yet implemented")
    }*/

    fun getUserComments(id: Long): List<CommentDTO> {
        TODO("Not yet implemented")
    }

    fun loginUser(loginUser: LoginUserDTO): Int {
        return this.userRepository.getLoginUser(loginUser) ?: throw AuthenticationException(UserError.BAD_CREDENTIALS)
    }

    fun getUser(id: Int): UserDTO {
        return this.userRepository.getById(id).toDTO()
    }

    fun getUserCredit(id: Int): Double {
        return this.userRepository.getById(id).credit
    }

    fun addCreditToUser(id: Int, creditToAdd: Double): Double {
        this.userRepository.addCredit(id, creditToAdd)

        return userRepository.getById(id).credit
    }

    /*fun updateUser(loginUser: UserDTO): UserDTO {
        TODO("Not yet implemented")
    }*/

}