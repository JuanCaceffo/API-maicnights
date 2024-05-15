package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.NotFoundException
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ShowService(

//    private lateinit var userRepository: UserRepository
//
//    @Autowired
//    private lateinit var facilityRepository: FacilityRepository
//
    @Autowired
    private var showRepository: ShowRepository
//
//    @Autowired
//    lateinit var userService: UserService
) {
    @Transactional(Transactional.TxType.NEVER)
    fun findById(id: Long): Show? =
        showRepository.findById(id).getOrNull()

    @Transactional(Transactional.TxType.NEVER)
    fun findByIdOrError(id: Long): Show =
        findById(id) ?: throw NotFoundException(FindError.NOT_FOUND(id, Show::class.toString()))

    @Transactional(Transactional.TxType.NEVER)
    fun findAll(/*params: ShowRequest*/): List<Show> {
        val shows = showRepository.findAll()
        //val filteredShows = filter(shows, params)

        return shows.map { it }
    }
}
//    @Autowired
//    private lateinit var userRepository: UserRepository
//
//    @Autowired
//    private lateinit var facilityRepository: FacilityRepository
//
//    @Autowired
//    lateinit var showRepository: ShowRepository
//
//    @Autowired
//    lateinit var userService: UserService
//

//
//    @Transactional(Transactional.TxType.NEVER)
//    fun findById(showId: Long): Show {
//        //  return show.toShowDTO(showService.getAPossibleUserById(userId),comments)
//        return validateOptionalIsNotNull(showRepository.findById(showId))
//    }
//
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun createShowDate(showId: Long, userId: Long,  body: ShowDateDTO): ShowDate {
////        userService.validateAdminStatus(userId)
////        val show = validateOptionalIsNotNull(showRepository.findById(showId))
////        return show.addDate(body.date)
////    }
//
//    @Transactional(Transactional.TxType.NEVER)
//    fun findByIdAdmin(showId: Long, userId: Long): Show {
//        userService.validateAdminStatus(userId)
//        return findById(showId)
//    }
//
//    @Transactional(Transactional.TxType.NEVER)
//    fun findByName(name: String): Show = validateOptionalIsNotNull(showRepository.findByNameEquals(name))
//
////    fun getBusyFacilities(): List<Facility> {
////        val facilitiesID = showRepository.busyFacilities().map { it }
////        return facilityRepository.findAll().filter { it.id in facilitiesID }
////    }
//
////    private fun filter(shows: Iterable<Show>, params: ShowRequest): List<Show> {
////        val filter = createFilter(params)
////        return shows.filter { show -> filter.apply(show) }
////    }
//
////    private fun createFilter(params: ShowRequest): Filter<Show> {
////        val user = userRepository.findById(params.userId)
////        return Filter<Show>().apply {
////            addFilterCondition(BandFilter(params.bandKeyword))
////            addFilterCondition(FacilityFilter(params.facilityKeyword))
////            if (user.isPresent) addFilterCondition(WithFriends(params.withFriends, user.get()))
////        }
////    }
////
////    fun getAPossibleUserById(userId: Long) = if (userId > -1) userRepository.getById(userId) else null
//}
//
