package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.NotFoundException
//import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
//import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
//import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrDefault
import kotlin.jvm.optionals.getOrNull

@Service
class TicketService(
    @Autowired
    private val ticketRepository: TicketRepository,
    @Autowired
    private val userService: UserService,
    private val hydrousService: HydrousService
) {
    fun findById(id: Long): Ticket? = ticketRepository.findById(id).getOrNull()
    fun findByIdOrError(id: Long) =
        findById(id) ?: throw NotFoundException(FindError.NOT_FOUND(id, Ticket::class.toString()))

    fun findByUserId(userId: Long): List<Ticket> =
        ticketRepository.findByUserId(userId).map { hydrousService.getHydrousTicket(it) }

    @Transactional(Transactional.TxType.NEVER)
    fun findUsersAttendingToShow(showId: String): Set<Long> =
        ticketRepository.findByShowId(showId).map { it.user.id }.toSet()

    @Transactional(Transactional.TxType.NEVER)
    fun findFriendsAttendingToShow(showId: String, userId: Long): Set<Long> {
        val user = userService.findByIdOrError(userId)
        return findUsersAttendingToShow(showId).filter { user.isMyFriend(it) }.toSet()
    }

    @Transactional(Transactional.TxType.NEVER)
    fun countFriendsAttendingToShow(showId: String, userId:Long):Int =
        ticketRepository.countFriendsByShow(showId, userId).getOrNull() ?: 0

    @Transactional(Transactional.TxType.NEVER)
    fun getTopFriendsImages(showId: String, userId:Long): List<String> =
        ticketRepository.getTopFriendsImages(showId, userId).map { it }

    fun totalShowSales(showId:String): Double =
        ticketRepository.totalShowSales(showId).getOrDefault(0.0)

    @Transactional(Transactional.TxType.REQUIRED)
    fun save(ticket: Ticket) {
        ticketRepository.save(ticket)
    }

    //TODO: ver que se necesita hidratar en estos metodos
    fun ticketSalesCountByShowDateId(id: String) =
        ticketRepository.showDateTakenCapacity(id).getOrDefault(0)

    fun ticketCountByShowId(id: Long) =
        ticketRepository.showTakenCapacity(id).getOrDefault(0)

    fun ticketCountByShowIdAndSeatId(showId: Long, seatId: Long) =
        ticketRepository.showTakenCapacitybySeatId(showId, seatId).getOrDefault(0)
}
