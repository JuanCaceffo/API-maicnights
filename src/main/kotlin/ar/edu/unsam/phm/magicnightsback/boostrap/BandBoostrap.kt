package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Band
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(0)
class BandBoostrap(
    val bandRepository : BandRepository,
) : InitializingBean {
    val bands = mapOf(
        "LaVelaPuerca" to Band("La Vela Puerca",
            10000.0),
        "PearlJam" to Band("Pearl Jam",
            20000.0),
        "AcDc" to Band("AcDc",
            30000.0)
    )

    fun createBands() {
        bands.values.forEach { band -> bandRepository.create(band) } }

    override fun afterPropertiesSet() {
        println("Band creation process starts")
        createBands()
        println("Band creation process ends")
    }
}