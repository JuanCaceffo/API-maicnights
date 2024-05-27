package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.controller.ShowController.ShowRequest
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowExtraDataDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.ShowStatsDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toDTO
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.CreationError
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.NotFoundException
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class ShowService(
    @Autowired private var facilityRepository: FacilityRepository,
    @Autowired private var showRepository: ShowRepository,
    @Autowired private var bandRepository: BandRepository,
    @Autowired private var userService: UserService,
    @Autowired private var ticketService: TicketService,
    @Autowired private var commentService: CommentService,
    private val hydrousService: HydrousService,
    private val showDateService: ShowDateService,
    private val showDateRepository: ShowDateRepository
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
            dates,

        )
    }

    fun addPendingAttendee(id: String) {
        val show = findByIdOrError(id)
        show.addPendingAttendee()
        showRepository.save(show)
    }

    //TODO: ver que metodos necesitan hidratacion
    fun getKPIs(id: String): List<ShowStatsDTO> {
        val show = hydrousService.getHydrousShow(findByIdOrError(id))
        val pendingAttendees = show.pendingAttendees
        println("--------------- before show sales ----------------")
        val showCost = showDateService.showCost(id)
        println("showCost: ${showCost}")
        val showSales = ticketService.totalShowSales(id)
        println("showSales: ${showSales}")
        val showDatesIds = showDateRepository.findAllByShowId(id).map { it.id }
        val showDatesSoldOut = showDatesIds.filter { showDateId ->
            showDateService.isSoldOut(showDateId)
        }.size

        val grossIncome = showSales - showCost
        val rentability = (grossIncome / if (showCost != 0.0) showCost else 1.0) * 100

        return AdminStatBuilder()
            .statSales(showSales, Pivots(1000000.0, 2000000.0))
            .statPending(pendingAttendees, Pivots(0.0, 50.0), show)
            .statRentability(rentability, Pivots(0.0, 50.0))
            .statSoldOut(showDatesSoldOut, Pivots(50.0, 75.0), showDatesIds.size)
            .build()
    }

    fun isSoldOut(showId: String) = showDateService.isShowSoldOut(showId)

    @Transactional(Transactional.TxType.REQUIRED)
    fun newShowDate(showId: String, date: LocalDateTime) {
        validateNewShowDate(showId, date.toLocalDate())
        val show = hydrousService.getHydrousShow(findByIdOrError(showId))
        val showDate = ShowDate(show, date)
        showDateRepository.save(showDate)
        clearPendingAttendees(show)
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun clearPendingAttendees(show: Show) {
        showRepository.save(show.apply { clearPendingAttendees() })
    }

    private fun validateNewShowDate(showId: String, date: LocalDate) {
        if (date.isBefore(LocalDate.now())) throw BusinessException(CreationError.DATE_NOT_PASSED)
        if (showDateService.findAllByShowId(showId).any { it.date.toLocalDate() == date }) throw BusinessException(
            CreationError.DATE_ALREADY_EXISTS
        )
        if (!getKPIs(showId).all { it.newDateConditionMeet }) throw BusinessException(CreationError.NEW_DATE_INVALID_CONDITIONS)
    }

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

    fun addClick(id: String) {
        val show = findByIdOrError(id)
        show.addClick()
        showRepository.save(show)
    }

}
