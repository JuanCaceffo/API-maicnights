package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show

data class StatsDTO (
    val sales: Double,
    val pendingAttendees: Int,
    val rentability: Double,
    val soldOutDates: Int
)


