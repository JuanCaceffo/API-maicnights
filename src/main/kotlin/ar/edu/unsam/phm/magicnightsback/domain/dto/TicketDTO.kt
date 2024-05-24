package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.interfaces.*
import java.time.LocalDateTime


data class TicketDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val prices: List<Double>,
    override val rating: Double,
    override val totalComments: Int,
    override val friendsImgs: List<String>,
    override val totalFriendsAttending: Int,
    override val dates: List<ShowDateDTO>,
    val seatId: Long,
    val showDateId: String,
    val canBeCommented: Boolean,
    val date: LocalDateTime
):  ShowStats, ShowWithFriends, ShowDates


fun Ticket.toDTO(stats: ShowExtraDataDTO): TicketDTO = TicketDTO(
    id,
    showDate.show.imgUrl(),
    showDate.show.name,
    showDate.show.band.name,
    showDate.show.facility.name,
    listOf(showDate.show.currentTicketPrice(seat)),
    stats.rating,
    stats.totalComments,
    stats.friendsImgs,
    stats.totalFriendsAttending,
    stats.dates,
    seat.id,
    showDate.id,
    canBeCommented(),
    showDate.date
)

//data class TicketDTO(
//    val ticketId: Long,
//    val data: ShowData,
//    val showStats: ShowStats,
//    val date: ShowDateDTO,
//    val price: Double,
//    val quantity: Int,
//    val canBeCommented: Boolean?
//)

//fun Ticket.toTicketDTO(commentStats: CommentStadisticsDTO? = null, user: User, price: Double, quantity: Int, canBeCommented: Boolean? = null) = TicketDTO(
//    this.id,
//    this.show.data(),
//    this.show.stats(commentStats, user),
//    this.showDate.toShowDateDTO(),
//    price,
//    quantity,
//    canBeCommented
//)

data class TicketCreateDTO(
    val showId: String,
    val showDateId: String,
    val seatTypeName: SeatTypes,
    val quantity: Int,
)

interface TicketResult {
    fun getQuantity(): Int
    fun getTicketId(): Long
    fun getShowDateId(): Long
    fun getShowId(): Long?
    fun getSeat(): String
}

data class TicketRequestDTO(
    val showDateId: String,
    val seatId: Long,
    val quantity: Int
)

data class TicketResponseDTO(
    val id: Long,
    val date: LocalDateTime?,
    val price: Double
)

//fun Ticket.toResponseDTO(): TicketResponseDTO = TicketResponseDTO(
//    this.id,
//    this.date,
//    this.price
//)