package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Band
import org.springframework.stereotype.Repository

@Repository
class BandRepository : CustomRepository<Band>() {
}