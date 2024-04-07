package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
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

    @Autowired
    lateinit var showRepository: ShowRepository

    /*Mapeo todos los tickets en uno solo por showDate juntando el precio total*/
    fun getTicketsGroupedByShowDate(user: User, ticketList: List<Ticket>): List<TicketDTO> {
        val distinctTickets = ticketList.distinctBy { it.showDate }
        return distinctTickets.map { uniqueTicket ->
            val ticketsSameShowDate = ticketList.filter { ticket -> ticket.showDate == uniqueTicket.showDate }
            val totalPrice = ticketsSameShowDate.sumOf { ticket -> ticket.price() }
            val quantity = ticketsSameShowDate.sumOf { ticket -> ticket.quantity }
            uniqueTicket.toTicketDTO(user, totalPrice, quantity)
        }
    }

    fun getTicketsCart(userId: Long): List<TicketDTO> {
        val user = userRepository.getById(userId)

        return getTicketsGroupedByShowDate(user,user.reservedTickets)
    }

    fun getUserPurchasedTickets(userId: Long): List<PurchasedTicketDTO>{
        val user = userRepository.getById(userId)
        return getTicketsGroupedByShowDate(user,user.tickets).map { it.toPurchasedTicketDTO() }
    }

    fun getUserFriends(id: Long): List<FriendDTO> {
        val friends = this.userRepository.getFriends(id)
        return friends.map { userFriend -> userFriend.toFriendDTO() }
    }

    fun getUserComments(id: Long): List<CommentDTO> {
        val user = userRepository.getById(id)
        
        return user.comments.map { comment -> comment.toUserCommentDTO()  }
    }

    fun loginUser(loginUser: LoginUserDTO): Long {
        return this.userRepository.getLoginUser(loginUser)
            ?: throw AuthenticationException(UserError.BAD_CREDENTIALS)
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

    fun updateUser(id: Long, loginUser: UserDTO) {
        val userToUpdate = this.userRepository.getById(id)

        userToUpdate.name = loginUser.name
        userToUpdate.surname = loginUser.surname

        this.userRepository.update(userToUpdate)
    }

    //TODO: posible extrapolacion de orquetado de logica de reserva en una entidad cart
    fun reserveTicket(userId: Long, ticketData: TicketCreateDTO) {
        val user = userRepository.getById(userId)
        val show = showRepository.getById(ticketData.showId)
        val showDate = show.dates.elementAtOrNull(ticketData.showDateId.toInt()) ?: throw NotFoundException(
            showError.TICKET_CART_NOT_FOUND
        )
        val seatType = show.facility.getSeat(ticketData.seatTypeName).seatType

        showDate.reserveSeat(seatType, ticketData.quantity)
        user.reservedTickets.add(Ticket(show, showDate, seatType, ticketData.seatPrice,ticketData.quantity))
    }

    //TODO: posible extrapolacion de orquetado de logica de elimindo en una entidad cart
    fun removeReserveTickets(userId: Long) {
        val user = userRepository.getById(userId)

        user.reservedTickets.forEach {
            ticket -> ticket.showDate.releaseSeat(ticket.seatType,ticket.quantity)
        }
        user.reservedTickets.clear()
    }

    fun purchaseReservedTickets(userId: Long) {
        val user = userRepository.getById(userId)
        user.buyReservedTickets()
    }

    fun reservedTicketsPrice(userId: Long): Double {
        val user = userRepository.getById(userId)

        return user.reservedTickets.sumOf { ticket -> ticket.seatPrice }
    }

    fun deleteComment(commentId: Long, id: Long) {
        val user = userRepository.getById(id)
        try{
            val comment = user.comments[commentId.toInt()]
            user.removeComment(comment)
        }catch (e: Exception){
            throw BusinessException(UserError.NONEXISTENT_USER_COMMENT)
        }
    }

    fun createComment(id: Long, commentCreat: CommentCreateDTO) {
        val user = userRepository.getById(id)
        val ticket = user.tickets.distinctBy { it.showDate }[commentCreat.groupTicketId.toInt()]
        val comment = Comment(ticket.show,commentCreat.text,commentCreat.rating)

        user.addComment(comment,ticket.show)
    }
}