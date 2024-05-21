package ar.edu.unsam.phm.magicnightsback.service
//
//import ar.edu.unsam.phm.magicnights.utils.stringMe
//import ar.edu.unsam.phm.magicnightsback.domain.ShoppingCart
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
//import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
//import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
//import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

//import java.time.LocalDateTime
//import kotlin.jvm.optionals.getOrNull
//
@Service
class TicketService(
    @Autowired
    private val ticketRepository: TicketRepository,
    @Autowired
    private val userService: UserService,
    private val hydrousService: HydrousService
) {

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

    @Transactional(Transactional.TxType.REQUIRED)
    fun save(ticket: Ticket) {
        ticketRepository.save(ticket)
    }
}
//
//    // Read methods
//    
//    fun findAll(): List<Ticket> = ticketRepository.findAll().map { it }
//
//    
//    fun findById(id: Long): Ticket? =
//        ticketRepository.findById(id).getOrNull()
//
//    
//    fun findOrErrorById(id: Long): Ticket =
//        findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, Ticket::class.stringMe()))
//
//    
//    fun findByDate(date: LocalDateTime): Ticket? = ticketRepository.findByDateIs(date).getOrNull()
//

//
////    
////    fun findUserReservations(userId: Long): List<Ticket> =
////        ticketRepository.findByUserIdAndStatusIs(userId, TicketStatus.RESERVED).map { it }
//
//    // Read count methods
//    @Transactional(Transactional.TxType.NEVER)
//    fun boughtBy(seatType: SeatTypes, showDateId: String): Int =

//        ticketRepository.countBySeatAndShowDateId(seatType, showDateId)
//
//    // Create methods
//    @Transactional(Transactional.TxType.REQUIRED)
//    fun save(ticket: Ticket) {
//
//
//        val cart = ShoppingCart()
//
//
//
//
//
////        val showDate = validateReservation(ticket)
////        val user = userService.findById(ticket.userId)
////
////        if (ticketReservations.keys.contains(user.id)) {
////            ticketReservations[user.id]?.add(ticket)
////        } else {
////            ticketReservations[user.id] = mutableListOf(ticket)
////        }
//
////        val showDate = validateReservation(ticket)
////
////        return ticketRepository.save(
////            ticket.apply {
////                status = TicketStatus.RESERVED
////                price = showDate.currentPrice(ticket.seat)
////            }
////        )
//    }
//
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun confirmBuy(userId: Long): Boolean {
////        val reservedTickets = findUserReservations(userId)
////        val total = reservedTickets.sumOf { it.price }
////        userService.modifyBalance(userId, total)
////        return reservedTickets.all { updateToPurchased(it).status == TicketStatus.BOUGHT }
////    }
//
////    
////    fun totalToPay(userId: Long) = findUserReservations(userId).sumOf { it.price }
//
//    // Delete Methods
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun deleteByUUID(id: Long) {
////        val foundTicket = findOrErrorByUUID(id)
////        ticketRepository.delete(foundTicket)
////    }
//
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun updateToPurchased(ticket: Ticket): Ticket {
////        ticket.updateToPurchased()
////        return ticketRepository.save(ticket)
////    }
//}
//
////class TicketService {
//
////    @Autowired lateinit var showRepository: ShowRepository
////    @Autowired lateinit var commentService: CommentService
////
////
////    /*Mapeo todos los tickets en uno solo por showDate juntando el precio total*/
////    
////    fun getTicketsGroupedByShowDate(user: User, ticketList: List<Ticket>): List<TicketDTO> {
////
////        val distinctTickets = ticketList.distinctBy { it.showDate.id }
////        return distinctTickets.map { uniqueTicket ->
////            val ticketsSameShowDate = ticketList.filter { ticket -> ticket.showDate.id == uniqueTicket.showDate.id }
////            val totalPrice = ticketsSameShowDate.sumOf { ticket -> ticket.price }
////            val quantity = ticketsSameShowDate.sumOf { ticket -> ticket.quantity }
////            val commentsStats = commentService.getCommentStadisticsOfShow(uniqueTicket.show.id)
////            val canBeCommented = commentService.canBeCommented(uniqueTicket.showDate, user,uniqueTicket.show)
////            uniqueTicket.toTicketDTO(commentsStats, user, totalPrice, quantity, canBeCommented)
////        }
////    }
////
////    
////    fun getTickets(user: User,ticketResults: List<TicketResult>): List<TicketDTO> {
////        val tickets = ticketResults.map {
////                ticketResult ->
////            val show = ticketResult.getShowId()?.let { validateOptionalIsNotNull(showRepository.findById(it)) } ?: throw BusinessException("El show id ingresado es null")
////            Ticket(
////                show,
////                show.getShowDateById(ticketResult.getShowDateId()),
////                SeatTypes.valueOf(ticketResult.getSeat()),
////                ticketResult.getQuantity()
////            ).apply {
////                id = ticketResult.getTicketId()
////            }
////        }
////        return getTicketsGroupedByShowDate(user,tickets)
////    }
////}
