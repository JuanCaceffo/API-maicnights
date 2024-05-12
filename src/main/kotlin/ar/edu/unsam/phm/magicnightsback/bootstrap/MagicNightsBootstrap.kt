package ar.edu.unsam.phm.magicnightsback.bootstrap

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.domain.factory.*
import ar.edu.unsam.phm.magicnightsback.repository.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class MagicNightsBootstrap(
    @Autowired
    private var showRepository: ShowRepository,

    @Autowired
    private var showDateRepository: ShowDateRepository,

    @Autowired
    private var bandRepository: BandRepository,

    @Autowired
    private var facilityRepository: FacilityRepository,

    @Autowired
    private var userRepository: UserRepository,

    @Autowired
    private var seatRepository: SeatRepository,
) : InitializingBean {


    //    @Autowired
//    lateinit var userRepository: UserRepository
//

//
//    @Autowired
//    lateinit var ticketRepository: TicketRepository

    val facilityCreator = FacilityFactory()
    val bandsCreator = BandFactory()
    val showDatesCreator = ShowDateFactory()
    val usersCreator = UserFactory()

    val initFacilities = mapOf(
        "river" to facilityCreator.createFacility(FacilityFactoryTypes.BIGSTADIUM),
        "boca" to facilityCreator.createFacility(FacilityFactoryTypes.MEDIUMSTADIUM),
        "colon" to facilityCreator.createFacility(FacilityFactoryTypes.THEATER),
        "rex" to facilityCreator.createFacility(FacilityFactoryTypes.BADTHEATER),
        "peque" to facilityCreator.createFacility(FacilityFactoryTypes.SMALLTHEATER)
    )

    val initBands = mapOf(
        "vela" to bandsCreator.createFacility(BandFactoryTypes.CHEAP),
        "pearl" to bandsCreator.createFacility(BandFactoryTypes.NORMAL),
        "acdc" to bandsCreator.createFacility(BandFactoryTypes.EXPENSIVE)
    )

    val initShows = mapOf(
        "cachen" to Show("Cachengued", initBands["vela"]!!, initFacilities["rex"]!!),
        "4you" to Show("4 You", initBands["pearl"]!!, initFacilities["boca"]!!),
        "demons" to Show("Demons of Hell Rise", initBands["acdc"]!!, initFacilities["river"]!!),
        "showcito" to Show("Unipersonal", initBands["acdc"]!!, initFacilities["peque"]!!)
    )

    val initUsers = mapOf(
        "pablo" to usersCreator.createUser(UserFactoryTypes.ADMIN),
        "sol" to usersCreator.createUser(UserFactoryTypes.NORMAL),
        "ana" to usersCreator.createUser(UserFactoryTypes.POOR),
        "carolina" to usersCreator.createUser(UserFactoryTypes.NOIMAGE),
    )

    val initShowDates = mutableListOf(
        showDatesCreator.createShowDate(ShowDateFactoryTypes.MINUS, initShows["cachen"]!!),
        showDatesCreator.createShowDate(ShowDateFactoryTypes.MINUS, initShows["4you"]!!),
        showDatesCreator.createShowDate(ShowDateFactoryTypes.MINUS, initShows["demons"]!!),
        showDatesCreator.createShowDate(ShowDateFactoryTypes.PLUS, initShows["4you"]!!),
        showDatesCreator.createShowDate(ShowDateFactoryTypes.PLUS, initShows["showcito"]!!),
    ).apply { addAll(showDatesCreator.createShowDates(ShowDateFactoryTypes.PLUS, initShows["demons"]!!, 3)) }


    override fun afterPropertiesSet() {
        persist(initFacilities.values.toSet())
        println("All facilities have been initialized")
        persist(initBands.values.toSet())
        println("All bands have been initialized")
        persist(initShows.values.toSet())
        println("All shows have been initialized")
        persist(initShowDates.toSet())
        println("All showDates have been initialized")
        persist(initUsers.values.toSet())
        println("All users have been initialized")
    }

    private fun <T> persist(objects: Set<T>) {
        filterExistingObjects(objects).forEach {
            when (it) {
                is Seat -> seatRepository.save(it)
                is User -> userRepository.save(it)
                is Facility -> facilityRepository.save(it)
                is Show -> showRepository.save(it)
                is Band -> bandRepository.save(it)
                is ShowDate -> showDateRepository.save(it)
                //        is Ticket -> ticketRepository.save(it)
            }
        }
    }

    private fun <T> filterExistingObjects(objects: Set<T>) =
        objects.filter {
            when (it) {
                is Seat -> {
                    val seat = seatRepository.findSeatByTypeIs(it.type)
                    seat == null
                }

                is User -> {
                    val user = userRepository.findByUsername(it.username).getOrNull()
                    user == null
                }

                is Facility -> {
                    val facility = facilityRepository.findByNameEquals(it.name).getOrNull()
                    facility == null
                }

                is Show -> {
                    val facility = showRepository.findByNameEquals(it.name).getOrNull()
                    facility == null
                }
//
                is Band -> {
                    val band = bandRepository.findByNameEquals(it.name).getOrNull()
                    band == null
                }
//
                is ShowDate -> {
                    val showDate = showDateRepository.findByDateAndShowId(it.date, it.show.id).getOrNull()
                    showDate == null
                }
//
//                is Ticket -> {
//                    true
//                }

                else -> throw IllegalArgumentException("Unsupported Class: ${it!!::class.simpleName}")
            }
        }
}