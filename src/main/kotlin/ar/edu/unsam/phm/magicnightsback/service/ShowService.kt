package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.controller.ShowController.*

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.dto.ShowDateDTO
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service
class ShowService {
    @Autowired
    lateinit var showRepository: ShowRepository

    @Autowired
    lateinit var userService: UserService

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

    fun createShowDate(showDate: ShowDateDTO): ShowDate {
        userService.validateAdminStatus(showDate.userId)
        val show = validateOptionalIsNotNull(showRepository.findById(showDate.showId))
        return show.addDate(parseLocalDateTime(showDate.date))
    }

    fun findAllAdmin(params: ShowAdminRequest): List<Show> {
        userService.validateAdminStatus(params.userId)
        return findAll(params.toShowRequest())
    }

    fun findByIdAdmin(showId: Long, userId: Long): Show{
        userService.validateAdminStatus(userId)
        return validateOptionalIsNotNull(showRepository.findById(showId))
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

