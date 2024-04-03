package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.*
import ar.edu.unsam.phm.magicnightsback.serializers.*
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository

@Service
class UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var showRepository: ShowRepository
    fun getTicketsCart(userId: Long): List<TicketCartDTO> {
        val user = userRepository.getById(userId)

        /*Mapeo todos los tickets en uno solo por show juntando el precio total, las fechas y
        la cantidad de tickets para ese show*/
        val distinctTickets = user.pendingTickets.distinctBy { it.show }
        return distinctTickets.map { uniqueTicket ->
            val ticketsSameShow = user.pendingTickets.filter { ticket -> ticket.show == uniqueTicket.show }
            val totalPrice = ticketsSameShow.sumOf { ticket -> ticket.price }
            val allDates = ticketsSameShow.map { ticket -> ticket.showDate.date }.distinct()
            uniqueTicket.toCartDTO(userId, allDates, totalPrice, ticketsSameShow.size)
        }
    }

    fun getUserPurchasedTickets(userId: Long): List<PurchsedTicketDTO>{
        val user = userRepository.getById(userId)
        return user.tickets.map { ticket -> (ticket.toPurchasedTicketDTO(userId)) }
    }

    fun getUserFriends(id: Long): List<FriendDTO> {
        val friends = this.userRepository.getFriends(id)
        return friends.map { userFriend -> userFriend.toFriendDTO() }
    }

    fun getUserComments(id: Long): List<CommentDTO> {
        TODO("Not yet implemented")
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

    fun reserveTicket(userId: Long, ticketData: TicketCreateDTO) {
        val user = userRepository.getById(userId)
        val show = showRepository.getById(ticketData.showId)
        val showDate = show.dates.elementAtOrNull(ticketData.showDateId.toInt()) ?: throw NotFoundException(
            showError.TICKET_CART_NOT_FOUND
        )
        val seatType = show.facility.getSeat(ticketData.seatTypeName).seatType

        showDate.reserveSeat(ticketData.seatTypeName, ticketData.quantity)
        repeat(ticketData.quantity) {
            user.pendingTickets.add(Ticket(show, showDate, seatType, ticketData.price))
        }
    }
}