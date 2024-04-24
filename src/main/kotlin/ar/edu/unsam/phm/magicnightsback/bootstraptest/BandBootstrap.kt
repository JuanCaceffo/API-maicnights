package ar.edu.unsam.phm.magicnightsback.bootstraptest

import ar.edu.unsam.phm.magicnightsback.domain.Band
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
@Profile("baseBoostrap")
class BandBootstrap: InitializingBean {
    @Autowired
    lateinit var bandRepository: BandRepository

    val bands = mapOf(
        "LaVelaPuerca" to Band(name = "La Vela Puerca").apply {
            cost = 10000.0
        },
        "PearlJam" to Band(name = "Pearl Jam").apply {
            cost = 20000.0
        },
        "AcDc" to Band(name = "Ac/Dc").apply {
            cost = 30000.0
        },
        "LosRedondos" to Band(name = "Los Redondos").apply {
            cost = 10.0
        },
        "OneDirection" to Band(name = "One Direction").apply {
            cost = 20000.0
        },
        "Queen" to Band(name = "Queen").apply {
            cost = 30000.0
        }
    )

    fun createBands() {
        bands.values.forEach{
            val bandInRepo = bandRepository.findByName(it.name).getOrNull()
            if (bandInRepo != null) {
                it.id = bandInRepo.id
            } else {
                bandRepository.save(it)
                println("Band ${it.name} created")
            }
        }
    }

    override fun afterPropertiesSet() {
        println("Band creation process starts")
        createBands()
    }
}