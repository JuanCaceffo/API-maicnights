package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.serializers.*
import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.NotFoundException
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.error.showError
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

    fun updateUser(loginUser: UserDTO): UserDTO {
        TODO("Not yet implemented")
    }

    fun reserveTicket(userId: Long, ticketData: TicketCreateDTO) {
        val user = userRepository.getById(userId)
        val show = showRepository.getById(ticketData.showId)
        val showDate = show.dates.elementAtOrNull(ticketData.showDateId.toInt()) ?: throw NotFoundException(showError.TICKET_CART_NOT_FOUND)

        //TODO: validar tipo de asiento que nos da el usuario, y preguntar a los chicos como lo tienen pensado

        showDate.reserveSeat(ticketData.seatType,ticketData.amount)
        repeat(ticketData.amount) {
            user.cart.add(Ticket(show, showDate, ticketData.seatType))
        }
    }
}