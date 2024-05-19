package ar.edu.unsam.phm.magicnightsback.service

//import ar.edu.unsam.phm.magicnights.utils.stringMe
//import ar.edu.unsam.phm.magicnightsback.domain.Ticket
//import ar.edu.unsam.phm.magicnightsback.domain.validateOptionalIsNotNull
//import ar.edu.unsam.phm.magicnightsback.dto.*
//import ar.edu.unsam.phm.magicnightsback.exceptions.AuthenticationException
//import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
//import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
//import ar.edu.unsam.phm.magicnightsback.exceptions.UserError
//import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.dto.FriendDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toFriendDTO
import ar.edu.unsam.phm.magicnightsback.exceptions.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.NotFoundException
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class UserService(
    @Autowired
    var userRepository: UserRepository,
) {
    @Transactional(Transactional.TxType.NEVER)
    fun findById(id: Long): User? =
        userRepository.findById(id).getOrNull()

    @Transactional(Transactional.TxType.NEVER)
    fun findByIdOrError(id: Long): User =
        findById(id) ?: throw NotFoundException(FindError.NOT_FOUND(id, User::class.toString()))

    @Transactional(Transactional.TxType.NEVER)
    fun userExists(id: Long): Boolean = userRepository.existsUserById(id)

    @Transactional(Transactional.TxType.NEVER)
    fun getBalance(userId: Long): Double = findByIdOrError(userId).balance

//    @Transactional(Transactional.TxType.NEVER)
//    fun getUserImageUrls(users: Set<Long>): Set<String> =
//        userRepository.findImageUrl(users)

    @Transactional(Transactional.TxType.NEVER)
    fun validateUserExists(id: Long) {
        if (!userExists(id)) {
            throw NotFoundException(
                FindError.NOT_FOUND(id, User::class.stringMe())
            )
        }
    }

    @Transactional(Transactional.TxType.NEVER)
    fun authenticate(username: String, password: String): User =
        userRepository.findByUsernameAndPassword(username, password).getOrNull()
            ?: throw AuthenticationException(FindError.BAD_CREDENTIALS)

    @Transactional(Transactional.TxType.NEVER)
    fun findUserFriends(id: Long): List<FriendDTO> {
        val user: User = findByIdOrError(id)
        return user.friends.map { it.toFriendDTO() }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun deleteUserFriend(userId: Long, friendId: Long): List<FriendDTO> {
        val user = findByIdOrError(userId)
        val friendToDelete = findByIdOrError(friendId)

        user.removeFriend(friendToDelete)
        userRepository.save(user)

        return user.friends.map { it.toFriendDTO() }
    }
}


//
//    @Transactional(Transactional.TxType.NEVER)
//    fun findByUsername(username: String): User = validateOptionalIsNotNull(userRepository.findByUsername(username))
//


//


//

//
//    @Transactional(Transactional.TxType.NEVER)
//    fun getLastDateBalanceModify(userId: Long): Double = findOrErrorById(userId).totalBalance()
//
//    @Transactional(Transactional.TxType.REQUIRED)
//    fun updateUserBalance(userId: Long, newBalance: Double): Double {
//        val user = findOrErrorById(userId)
//
//        user.modifyBalance(newBalance)
//        userRepository.save(user)
//
//        return user.totalBalance()
//    }
//
//    @Transactional(Transactional.TxType.REQUIRED)
//    fun updateUser(userId: Long, userUpdate: UserUpdateDTO): UserDTO {
//        val user = findOrErrorById(userId)
//
//        user.name = userUpdate.name
//        user.surname = userUpdate.surname
//
//        userRepository.save(user)
//
//        return user.toDTO()
//    }
//
//    @Transactional(Transactional.TxType.NEVER)
//    fun getBalances(userId: Long): List<UserBalanceDTO> {
//        findById(userId)
//        return userRepository.allBalances(userId)
//    }
//
//    fun findUsersWithMoreTicketsThan(ticketsQuantity: Int): List<UserDTO> {
//        return userRepository.findUsersWithMoreTicketsThan(ticketsQuantity).map { user -> user.toDTO() }
//    }
//
//    fun validateAdminStatus(id: Long) =
//        require(findOrErrorById(id).isAdmin) { throw AuthenticationException(UserError.USER_IS_NOT_ADMIN) }
//

//
////    @Transactional(Transactional.TxType.NEVER)
////    fun historyTickets(userId: Long, year: Int): List<TicketDTO> {
////        val user = findById(userId)
////        val tickets = userRepository.historyTickets(userId,year)
////        return ticketService.getTickets(user,tickets)
////    }
//}