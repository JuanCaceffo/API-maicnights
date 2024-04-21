package ar.edu.unsam.phm.magicnightsback.service

//import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.validateOptionalIsNotNull
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.*
import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.UserError
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var commentService: CommentService


//    @Autowired
//    lateinit var showRepository: ShowRepository

    @Transactional(Transactional.TxType.NEVER)
    fun findById(id: Long): User = validateOptionalIsNotNull(userRepository.findById(id))

    @Transactional(Transactional.TxType.NEVER)
    fun findByUsername(username: String): User = validateOptionalIsNotNull(userRepository.findByUsername(username))

    fun authenticate(username: String, password: String): User {
        val user = validateOptionalIsNotNull(userRepository.findByUsername(username), "No se encontró un usuario con username '${username}'.")

        if (user.password != password) {
            throw AuthenticationException(UserError.BAD_CREDENTIALS)
        }

        return user
    }
    @Transactional(Transactional.TxType.NEVER)
    fun getUserById(userId: Long) = validateOptionalIsNotNull(userRepository.findById(userId), "El usuario de id ${userId} no fue encontrado.")

    /*Mapeo todos los tickets en uno solo por showDate juntando el precio total*/
    fun getTicketsGroupedByShowDate(user: User, ticketList: List<Ticket>): List<TicketDTO> {

        val distinctTickets = ticketList.distinctBy { it.showDate }
        return distinctTickets.map { uniqueTicket ->
            val ticketsSameShowDate = ticketList.filter { ticket -> ticket.showDate == uniqueTicket.showDate }
            val totalPrice = ticketsSameShowDate.sumOf { ticket -> ticket.price() }
            val quantity = ticketsSameShowDate.sumOf { ticket -> ticket.quantity }
            val commentsStats = commentService.getCommentStadisticsOfShow(uniqueTicket.show.id)
            uniqueTicket.toTicketDTO(commentsStats,user, totalPrice, quantity)
        }
    }
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

    fun updateUserCredit(userId: Long, creditToAdd: Double): Double {
        val user = validateOptionalIsNotNull(userRepository.findById(userId) , "El usuario con id '${userId}' no fue encontrado.")

        user.credit += creditToAdd
        userRepository.save(user)

        return user.credit
    }

    fun updateUser(userId: Long, userDTO: UserDTO): User {
        val user = validateOptionalIsNotNull(userRepository.findById(userId), "El usuario con id '${userId}' no fue encontrado.")

        user.name = userDTO.name
        user.surname = userDTO.surname

        userRepository.save(user)

        return user
    }


//        val user = userRepository.getById(id)
//        try {
//            val comment = user.comments[commentId.toInt()]
//            user.removeComment(comment)
//        } catch (e: Exception) {
//            throw BusinessException(UserError.NONEXISTENT_USER_COMMENT)
//        }

//
//    fun createComment(id: Long, commentCreat: CommentCreateDTO) {
//        val user = userRepository.getById(id)
//        val ticket = user.tickets.distinctBy { it.showDate }[commentCreat.groupTicketId.toInt()]
//        val comment = Comment(ticket.show, commentCreat.text, commentCreat.rating)
//
//        user.addComment(comment, ticket.show)
//    }
//
//

    fun validateAdmin(userId: Long) {
        val user = validateOptionalIsNotNull(userRepository.findById(userId), "El usuario con id '${userId}' no fue encontrado.")
        if (!user.isAdmin) throw AuthenticationException(UserError.USER_IS_NOT_ADMIN)
    }

}