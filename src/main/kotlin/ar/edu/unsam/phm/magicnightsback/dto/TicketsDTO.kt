package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.*
import java.time.LocalDateTime

data class TicketDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val rating: Double,
    val totalComments: Int,
    val price: Double,
    val dates: List<LocalDateTime>,
    val userImageNames: List<String>,
    val quantity: Int,
    val canBeCommented: Boolean
){
    fun toTicketCartDTO(): TicketCartDTO = TicketCartDTO(
        id,
        showImg,
        showName,
        bandName,
        facilityName,
        rating,
        totalComments,
        price,
        dates,
        userImageNames,
        quantity
    )

    fun toPurchasedTicketDTO(): PurchasedTicketDTO = PurchasedTicketDTO(
        id,
        showImg,
        showName,
        bandName,
        facilityName,
        rating,
        totalComments,
        price,
        dates,
        userImageNames,
        canBeCommented
    )
}


fun Ticket.toTicketDTO(user: User, showDates: List<LocalDateTime>, price: Double, quantity: Int) = TicketDTO(
    this.id,
    this.show.showImg,
    this.show.name,
    this.show.band.name,
    this.show.facility.name,
    this.show.totalRating(),
    this.show.comments().size,
    price,
    showDates,
    this.show.friendsAttendeesProfileImages(user),
    quantity,
    this.show.canBeCommented(user)
)

data class TicketCartDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val rating: Double,
    val totalComments: Int,
    val price: Double,
    val dates: List<LocalDateTime>,
    val userImageNames: List<String>,
    val quantity: Int
)

data class PurchasedTicketDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val rating: Double?,
    val totalComments: Int?,
    val price: Double?,
    val dates: List<LocalDateTime>,
    val userImageNames: List<String>,
    val canBeCommented: Boolean
)

data class TicketCreateDTO(
    val showId: Long,
    val showDateId: Long,
    val price: Double,
    val seatTypeName: AllSetTypeNames,
    val quantity: Int
)