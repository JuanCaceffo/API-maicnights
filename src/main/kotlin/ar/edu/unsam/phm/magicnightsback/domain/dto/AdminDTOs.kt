//package ar.edu.unsam.phm.magicnightsback.dto
//
//import ar.edu.unsam.phm.magicnightsback.domain.Show
//
//
//data class ShowAdminDetailsDTO(
//    val show: ShowDTO,
//    val totalTicketsSold: Int,
//    val ticketsSoldPerSeatType: List<TicketsPerSeatType>,
//    val totalSales: Double,
//    val showCost: Double,
//    val pendingAttendees: Int
//)
//
////data class ShowStatsDTO (
////    val id: Long,
////    val value: Double,
////    val state: States
////)
//
//data class TicketsPerSeatType(
//    val name: String,
//    val tickets: Int
//)
//
////fun Show.toShowAdminDetailsDTO() = ShowAdminDetailsDTO(
////    this.toShowDTO(),
////    this.totalTicketsSold(),
////    this.getSeatTypes().map { TicketsPerSeatType(it.seatType.name, totalTicketsSoldOf(it.seatType)) },
////    this.totalSales(),
////    this.baseCost(),
////    this.totalPendingAttendees()
////)
//
