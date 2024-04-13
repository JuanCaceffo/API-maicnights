//package ar.edu.unsam.phm.magicnightsback.domain
//
//import ar.edu.unsam.phm.magicnightsback.dto.ShowStatsDTO
//
//enum class Colors {
//    red,
//    yellow,
//    green
//}
//
//interface StatProps {
//    val lowPivot: Double
//    val highPivot: Double
//    fun statusColor(show: Show): Colors
//    fun newDateCondition(show: Show): Boolean
//    fun getStat(show: Show): ShowStatsDTO
//}
//
//fun calcular(value: Double, stat: StatProps): Colors {
//    return if (value <= stat.lowPivot) {
//        Colors.red
//    } else if (value <= stat.highPivot) {
//        Colors.yellow
//    } else {
//        Colors.green
//    }
//}
//
//class StatSales(override val lowPivot: Double, override val highPivot: Double) : StatProps {
////Monto de ingresos. (rojo si no supera $1.000.000, amarillo hasta $2.000.000 y verde en adelante)
//    override fun statusColor(show: Show): Colors = calcular(show.totalSales(), this)
//    override fun newDateCondition(show: Show) = true
//
//    override fun getStat(show: Show) = ShowStatsDTO(show.id, show.totalSales().truncate(), statusColor(show))
//}
//
//class StatPending(override val lowPivot: Double, override val highPivot: Double) : StatProps {
//
//    override fun statusColor(show: Show): Colors {
//        val value = show.allTicketPrices().min() * show.pendingAttendees.size
//        return calcular(value, this)
//    }
//    override fun getStat(show: Show) = ShowStatsDTO(show.id, show.pendingAttendees.size.toDouble().truncate(), statusColor(show))
//    override fun newDateCondition(show: Show) = statusColor(show) != Colors.red
//}
//
//class StatRentability(override val lowPivot: Double, override val highPivot: Double) : StatProps {
//    override fun statusColor(show: Show): Colors = calcular(show.rentability(), this)
//    override fun getStat(show: Show) = ShowStatsDTO(show.id, show.rentability().truncate(), statusColor(show))
//    override fun newDateCondition(show: Show) = statusColor(show) != Colors.red
//}
//
//class StatSoldOut(override val lowPivot: Double, override val highPivot: Double) : StatProps {
//    override fun statusColor(show: Show): Colors {
//        val value = show.soldOutDates() / show.dates.size * 100.0
//        return calcular(value, this)
//    }
//    override fun getStat(show: Show) = ShowStatsDTO(show.id, show.soldOutDates().toDouble().truncate(), statusColor(show))
//    override fun newDateCondition(show: Show) = statusColor(show) != Colors.red
//}