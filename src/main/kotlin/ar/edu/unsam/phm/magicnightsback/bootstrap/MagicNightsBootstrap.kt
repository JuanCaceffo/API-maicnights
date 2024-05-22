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
    @Autowired private var showRepository: ShowRepository,
    @Autowired private var showDateRepository: ShowDateRepository,
    @Autowired private var bandRepository: BandRepository,
    @Autowired private var facilityRepository: FacilityRepository,
    @Autowired private var userRepository: UserRepository,
    @Autowired private var seatRepository: SeatRepository,
    @Autowired private var ticketRepository: TicketRepository,
    @Autowired private var commentRepository: CommentRepository,
) : InitializingBean {

    val initFacilities = mapOf(
        "river" to FacilityFactory().createFacility(FacilityFactoryTypes.BIGSTADIUM),
        "boca" to FacilityFactory().createFacility(FacilityFactoryTypes.MEDIUMSTADIUM),
        "colon" to FacilityFactory().createFacility(FacilityFactoryTypes.THEATER),
        "rex" to FacilityFactory().createFacility(FacilityFactoryTypes.BADTHEATER),
        "peque" to FacilityFactory().createFacility(FacilityFactoryTypes.SMALLTHEATER)
    )

    val initBands = mapOf(
        "vela" to BandFactory().createFacility(BandFactoryTypes.CHEAP),
        "pearl" to BandFactory().createFacility(BandFactoryTypes.NORMAL),
        "acdc" to BandFactory().createFacility(BandFactoryTypes.EXPENSIVE)
    )

    val initShows = mapOf(
        "cachen" to Show("Cachengued", initBands["vela"]!!, initFacilities["rex"]!!),
        "4you" to Show("4 You", initBands["pearl"]!!, initFacilities["boca"]!!),
        "demons" to Show("Demons of Hell Rise", initBands["acdc"]!!, initFacilities["river"]!!),
        "showcito" to Show("Unipersonal", initBands["acdc"]!!, initFacilities["peque"]!!)
    )

    val initUsers = mapOf(
        "pablo" to UserFactory().createUser(UserFactoryTypes.ADMIN),
        "sol" to UserFactory().createUser(UserFactoryTypes.NORMAL),
        "ana" to UserFactory().createUser(UserFactoryTypes.POOR),
        "carolina" to UserFactory().createUser(UserFactoryTypes.NOIMAGE),
        "jaun" to UserFactory().createUser(UserFactoryTypes.RICH),
    )

    val initShowDates = mutableListOf(
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.MINUS, initShows["cachen"]!!),
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.MINUS, initShows["4you"]!!),
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.MINUS, initShows["demons"]!!),
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.PLUS, initShows["4you"]!!),
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.PLUS, initShows["showcito"]!!),
    ).apply { addAll(ShowDateFactory().createShowDates(ShowDateFactoryTypes.PLUS, initShows["demons"]!!, 3)) }

    val initComments = listOf(
        Comment(
            initUsers["pablo"]!!,
            initShows["cachen"]!!,
            """La noche con La vela fue simplemente espectacular. Desde el primer acorde hasta
        |el último, la banda nos llevó en un viaje emocionante a través de su música icónica. Sebas irradiaba
        |energía en el escenario, y cada canción resonaba en lo más profundo de mi ser. La atmósfera estaba cargada
        |de emoción y camaradería, y el público se entregó por completo. 🎸🎶 #LaVela #ConciertoInolvidable""".trimMargin(),
            5.0
        ), Comment(
            initUsers["sol"]!!, initShows["cachen"]!!, "Que divertido estuvo, la pase re bien con mis amigos.", 4.5
        ), Comment(initUsers["ana"]!!, initShows["cachen"]!!, "Pésimo. El sonido anduvo mal todo el show", 1.5)
    )

    fun initTickets(): List<Ticket> {
        val showDates = showDateRepository.findAll().map { it }
        val seatsACDC = showDates[6].show.facility.seats

        return listOf(
            Ticketfactory().createTicket(
                TicketFactoryTypes.NORMAL,
                initUsers["sol"]!!,
                showDateRepository.save(showDates[6].apply { modifyOcupation(seatsACDC[0]) }),
                seatsACDC[0]
            ),
            Ticketfactory().createTicket(
                TicketFactoryTypes.NORMAL,
                initUsers["ana"]!!,
                showDateRepository.save(showDates[6].apply { modifyOcupation(seatsACDC[1]) }),
                seatsACDC[1]
            ),
            Ticketfactory().createTicket(
                TicketFactoryTypes.NORMAL,
                initUsers["carolina"]!!,
                showDateRepository.save(showDates[6].apply { modifyOcupation(seatsACDC[0]) }),
                seatsACDC[0]
            ),
        )
    }

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
        persist(initFacilities.values.toList())
        println("All facilities have been initialized")
        persist(initBands.values.toList())
        println("All bands have been initialized")
        persist(initShows.values.toList())
        println("All shows have been initialized")
        persist(initShowDates)
        println("All showDates have been initialized")
        setFriends()
        persist(initUsers.values.toList())
        println("All users have been initialized")
        persist(initTickets())
        println("All tickets have been initialized")
        persist(initComments)
        println("All comments have been initialized")
    }

    private fun <T> persist(objects: List<T>) {
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

    private fun <T> filterExistingObjects(objects: List<T>) = objects.filter {
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

            is Band -> {
                val band = bandRepository.findByNameEquals(it.name).getOrNull()
                band == null
            }

            is ShowDate -> {
                val showDate = showDateRepository.findByDateAndShowId(it.date, it.show.id).getOrNull()
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