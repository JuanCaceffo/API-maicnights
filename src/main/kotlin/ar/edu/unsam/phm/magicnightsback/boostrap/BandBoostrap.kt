package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Band
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service

@Service
@Order(0)
class BandBoostrap : InitializingBean {
    @Autowired
    lateinit var bandRepository: BandRepository

    val bands = mapOf(
        "LaVelaPuerca" to Band().apply {
            name = "La Vela Puerca"
            cost = 10000.0
        },
        "PearlJam" to Band().apply {
            name = "Pearl Jam"
            cost = 20000.0
        },
        "AcDc" to Band().apply {
            name = "Ac/Dc"
            cost = 30000.0
        },
        "LosRedondos" to Band().apply {
            name = "Los Redondos"
            cost = 10.0
        },
        "OneDirection" to Band().apply {
            name = "One Direction"
            cost = 20000.0
        },
        "Queen" to Band().apply {
            name = "Queen"
            cost = 30000.0
        }
    )

    fun createBands() {
        bands.values.forEach{
//            val bandInRepo = bandRepository.findByName(it.name)
//            if (bandInRepo.isPresent) {
//                it.id = bandInRepo.get().id
//            } else {
                bandRepository.save(it)
//            }
        }
    }

    override fun afterPropertiesSet() {
        println("Band creation process starts")
        createBands()
    }
}