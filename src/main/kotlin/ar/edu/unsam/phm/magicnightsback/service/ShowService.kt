package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.controller.ShowController.*

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShowService {
    @Autowired
    lateinit var showRepository: ShowRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Transactional(Transactional.TxType.NEVER)
    fun findAll(params: ShowRequest): List<Show> {
        val shows = showRepository.findAll()
        val filteredShows = filter(shows, params)

        return filteredShows
    }

    @Transactional(Transactional.TxType.NEVER)
    fun findById(showId: Long): Show {
        //  return show.toShowDTO(showService.getAPossibleUserById(userId),comments)
        return validateOptionalIsNotNull(showRepository.findById(showId))
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
    fun findAllAdmin(params: ShowAdminRequest): List<Show> {
        validateOptionalIsNotNull(userRepository.findById(params.userId)).validateAdminStatus(UserError.USER_IS_NOT_ADMIN)
        return findAll(params.toShowRequest())
    }

    fun findByName(name: String): Show = validateOptionalIsNotNull(showRepository.findByName(name))


    private fun filter(shows: Iterable<Show>, params: ShowRequest): List<Show> {
        val filter = createFilter(params)
        return shows.filter { show -> filter.apply(show) }
    }

    private fun createFilter(params: ShowRequest): Filter<Show> {
        return Filter<Show>().apply {
            addFilterCondition(BandFilter(params.bandKeyword))
            addFilterCondition(FacilityFilter(params.facilityKeyword))
            addFilterCondition(WithFriends(params.withFriends, params.userId))
        }
    }
//
//    fun getAPossibleUserById(userId: Long) = if (userId > -1) userRepository.getById(userId) else null
}

