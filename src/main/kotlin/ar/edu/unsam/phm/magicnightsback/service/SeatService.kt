package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class SeatService(
    @Autowired
    val seatRepository: SeatRepository
) {
    
    fun findById(id: Long): Seat? =
        seatRepository.findById(id).getOrNull()

    
    fun findByIdOrError(id: Long): Seat =
        findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, Seat::class.stringMe()))

    
    fun findAllSeatByShowDateId(shoDateId: Long): List<Seat> =
        seatRepository.findAllByShowDateId(shoDateId).map { it }
}