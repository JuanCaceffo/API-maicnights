package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@Order(3)
@DependsOn("showBoostrap")
class ShowDateBoostrap(
    showBoostrap: ShowBoostrap,
    val showDateRepository: ShowDateRepository
) : InitializingBean {

    val showDates = mapOf(
        "SmallShow1" to ShowDate(LocalDateTime.now().plusDays(12), showBoostrap.shows["SmallShow"]!!),
        "SmallShow2" to ShowDate(LocalDateTime.now().plusDays(13), showBoostrap.shows["SmallShow"]!!),
        "SmallShow3" to ShowDate(LocalDateTime.now().plusDays(14), showBoostrap.shows["SmallShow"]!!),
        "BigShow1" to ShowDate(LocalDateTime.now().plusDays(20), showBoostrap.shows["BigShow"]!!),
        "BigShow2" to ShowDate(LocalDateTime.of(2024,1,1,19,0,0), showBoostrap.shows["BigShow"]!!),
        "BestSmallShow1" to ShowDate(LocalDateTime.now().plusDays(35), showBoostrap.shows["BestSmallShow"]!!)
    )

    fun createShowDates() {
        showDates.values.forEach { showDateRepository.apply { create(it) } }
    }

    override fun afterPropertiesSet() {
        println("Show Date creation process starts")
        createShowDates()
        println("Show Date creation process ends")
    }
}