package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Order(3)
@DependsOn("showBoostrap")
class ShowDateBoostrap(
    val showDateRepository: ShowDateRepository,
    showRepository: ShowRepository
) : InitializingBean {
    private val showDates = mapOf(
        "SmallShowDate1" to ShowDate(LocalDateTime.now().plusDays(12), showRepository.getById(0)),
        "SmallShowDate2" to ShowDate(LocalDateTime.now().plusDays(13), showRepository.getById(0)),
        "SmallShowDate3" to ShowDate(LocalDateTime.now().plusDays(14), showRepository.getById(0)),
        "BigShowDate1" to ShowDate(LocalDateTime.now().plusDays(20), showRepository.getById(1)),
        "BigShowDate2" to ShowDate(LocalDateTime.of(2024, 1, 1, 19, 0, 0), showRepository.getById(1)),
        "BestSmallShow1" to ShowDate(LocalDateTime.now().plusDays(35), showRepository.getById(2))
    )

    fun createShowDates() {
        showDates.values.forEach { showDateRepository.apply { create(it) } }
    }

    override fun afterPropertiesSet() {
        println("ShowDate creation process starts")
        createShowDates()
        println("ShowDate creation process ends")
    }

}