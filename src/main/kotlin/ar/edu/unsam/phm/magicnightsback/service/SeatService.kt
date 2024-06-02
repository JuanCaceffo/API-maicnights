package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class SeatService(
    @Autowired
    val seatRepository: SeatRepository,
    @Autowired
    val showDateRepository: ShowDateRepository,
    ) {
    @Transactional(Transactional.TxType.NEVER)
    fun findById(id: Long): Seat? =
        seatRepository.findById(id).getOrNull()

    
    fun findByIdOrError(id: Long): Seat =
        findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id.toString(), Seat::class.stringMe()))

    @Transactional(Transactional.TxType.NEVER)
    fun findAllSeatByShowDateId(showDateId: String): List<Seat> {
        val showdate = showDateRepository.findById(showDateId).get()
        return seatRepository.findAllByFacilityId(showdate.show.facilityId).toList()
    }

}