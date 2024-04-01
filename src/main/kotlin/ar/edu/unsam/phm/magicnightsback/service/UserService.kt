package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.dto.FriendDTO
import ar.edu.unsam.phm.magicnightsback.dto.toFriendDTO
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

    fun getById(userid: Long) = userRepository.getById(userid)

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

    fun deleteUserFriend(userId: Long, friendId: Long) {
        this.userRepository.getById(userId).removeFriendById(friendId)
    }

    /*fun updateUser(loginUser: UserDTO): UserDTO {
        TODO("Not yet implemented")
    }

    fun getUserCredit(user: UserDTO): Double {
        TODO("Not yet implemented")
    }*/
}