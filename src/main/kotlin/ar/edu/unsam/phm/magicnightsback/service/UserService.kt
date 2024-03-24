package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.dto.*
import org.springframework.stereotype.Service

@Service
class UserService {
//    fun getUserPurchased(id: Long): List<PurchasedTicketDTO> {
//        TODO("Not yet implemented")
//    }
//
//    fun getUserPending(id: Long): List<PendingTicketDTO> {
//        TODO("Not yet implemented")
//    }

    fun getUserFriends(id: Long): List<FriendDTO> {
        TODO("Not yet implemented")
    }

    fun getUserComments(id: Long): List<CommentDTO> {
        TODO("Not yet implemented")
    }

    fun loginUser(loginUser: LoginDTO): Long {
        TODO("Not yet implemented")
    }

    fun updateUser(loginUser: UserDTO): UserDTO {
        TODO("Not yet implemented")
    }

    fun getUserCredit(user: UserDTO): Double {
        TODO("Not yet implemented")
    }
}