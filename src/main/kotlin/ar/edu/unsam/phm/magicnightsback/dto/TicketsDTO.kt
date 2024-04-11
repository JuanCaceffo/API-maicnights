package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.*
import java.time.LocalDate
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
        quantity,
        canBeCommented
    )
}


fun Ticket.toTicketDTO(user: User, price: Double, quantity: Int) = TicketDTO(
    this.id,
    this.show.showImg,
    this.show.name,
    this.show.band.name,
    this.show.facility.name,
    this.show.totalRating(),
    this.show.comments().size,
    price,
    listOf(this.showDate.date),
    this.show.friendsAttendeesProfileImages(user),
    quantity,
    this.show.canBeCommented(user)
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
    val quantity: Int,
    val canBeCommented: Boolean
)

data class TicketCreateDTO(
    val showId: Long,
    val date: LocalDate,
    val seatPrice: Double,
    val seatTypeName: AllSetTypeNames,
    val quantity: Int
)