package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.controller.ShowController.ShowRequest
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.dto.ShowDateDTO
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShowService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var facilityRepository: FacilityRepository

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
    fun findByIdAdmin(showId: Long, userId: Long): Show {
        userService.validateAdminStatus(userId)
        return findById(showId)
    }

    @Transactional(Transactional.TxType.NEVER)
    fun findByName(name: String): Show = validateOptionalIsNotNull(showRepository.findByName(name))

    fun getBusyFacilities(): List<Facility> {
        val facilitiesID = showRepository.busyFacilities().map { it }
        return facilityRepository.findAll().filter { it.id in facilitiesID }
    }

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

