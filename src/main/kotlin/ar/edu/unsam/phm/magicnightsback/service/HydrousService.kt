package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.stereotype.Service

enum class ShowFieldsToHydrous{
    FACILITY,
    BAND
}

@Service
class HydrousService(
    private val facilityRepository: FacilityRepository,
    private val bandRepository: BandRepository,
    private val showRepository: ShowRepository
) {

    fun getHydrousShow(show: Show,vararg fields: ShowFieldsToHydrous = arrayOf(ShowFieldsToHydrous.FACILITY,ShowFieldsToHydrous.BAND)) = show.apply {
        fields.forEach { field ->
            when (field){
                ShowFieldsToHydrous.FACILITY -> facility = facilityRepository.findById(show.facilityId).get()
                ShowFieldsToHydrous.BAND -> band = bandRepository.findById(show.bandId).get()
            }
        }
    }

    fun getHydrousComment(comment: Comment, vararg fields: ShowFieldsToHydrous = arrayOf(ShowFieldsToHydrous.FACILITY,ShowFieldsToHydrous.BAND)) = comment.apply {
        show = getHydrousShow(showRepository.findById(comment.showId).get(), *fields)
    }
}