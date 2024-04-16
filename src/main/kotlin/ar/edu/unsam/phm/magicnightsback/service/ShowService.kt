package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShowService {
    //    @Autowired
//    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var showRepository: ShowRepository


    //
//    fun getAll(params: BaseFilterParams): Iterable<Show> {
//        val shows = showRepository.getAll()
//        return filter(shows, params)
//    }
//
//    fun createShowDate(showId: Long, userId: Long, date: LocalDateTime) {
//        val show = getById(showId)
//        if (!AdminStats.newDateAvailable(show)) {
//            throw BusinessException(showDateError.NEW_SHOW_INVALID_CONDITIONS)
//        }
//        showRepository.getById(showId).addDate(date)
//    }
//
//    fun filter(shows: List<Show>, params: BaseFilterParams): List<Show>{
//        val filter = createFilter(params)
//        return shows.filter { show -> filter.apply(show) }
//    }
//
//    fun createFilter(params: BaseFilterParams): Filter<Show>{
//        return Filter<Show>().apply {
//            addFilterCondition(BandFilter(params.bandKeyword))
//            addFilterCondition(FacilityFilter(params.facilityKeyword))
//            addFilterCondition(WithFriends(params.withFriends, params.userId))
//        }
//    }
    fun findById(id: Long): Show = validateOptionalIsNotNull(showRepository.findById(id))

    fun findByName(name: String): Show = validateOptionalIsNotNull(showRepository.findByName(name))
//
//    fun getAPossibleUserById(userId: Long) = if (userId > -1) userRepository.getById(userId) else null
}

