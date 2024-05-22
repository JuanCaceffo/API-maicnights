package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import ar.edu.unsam.phm.magicnightsback.repository.TicketRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ShowDateService(
    @Autowired
    private val showDateRepository: ShowDateRepository,
    private val ticketRepository: TicketRepository,

    @Autowired
    private val hydrousService: HydrousService
) {

    fun getHydrousShowDate(showDate: ShowDate)= showDate.apply { show = hydrousService.getHydrousShow(show) }

    fun getAllHydrousShowDates() = showDateRepository.findAll().map { showDate ->  getHydrousShowDate(showDate)}

    fun findById(id: String): ShowDate? {
        return showDateRepository.findById(id).getOrNull()
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

    //TODO: ver que onda con esto!!
//    fun isSoldOut(showDateId: String): Boolean {
//        val reputo = findByIdOrError(showDateId).show.totalCapacity()
//        val megaputo = ticketRepository.showDateTakenCapacity(showDateId)
//        return reputo.minus(megaputo) <= 0
//    }
}