package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.validateOptionalIsNotNull
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var showRepository: ShowRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var commentService: CommentService

    @Transactional(Transactional.TxType.NEVER)
    fun findById(id: Long): User =
        validateOptionalIsNotNull(userRepository.findById(id), "El usuario de id ${id} no fue encontrado")

    @Transactional(Transactional.TxType.NEVER)
    fun findByUsername(username: String): User = validateOptionalIsNotNull(userRepository.findByUsername(username))

    @Transactional(Transactional.TxType.NEVER)
    fun authenticate(username: String, password: String): User {
        val user = findByUsername(username)

        if (user.password != password) {
            throw AuthenticationException(UserError.BAD_CREDENTIALS)
        }

        return user
    }

    /*Mapeo todos los tickets en uno solo por showDate juntando el precio total*/
    @Transactional(Transactional.TxType.NEVER)
    fun getTicketsGroupedByShowDate(user: User, ticketList: List<Ticket>): List<TicketDTO> {

        val distinctTickets = ticketList.distinctBy { it.showDate.id }
        return distinctTickets.map { uniqueTicket ->
            val ticketsSameShowDate = ticketList.filter { ticket -> ticket.showDate.id == uniqueTicket.showDate.id }
            val totalPrice = ticketsSameShowDate.sumOf { ticket -> ticket.price }
            val quantity = ticketsSameShowDate.sumOf { ticket -> ticket.quantity }
            val commentsStats = commentService.getCommentStadisticsOfShow(uniqueTicket.show.id)
            uniqueTicket.toTicketDTO(commentsStats, user, totalPrice, quantity)
        }
    }

    @Transactional(Transactional.TxType.NEVER)
    fun getPurchasedTickets(userId: Long): List<TicketDTO> {
        val user = findById(userId)
        return getTicketsGroupedByShowDate(user, user.tickets).map { it }
    }

    @Transactional(Transactional.TxType.NEVER)
    fun getUserFriends(id: Long): List<FriendDTO> {
        val user: User = findById(id)
        return user.friends.map { userFriend -> userFriend.toFriendDTO() }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun deleteUserFriend(userId: Long, friendId: Long): List<FriendDTO> {
        val user = findById(userId)
        val friendToDelete = findById(friendId)

        user.removeFriend(friendToDelete)
        userRepository.save(user)

        return user.friends.map { it.toFriendDTO() }
    }

    @Transactional(Transactional.TxType.NEVER)
    fun getUserBalance(userId: Long): Double = findById(userId).totalBalance()

    @Transactional(Transactional.TxType.NEVER)
    fun getLastDateBalanceModify(userId: Long): Double = findById(userId).totalBalance()

    @Transactional(Transactional.TxType.REQUIRED)
    fun updateUserBalance(userId: Long, newBalance: Double): Double {
        val user = findById(userId)

        user.modifyBalance(newBalance)
        userRepository.save(user)

        return user.totalBalance()
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun updateUser(userId: Long, userUpdate: UserUpdateDTO): UserDTO {
        val user = findById(userId)

        user.name = userUpdate.name
        user.surname = userUpdate.surname

        userRepository.save(user)

        return user.toDTO()
    }

    @Transactional(Transactional.TxType.NEVER)
    fun getBalances(userId: Long): List<UserBalanceDTO> {
        findById(userId)
        return userRepository.allBalances(userId)
    }

    fun findUsersWithMoreTicketsThan(ticketsQuantity: Int): List<UserDTO> {
        return userRepository.findUsersWithMoreTicketsThan(ticketsQuantity).map { user -> user.toDTO() }
    }

    fun validateAdminStatus(id: Long) =
        require(findById(id).isAdmin) { throw AuthenticationException(UserError.USER_IS_NOT_ADMIN) }

    @Transactional(Transactional.TxType.NEVER)
    fun historyTickets(userId: Long, year: Int): List<TicketDTO> {
        val user = findById(userId)
        val tickets = userRepository.historyTickets(userId,year).map {
            ticketResult ->
            val show = ticketResult.getShowId()?.let { validateOptionalIsNotNull(showRepository.findById(it)) } ?: throw BusinessException("El show id ingresado es null")
            Ticket(
                show,
                show.getShowDateById(ticketResult.getShowDateId()),
                SeatTypes.valueOf(ticketResult.getSeat()),
                ticketResult.getQuantity()
            ).apply {
                id = ticketResult.getTicketId()
            }
        }
        return getTicketsGroupedByShowDate(user,tickets)
    }
}