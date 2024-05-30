package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ShowDateService(
    private val showDateRepository: ShowDateRepository,
    private val getHydrousShowDate: HydrousService,
) {


    fun getAllHydrousShowDates() = showDateRepository.findAll().map { showDate ->  getHydrousShowDate.getHydrousShowDate(showDate)}

    fun findById(id: String): ShowDate? {
        return showDateRepository.findById(id).getOrNull()
    }

    //TODO: SEGUIMIENTO DE HIDRATACION (puede que no funcione y se requiera tratar en la entidad la inicializacion de las variables dependeientes de otra froma)
    fun isSoldOut(id: String): Boolean {
        return getHydrousShowDate.getHydrousShowDate(findByIdOrError(id)).isSoldOut()
    }

    fun isShowSoldOut(showId: String): Boolean {
        return findAllByShowId(showId).all {
            isSoldOut(it.id)
        }
    }


    fun showCost(id: String): Double =
        showDateRepository.findAllByShowId(id).sumOf {  it.show.cost }


    fun findHydrousById(id: String): ShowDate? {
        val showDate = showDateRepository.findById(id).getOrNull()
        return getHydrousShowDate.getHydrousShowDate(showDate!!)
    }

    fun findByIdOrError(id: String): ShowDate {
        return findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, ShowDate::class.stringMe()))
    }

    fun findHydrousByIdOrError(id: String): ShowDate {
        val showDate = findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, ShowDate::class.stringMe()))
        return getHydrousShowDate.getHydrousShowDate(showDate)
    }

    fun findAllByShowId(showId: String): List<ShowDate> =
        showDateRepository.findAllByShowId(showId)

}
