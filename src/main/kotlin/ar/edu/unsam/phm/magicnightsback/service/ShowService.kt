package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.controller.ShowController.ShowAdminRequest
import ar.edu.unsam.phm.magicnightsback.controller.ShowController.ShowRequest
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.dto.ShowDateDTO
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ShowService {
    @Autowired
    private lateinit var userRepository: UserRepository

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

    @Transactional(Transactional.TxType.REQUIRED)
    fun createShowDate(showId: Long, userId: Long,  body: ShowDateDTO): ShowDate {
        userService.validateAdminStatus(userId)
        val show = validateOptionalIsNotNull(showRepository.findById(showId))
        return show.addDate(body.date)
    }

    @Transactional(Transactional.TxType.NEVER)
    fun findAllAdmin(params: ShowAdminRequest): List<Show> {
        userService.validateAdminStatus(params.userId)
        return findAll(params.toShowRequest())
    }

    @Transactional(Transactional.TxType.NEVER)
    fun findByIdAdmin(showId: Long, userId: Long): Show {
        userService.validateAdminStatus(userId)
        return validateOptionalIsNotNull(showRepository.findById(showId))
    }

    @Transactional(Transactional.TxType.NEVER)
    fun findByName(name: String): Show = validateOptionalIsNotNull(showRepository.findByName(name))

    private fun filter(shows: Iterable<Show>, params: ShowRequest): List<Show> {
        val filter = createFilter(params)
        return shows.filter { show -> filter.apply(show) }
    }

    private fun createFilter(params: ShowRequest): Filter<Show> {
        val user = userRepository.findById(params.userId)
        return Filter<Show>().apply {
            addFilterCondition(BandFilter(params.bandKeyword))
            addFilterCondition(FacilityFilter(params.facilityKeyword))
            if (user.isPresent) addFilterCondition(WithFriends(params.withFriends, user.get()))
        }
    }
//
//    fun getAPossibleUserById(userId: Long) = if (userId > -1) userRepository.getById(userId) else null
}

