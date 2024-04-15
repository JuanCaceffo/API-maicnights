package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.validateOptionalIsNotNull
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.*
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.UserError
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository
//
//    @Autowired
//    lateinit var showRepository: ShowRepository

        fun findById(id: Long): User = validateOptionalIsNotNull(userRepository.findById(id))
    }


//    /*Mapeo todos los tickets en uno solo por showDate juntando el precio total*/
//    fun getTicketsGroupedByShowDate(user: User, ticketList: List<Ticket>): List<TicketDTO> {
//        val distinctTickets = ticketList.distinctBy { it.showDate }
//        return distinctTickets.map { uniqueTicket ->
//            val ticketsSameShowDate = ticketList.filter { ticket -> ticket.showDate == uniqueTicket.showDate }
//            val totalPrice = ticketsSameShowDate.sumOf { ticket -> ticket.price() }
//            val quantity = ticketsSameShowDate.sumOf { ticket -> ticket.quantity }
//            uniqueTicket.toTicketDTO(user, totalPrice, quantity)
//        }
//    }
//
//    fun getUserPurchasedTickets(userId: Long): List<PurchasedTicketDTO> {
//        val user = userRepository.getById(userId)
//        return getTicketsGroupedByShowDate(user, user.tickets).map { it.toPurchasedTicketDTO() }
//    }
//
//    fun getUserFriends(id: Long): List<FriendDTO> {
//        val friends = this.userRepository.getFriends(id)
//        return friends.map { userFriend -> userFriend.toFriendDTO() }
//    }
//
//    fun getUserComments(id: Long): List<CommentDTO> {
//        val user = userRepository.getById(id)
//
//        return user.comments.map { comment -> comment.toUserCommentDTO() }
//    }
//
//    fun loginUser(loginUser: LoginUserDTO): Long {
//        return this.userRepository.getLoginUser(loginUser)
//            ?: throw AuthenticationException(UserError.BAD_CREDENTIALS)
//    }
//

//    fun deleteUserFriend(userId: Long, friendId: Long) {
//        this.userRepository.getById(userId).removeFriendById(friendId)
//    }
//
//    fun validateUser(userId: Long): Boolean {
//        return this.userRepository.getById(userId).isAdmin
//    }
//
//    fun getUserCredit(id: Long): Double {
//        return this.userRepository.getById(id).credit
//    }
//
//    fun addCreditToUser(id: Long, creditToAdd: Double): Double {
//        this.userRepository.addCredit(id, creditToAdd)
//
//        return userRepository.getById(id).credit
//    }
//
//    fun updateUser(id: Long, loginUser: UserDTO) {
//        val userToUpdate = this.userRepository.getById(id)
//
//        userToUpdate.name = loginUser.name
//        userToUpdate.surname = loginUser.surname
//
//        this.userRepository.update(userToUpdate)
//    }
//
//    fun deleteComment(commentId: Long, id: Long) {
//        val user = userRepository.getById(id)
//        try {
//            val comment = user.comments[commentId.toInt()]
//            user.removeComment(comment)
//        } catch (e: Exception) {
//            throw BusinessException(UserError.NONEXISTENT_USER_COMMENT)
//        }
//    }
//
//    fun createComment(id: Long, commentCreat: CommentCreateDTO) {
//        val user = userRepository.getById(id)
//        val ticket = user.tickets.distinctBy { it.showDate }[commentCreat.groupTicketId.toInt()]
//        val comment = Comment(ticket.show, commentCreat.text, commentCreat.rating)
//
//        user.addComment(comment, ticket.show)
//    }
//
//    fun isAdmin(id: Long) = userRepository.getById(id).throwIfNotAdmin(UserError.USER_IS_NOT_ADMIN)
}