package ar.edu.unsam.phm.magicnightsback.bootstrap

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.domain.factory.*
import ar.edu.unsam.phm.magicnightsback.repository.*
import ar.edu.unsam.phm.magicnightsback.service.ShowDateService
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date
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

    @Autowired
    private var ticketRepository: TicketRepository,

    @Autowired
    private var commentRepository: CommentRepository,

    private val showDateService: ShowDateService,
) : InitializingBean {

    val facilityCreator = FacilityFactory()
    val bandsCreator = BandFactory()
    val showDatesCreator = ShowDateFactory()
    val usersCreator = UserFactory()
    val ticketCreator = Ticketfactory()

    val initFacilities = mapOf(
        "river" to facilityCreator.createFacility(FacilityFactoryTypes.BIGSTADIUM),
        "boca" to facilityCreator.createFacility(FacilityFactoryTypes.MEDIUMSTADIUM),
        "colon" to facilityCreator.createFacility(FacilityFactoryTypes.THEATER),
        "rex" to facilityCreator.createFacility(FacilityFactoryTypes.BADTHEATER),
        "peque" to facilityCreator.createFacility(FacilityFactoryTypes.SMALLTHEATER)
    )

    val initBands = mapOf(
        "vela" to bandsCreator.createBand(BandFactoryTypes.CHEAP),
        "pearl" to bandsCreator.createBand(BandFactoryTypes.NORMAL),
        "acdc" to bandsCreator.createBand(BandFactoryTypes.EXPENSIVE)
    )

    fun initShows() = mapOf(
        "cachen" to Show("Cachengued", bandRepository.findByName("La Vela Puerca").get().id, facilityRepository.findByName("Gran Rex").get().id),
        "4you" to Show("4 You", bandRepository.findByName("Pearl Jam").get().id, facilityRepository.findByName("La Bombonera").get().id),
        "demons" to Show("Demons of Hell Rise", bandRepository.findByName("AC/DC").get().id, facilityRepository.findByName("River Plate").get().id),
        "showcito" to Show("Unipersonal", bandRepository.findByName("AC/DC").get().id, facilityRepository.findByName("Teatro Peque").get().id)
    )

    val initUsers = mapOf(
        "pablo" to usersCreator.createUser(UserFactoryTypes.ADMIN),
        "sol" to usersCreator.createUser(UserFactoryTypes.NORMAL),
        "ana" to usersCreator.createUser(UserFactoryTypes.POOR),
        "carolina" to usersCreator.createUser(UserFactoryTypes.NOIMAGE),
    )

    fun initShowDates() = mutableListOf(
        showDatesCreator.createShowDate(ShowDateFactoryTypes.MINUS, showRepository.findByName("Cachengued").get()),
//        showDatesCreator.createShowDate(ShowDateFactoryTypes.MINUS, showRepository.findByName("4 You").get()),
//        showDatesCreator.createShowDate(ShowDateFactoryTypes.MINUS, showRepository.findByName("Demons of Hell Rise").get()),
//        showDatesCreator.createShowDate(ShowDateFactoryTypes.PLUS, showRepository.findByName("4 You").get()),
//        showDatesCreator.createShowDate(ShowDateFactoryTypes.PLUS, showRepository.findByName("Unipersonal").get()),
    )/*.apply { addAll(showDatesCreator.createShowDates(ShowDateFactoryTypes.PLUS, showRepository.findByName("Demons of Hell Rise").get(), 3)) }*/

    fun initComments() = setOf(
        Comment(initUsers["pablo"]!!, showRepository.findByName("Cachengued").get(), """La noche con La vela fue simplemente espectacular. Desde el primer acorde hasta
        |el último, la banda nos llevó en un viaje emocionante a través de su música icónica. Sebas irradiaba
        |energía en el escenario, y cada canción resonaba en lo más profundo de mi ser. La atmósfera estaba cargada
        |de emoción y camaradería, y el público se entregó por completo. 🎸🎶 #LaVela #ConciertoInolvidable""".trimMargin(), 5.0),
        Comment(initUsers["sol"]!!, showRepository.findByName("Cachengued").get(), "Que divertido estuvo, la pase re bien con mis amigos.", 4.5),
        Comment(initUsers["ana"]!!, showRepository.findByName("Cachengued").get(), "Pésimo. El sonido anduvo mal todo el show", 1.5)
    )

    fun initTickets(): Set<Ticket> {
        val showDates = showDateService.getAllHydrousShowDates()

        return setOf(
        ticketCreator.createTicket(TicketFactoryTypes.NORMAL, initUsers["sol"]!!, showDates[6], showDates[6].show.facility.seats.toList()[0]),
        ticketCreator.createTicket(TicketFactoryTypes.NORMAL, initUsers["ana"]!!, showDates[6], showDates[6].show.facility.seats.toList()[1]),
        ticketCreator.createTicket(TicketFactoryTypes.NORMAL, initUsers["carolina"]!!, showDates[6], showDates[6].show.facility.seats.toList()[0]),
    )}

    fun setFriends() {
        initUsers["pablo"]?.apply {
            initUsers["sol"]?.let { addFriend(it) }
            initUsers["ana"]?.let { addFriend(it) }
            initUsers["carolina"]?.let { addFriend(it) }
        }

        initUsers["sol"]?.apply {
            initUsers["pablo"]?.let { addFriend(it) }
        }
    }

    override fun afterPropertiesSet() {
        persist(initFacilities.values.toSet())
        println("All facilities have been initialized")
        persist(initBands.values.toSet())
        println("All bands have been initialized")
        persist(initShows().values.toSet())
        println("All shows have been initialized")
        persist(initShowDates().toSet())
        println("All showDates have been initialized")
        setFriends()
        persist(initUsers.values.toSet())
        println("All users have been initialized")
//        persist(initTickets())
        println("All tickets have been initialized")
        persist(initComments())
        println("All comments have been initialized")
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
                is Ticket -> ticketRepository.save(it)
                is Comment -> commentRepository.save(it)
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
                    val facility = facilityRepository.findByName(it.name).getOrNull()
                    facility == null
                }

                is Show -> {
                    val show = showRepository.findByName(it.name).getOrNull() ////
                    show == null
                }

                is Band -> {
                    val band = bandRepository.findByName(it.name).getOrNull()
                    band == null
                }

                is ShowDate -> {
                    val showDate = showDateRepository.getByDateAndShowId(it.date.plusHours(3L).toLocalDate(), it.show.id).getOrNull()
                    showDate == null
                }


                is Ticket -> {
                    true
                }

                is Comment -> {
                    true
                }

                else -> throw IllegalArgumentException("Unsupported Class: ${it!!::class.simpleName}")


            }
        }
}