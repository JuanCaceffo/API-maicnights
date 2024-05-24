package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowDateDTO
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import ar.edu.unsam.phm.magicnightsback.repository.TicketRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrDefault
import kotlin.jvm.optionals.getOrNull

@Service
class ShowDateService(
    private val showDateRepository: ShowDateRepository,
    private val ticketRepository: TicketRepository,
    private val hydrousService: HydrousService
) {

    fun getHydrousShowDate(showDate: ShowDate)= showDate.apply { show = hydrousService.getHydrousShow(show) }

    fun getAllHydrousShowDates() = showDateRepository.findAll().map { showDate ->  getHydrousShowDate(showDate)}

    fun findById(id: String): ShowDate? {
        return showDateRepository.findById(id).getOrNull()
    }

    //TODO: hidratar el seat del seatOcupation
    fun isSoldOut(id: String): Boolean {
        return findByIdOrError(id).isSoldOut()
    }

    //TODO: hidratar el seat del seatOcupation
    fun isShowSoldOut(showId: String): Boolean {
        return findAllByShowId(showId).all {
            isSoldOut(it.id)
        }
    }


    fun showCost(id: String): Double =
        showDateRepository.findAllShowCosts(id).sum()

    @Transactional(Transactional.TxType.REQUIRED)
    fun save(showDate: ShowDate) {
        showDateRepository.save(showDate)
    }

    fun findHydrousById(id: String): ShowDate? {
        val showDate = showDateRepository.findById(id).getOrNull()
        return this.getHydrousShowDate(showDate!!)
    }

    fun findByIdOrError(id: String): ShowDate {
        return findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, ShowDate::class.stringMe()))
    }

    fun findHydrousByIdOrError(id: String): ShowDate {
        val showDate = findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, ShowDate::class.stringMe()))
        return this.getHydrousShowDate(showDate)
    }

    fun findAllByShowId(showId: String): List<ShowDate> =
        showDateRepository.findAllByShowId(showId)

}