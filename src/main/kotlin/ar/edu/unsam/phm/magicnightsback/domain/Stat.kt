package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.dto.ShowStatsDTO

enum class States {
    RED,
    YELLOW,
    GREEN
}

interface StatProps {
    val lowPivot: Double
    val highPivot: Double
    fun statusColor(show: Show): States
    fun newDateCondition(show: Show): Boolean
    fun getStat(show: Show): ShowStatsDTO
}

fun calcular(value: Double, stat: StatProps): States {
    return if (value <= stat.lowPivot) {
        States.RED
    } else if (value <= stat.highPivot) {
        States.YELLOW
    } else {
        States.GREEN
    }
}

class StatSales(override val lowPivot: Double, override val highPivot: Double) : StatProps {
//Monto de ingresos. (rojo si no supera $1.000.000, amarillo hasta $2.000.000 y verde en adelante)
    override fun statusColor(show: Show): States = calcular(show.totalSales(), this)
    override fun newDateCondition(show: Show) = true

    override fun getStat(show: Show) = ShowStatsDTO(show.id, show.totalSales().truncate(), statusColor(show))
}

class StatPending(override val lowPivot: Double, override val highPivot: Double) : StatProps {

    override fun statusColor(show: Show): States {
        val value = show.allTicketPrices().min() * show.totalPendingAttendees()
        return calcular(value, this)
    }
    override fun getStat(show: Show) = ShowStatsDTO(show.id, show.totalPendingAttendees().toDouble().truncate(), statusColor(show))
    override fun newDateCondition(show: Show) = statusColor(show) != States.RED
}

class StatRentability(override val lowPivot: Double, override val highPivot: Double) : StatProps {
    override fun statusColor(show: Show): States = calcular(show.rentability(), this)
    override fun getStat(show: Show) = ShowStatsDTO(show.id, show.rentability().truncate(), statusColor(show))
    override fun newDateCondition(show: Show) = statusColor(show) != States.RED
}

class StatSoldOut(override val lowPivot: Double, override val highPivot: Double) : StatProps {
    override fun statusColor(show: Show): States {
        val value = show.soldOutDates() / show.dates.size * 100.0
        return calcular(value, this)
    }
    override fun getStat(show: Show) = ShowStatsDTO(show.id, show.soldOutDates().toDouble().truncate(), statusColor(show))
    override fun newDateCondition(show: Show) = statusColor(show) != States.RED
}