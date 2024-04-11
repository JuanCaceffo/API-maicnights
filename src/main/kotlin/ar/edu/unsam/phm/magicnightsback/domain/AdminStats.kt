package ar.edu.unsam.phm.magicnightsback.domain

object AdminStats {
    val stats = mutableListOf<StatProps>(
        StatSales(1000000.0, 2000000.0),
        StatPending(0.0,50.0),
        StatRentability(0.0,50.0),
        StatSoldOut(50.0,75.0))
    fun newDateAvailable(show: Show) = stats.all { it.newDateCondition(show) }
    fun getAllStats(show: Show) = stats.map { it.getStat(show) }
}