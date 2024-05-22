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
    @Autowired private val ticketRepository: TicketRepository,
    @Autowired private val userService: UserService
) {
    fun findById(id: Long): Ticket? = ticketRepository.findById(id).getOrNull()
    fun findByIdOrError(id: Long) =
        findById(id) ?: throw NotFoundException(FindError.NOT_FOUND(id, Ticket::class.toString()))

    fun findByUserId(userId: Long): List<Ticket> =
        ticketRepository.findByUserId(userId).map { it }

    fun findUsersAttendingToShow(showId: Long): Set<Long> =
        ticketRepository.findByShowDateShowId(showId).map { it.user.id }.toSet()

    fun findFriendsAttendingToShow(showId: Long, userId: Long): Set<Long> {
        val user = userService.findByIdOrError(userId)
        return findUsersAttendingToShow(showId).filter { user.isMyFriend(it) }.toSet()
    }

    fun countFriendsAttendingToShow(showId:Long, userId:Long):Int =
        ticketRepository.countFriendsByShow(showId, userId).getOrNull() ?: 0

    fun getTopFriendsImages(showId:Long, userId:Long): List<String> =
        ticketRepository.getTopFriendsImages(showId, userId).map { it }

    fun totalShowSales(showId:Long): Double =
        ticketRepository.totalShowSales(showId).getOrDefault(0.0)

    @Transactional(Transactional.TxType.REQUIRED)
    fun save(ticket: Ticket) {
        ticketRepository.save(ticket)
    }

    fun ticketSalesCountByShowDateId(id: Long) =
        ticketRepository.showDateTakenCapacity(id).getOrDefault(0)

    fun ticketCountByShowId(id: Long) =
        ticketRepository.showTakenCapacity(id).getOrDefault(0)

    fun ticketCountByShowIdAndSeatId(showId: Long, seatId: Long) =
        ticketRepository.showTakenCapacitybySeatId(showId, seatId).getOrDefault(0)
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
//    
//    fun boughtBy(seatType: SeatTypes, showDateId: Long): Int =
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
