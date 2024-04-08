package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show
import java.time.LocalDateTime

data class ShowAdminDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val dates: List<LocalDateTime>,
    val prices: List<Double>
)

fun Show.toShowAdminDTO() =
    ShowAdminDTO(
        this.id,
        this.showImg,
        this.name,
        this.band.name,
        this.facility.name,
        this.allDates(),
        this.allTicketPrices()
    )

data class ShowStatsDTO (
    val id: Long,
    val totalSales: Double,
    val pendingAttendees: Int,
    val rentability: Double,
    val soldOutDates: Int
)

fun Show.toShowStatsDTO() = ShowStatsDTO(
    this.id,
    this.totalSales(),
    this.pendingAttendees.size,
    this.rentability(),
    this.soldOutDates()
)