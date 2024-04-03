package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.controller.BaseFilterParams
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ShowService {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var showRepository: ShowRepository

    fun getAll(params: BaseFilterParams): Iterable<Show> {
        val shows = showRepository.getAll()
        return filter(shows, params)
    }

    fun createShowDate(showId: Long, userId: Long, date: LocalDateTime) {
        userRepository.getById(userId).throwIfNotAdmin(UserError.USER_NOT_AUTHORIZED_CREATE_DATE)
        showRepository.getById(showId).addDate(date)
    }

    fun filter(shows: List<Show>, params: BaseFilterParams): List<Show>{
        val filter = createFilter(params)
        return shows.filter { show -> filter.apply(show) }
    }

    fun createFilter(params: BaseFilterParams): Filter<Show>{
        return Filter<Show>().apply {
            addFilterCondition(BandFilter(params.bandKeyword, showRepository))
            addFilterCondition(FacilityFilter(params.facilityKeyword, showRepository))
            addFilterCondition(WithFriends(params.withFriends, params.userId))
        }
    }
}

