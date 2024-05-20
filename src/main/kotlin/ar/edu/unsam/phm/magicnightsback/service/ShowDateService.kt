package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class ShowDateService(
    @Autowired
    private val showDateRepository: ShowDateRepository,

    @Autowired
    private val seatService: SeatService
) {
    fun findById(id: Long): ShowDate? =
        showDateRepository.findById(id).getOrNull()

    fun findByIdOrError(id: Long): ShowDate =
        findById(id) ?: throw ResponseFindException (FindError.NOT_FOUND(id, ShowDate::class.stringMe()))

    fun findAllByShowId(showId: Long): List<ShowDate> =
        showDateRepository.findAllByShowId(showId).map { it }
}