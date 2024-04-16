package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.controller.BaseFilterParams
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.dto.CommentRatingDTO
import ar.edu.unsam.phm.magicnightsback.dto.ShowDTO
import ar.edu.unsam.phm.magicnightsback.dto.toShowDTO
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShowService {
    @Autowired
    lateinit var commentService: CommentService

    @Autowired
    lateinit var showRepository: ShowRepository

    @Transactional(Transactional.TxType.NEVER)
    fun findAll(params: BaseFilterParams): List<ShowDTO> {
        val shows = showRepository.findAll()
        val filteredShows = filter(shows, params)

        return filteredShows.map {
            val totalRating = commentService.showRating(it.id)
            val totalComments = commentService.totalShowComments(it.id)
            it.toShowDTO(CommentRatingDTO(totalRating, totalComments))
        }
    }
//
//    fun createShowDate(showId: Long, userId: Long, date: LocalDateTime) {
//        val show = getById(showId)
//        if (!AdminStats.newDateAvailable(show)) {
//            throw BusinessException(showDateError.NEW_SHOW_INVALID_CONDITIONS)
//        }
//        showRepository.getById(showId).addDate(date)
//    }
//

    fun findById(id: Long): Show = validateOptionalIsNotNull(showRepository.findById(id))

    fun findByName(name: String): Show = validateOptionalIsNotNull(showRepository.findByName(name))

    private fun filter(shows: Iterable<Show>, params: BaseFilterParams): List<Show> {
        val filter = createFilter(params)
        return shows.filter { show -> filter.apply(show) }
    }

    private fun createFilter(params: BaseFilterParams): Filter<Show> {
        return Filter<Show>().apply {
            addFilterCondition(BandFilter(params.bandKeyword))
            addFilterCondition(FacilityFilter(params.facilityKeyword))
            addFilterCondition(WithFriends(params.withFriends, params.userId))
        }
    }
//
//    fun getAPossibleUserById(userId: Long) = if (userId > -1) userRepository.getById(userId) else null
}

