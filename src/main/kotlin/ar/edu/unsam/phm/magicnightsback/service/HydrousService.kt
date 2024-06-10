package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.stereotype.Service

@Service
class HydrousService(
    private val facilityRepository: FacilityRepository,
    private val bandRepository: BandRepository,
    private val showRepository: ShowRepository,
    private val showDateRepository: ShowDateRepository,
) {
    fun getHydrousShow(show: Show) = show.apply {
        val facilityFound = facilityRepository.findById(show.facilityId).get()
        val bandFound = bandRepository.findById(show.bandId).get()
        facility = facilityFound
        facilityName = facilityFound.name
        band = bandFound
        bandName = bandFound.name
    }

    fun getHydrousComment(comment: Comment) = comment.apply {
        show = getHydrousShow(showRepository.findById(comment.showId).get())
    }

    fun getHydrousTicket(ticket: Ticket) = ticket.apply {
        showDate = showDateRepository.findById(ticket.showDateId).get().apply {
            show = getHydrousShow(show)
        }
    }

    fun getHydrousShowDate(showDate: ShowDate) = showDate.apply {
        show = getHydrousShow(show)
        initSeatOcupation()
    }
}