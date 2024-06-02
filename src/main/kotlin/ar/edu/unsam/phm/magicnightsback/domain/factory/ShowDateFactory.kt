package ar.edu.unsam.phm.magicnightsback.domain.factory

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import java.time.LocalDateTime

enum class ShowDateFactoryTypes {
    MINUS, PLUS
}

class ShowDateFactory {
    fun createShowDate(type: ShowDateFactoryTypes, show: Show): ShowDate = when (type) {
        ShowDateFactoryTypes.MINUS -> ShowDateMinus(show).build()
        ShowDateFactoryTypes.PLUS -> ShowDatePlus(show).build()
    }

    fun createShowDates(type: ShowDateFactoryTypes, show: Show, qty: Int): Set<ShowDate> = when (type) {
        ShowDateFactoryTypes.MINUS -> ShowDateMinus(show).buildBulk(qty)
        ShowDateFactoryTypes.PLUS -> ShowDatePlus(show).buildBulk(qty)
    }
}


class ShowDateMinus(
    override val show: Show
) : FactoryShowDate {
    override fun build() = ShowDate(show, LocalDateTime.now().minusDays(1)).apply { initSeatOcupation() }
    override fun buildBulk(quantity: Int) =
        (1 until quantity+1).map { ShowDate(show, LocalDateTime.now().minusDays(it.toLong())).apply { initSeatOcupation() } }.toSet()
}

class ShowDatePlus(
    override val show: Show,
) : FactoryShowDate {
    override fun build() = ShowDate(show, LocalDateTime.now().plusDays(1)).apply { initSeatOcupation() }
    override fun buildBulk(quantity: Int) =
        (1 until quantity+1).map { ShowDate(show, LocalDateTime.now().plusDays(it.toLong())).apply { initSeatOcupation() } }.toSet()
}