package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowStatsDTO
import ar.edu.unsam.phm.magicnightsback.domain.enums.StatColors
import java.util.UUID

class AdminStatBuilder {
    private val stats = mutableListOf<ShowStatsDTO>()

    fun statSales(value: Double, pivots: Pivots): AdminStatBuilder {
        stats.add(ShowStatsDTO(UUID.randomUUID(), value, calcular(value, pivots)))
        return this
    }

    fun statPending(value: Int, pivots: Pivots, show: Show): AdminStatBuilder {
        val colorValue = show.allTicketPrices().min() * value
        stats.add(ShowStatsDTO(UUID.randomUUID(), value.toDouble(), calcular(colorValue, pivots)))
        return this
    }

    fun statRentability(value: Double, pivots: Pivots): AdminStatBuilder {
        stats.add(ShowStatsDTO(UUID.randomUUID(), value, calcular(value, pivots)))
        return this
    }

    fun statSoldOut(value: Int, pivots: Pivots, totalDates: Int): AdminStatBuilder {
        val colorValue = value / totalDates * 100.0
        stats.add(ShowStatsDTO(UUID.randomUUID(), value.toDouble(), calcular(colorValue, pivots)))
        return this
    }

    private fun calcular(value: Double, pivots: Pivots): StatColors {
        return if (value <= pivots.lowPivot) {
            StatColors.RED
        } else if (value <= pivots.highPivot) {
            StatColors.YELLOW
        } else {
            StatColors.GREEN
        }
    }

    fun build() = stats
}

data class Pivots(val lowPivot: Double, val highPivot: Double)