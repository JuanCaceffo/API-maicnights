package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.controller.ShowController.ShowRequest
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowExtraDataDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.StatsDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toDTO
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.NotFoundException
import ar.edu.unsam.phm.magicnightsback.repository.*
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ShowService(
    @Autowired private var facilityRepository: FacilityRepository,

    @Autowired private var showRepository: ShowRepository,

    @Autowired private var bandRepository: BandRepository,

    @Autowired private var ticketRepository: TicketRepository,

    @Autowired private var showDateRepository: ShowDateRepository,

    @Autowired private var userService: UserService,

    @Autowired private var ticketService: TicketService,

    @Autowired private var commentService: CommentService,

    private val hydrousService: HydrousService,
    private val showDateService: ShowDateService
) {
    fun findById(id: String): Show? = showRepository.findById(id).getOrNull()

    fun findByIdOrError(id: String): Show =
        findById(id) ?: throw NotFoundException(FindError.NOT_FOUND(id, Show::class.toString()))


    fun findAll(params: ShowRequest): List<Show> {
        val shows = showRepository.findAll().map { hydrousService.getHydrousShow(it) }
        val filteredShows = filter(shows, params)
        return filteredShows
    }

    @Transactional(Transactional.TxType.NEVER)
    fun getShowExtraData(showId: String, userId: Long): ShowExtraDataDTO {
        val dates = showDateRepository.findAllByShowId(showId).map { it.toDTO() }
        val commentsStats = commentService.getCommentStadisticsOfShow(showId)
        val totalFriendsAttending = ticketService.countFriendsAttendingToShow(showId, userId)
        val images = ticketService.getTopFriendsImages(showId, userId)

        return ShowExtraDataDTO(
            images,
            totalFriendsAttending,
            commentsStats.rating,
            commentsStats.totalComments,
            dates
        )
    }

    fun addPendingAttendee(id: String) {
        val show = findByIdOrError(id)
        show.addPendingAttendee()
        showRepository.save(show)
    }
//
//    fun getKPIs(id: String): StatsDTO {
//        val show = findByIdOrError(id)
//        //TODO: ver si conviene cambiar a query directo y que desventaja tiene (ventaja ya conocida)
//        val showCost = showDateRepository.findAllByShowId(id).sumOf { hydrousService.getHydrousShow(it.show).baseCost() } ?: 1.0
//        val showSales = ticketRepository.totalShowSales(id).getOrNull() ?: 0.0
//
//        val showDatesSoldOut = showDateRepository.showDateIdsByShowId(id).map{it}.filter { showDateId ->
//            showDateService.isSoldOut(showDateId)
//        }.size
//
//        return StatsDTO(
//            ticketRepository.totalShowSales(id).getOrNull() ?: 0.0,
//            show.pendingAttendees,
//            (showSales / if (showCost == 0.0) 1.0 else showCost) * 100,
//            showDatesSoldOut
//        )
//    }

    private fun filter(shows: Iterable<Show>, params: ShowRequest): List<Show> {
        val filter = createFilter(params)
        return shows.filter { show -> filter.apply(show) }
    }

    private fun createFilter(params: ShowRequest): Filter<Show> {
        val bandIds = bandRepository.findByNameIsContainingIgnoreCase(params.bandKeyword).map { it.id }
        val facilityIds = facilityRepository.findByNameIsContainingIgnoreCase(params.facilityKeyword).map { it.id }
        val user = userService.findById(params.userId)

        return Filter<Show>().apply {
            addFilterCondition(BandFilter(bandIds))
            addFilterCondition(FacilityFilter(facilityIds))
            if (user != null && params.withFriends) addFilterCondition(WithFriends(user.id, ticketService))
        }
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
//    fun findById(showId: String): Show {
//        //  return show.toShowDTO(showService.getAPossibleUserById(userId),comments)
//        return validateOptionalIsNotNull(showRepository.findById(showId))
//    }
//
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun createShowDate(showId: String, userId: Long,  body: ShowDateDTO): ShowDate {
////        userService.validateAdminStatus(userId)
////        val show = validateOptionalIsNotNull(showRepository.findById(showId))
////        return show.addDate(body.date)
////    }
//
//    
//    fun findByIdAdmin(showId: Long, userId: Long): Show {
//        userService.validateAdminStatus(userId)
//        return findById(showId)
//    }
//
//    
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
