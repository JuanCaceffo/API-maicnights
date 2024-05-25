package ar.edu.unsam.phm.magicnightsback.domain.factory

import ar.edu.unsam.phm.magicnightsback.domain.Band
import jakarta.persistence.Column
import org.springframework.stereotype.Component

enum class BandFactoryTypes {
    CHEAP, NORMAL, EXPENSIVE
}

@Component
class BandFactory {
    fun createFacility(type: BandFactoryTypes) = when (type) {
        BandFactoryTypes.CHEAP -> CheapBand().build()
        BandFactoryTypes.NORMAL -> NormalBand().build()
        BandFactoryTypes.EXPENSIVE -> ExpensiveBand().build()
    }
}

class CheapBand() : FactoryObject<Band> {
    override fun build(): Band = Band("La Vela Puerca", 50000.0)
}

class NormalBand() : FactoryObject<Band> {
    override fun build(): Band = Band("Pearl Jam", 200000.0)
}

class ExpensiveBand() : FactoryObject<Band> {
    override fun build(): Band = Band("AC/DC", 500000.0)
}