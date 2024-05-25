package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowDateDTO
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrDefault
import kotlin.jvm.optionals.getOrNull

@Service
class ShowDateService(
    @Autowired private val showDateRepository: ShowDateRepository
) {
    fun findById(id: Long): ShowDate? =
        showDateRepository.findById(id).getOrNull()

    fun findByIdOrError(id: Long): ShowDate =
        findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, ShowDate::class.stringMe()))

    fun findAllByShowId(showId: Long): List<ShowDate> =
        showDateRepository.findAllByShowId(showId).map { it }

    fun isSoldOut(id: Long): Boolean {
        return findByIdOrError(id).isSoldOut()
    }

    fun isShowSoldOut(showId: Long): Boolean {
        return findAllByShowId(showId).all {
            isSoldOut(it.id)
        }
    }

    fun showCost(id: Long): Double =
        showDateRepository.showCost(id).getOrDefault(0.0)

    @Transactional(Transactional.TxType.REQUIRED)
    fun save(showDate: ShowDate) {
        showDateRepository.save(showDate)
    }
}