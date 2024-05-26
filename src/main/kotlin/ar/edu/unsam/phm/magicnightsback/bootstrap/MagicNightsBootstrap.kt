package ar.edu.unsam.phm.magicnightsback.bootstrap

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.domain.factory.*
import ar.edu.unsam.phm.magicnightsback.repository.*
import ar.edu.unsam.phm.magicnightsback.service.HydrousService
import ar.edu.unsam.phm.magicnightsback.service.ShowDateService
import ar.edu.unsam.phm.magicnightsback.service.ShowFieldsToHydrous
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.*
import java.util.Date
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
    private val showDateService: ShowDateService,
    private val hydrousService: HydrousService
    ) : InitializingBean {

    val initFacilities = mapOf(
        "river" to FacilityFactory().createFacility(FacilityFactoryTypes.BIGSTADIUM),
        "boca" to FacilityFactory().createFacility(FacilityFactoryTypes.MEDIUMSTADIUM),
        "colon" to FacilityFactory().createFacility(FacilityFactoryTypes.THEATER),
        "rex" to FacilityFactory().createFacility(FacilityFactoryTypes.BADTHEATER),
        "peque" to FacilityFactory().createFacility(FacilityFactoryTypes.SMALLTHEATER)
    )

    val initBands = mapOf(
        "vela" to BandFactory().createBand(BandFactoryTypes.CHEAP),
        "pearl" to BandFactory().createBand(BandFactoryTypes.NORMAL),
        "acdc" to BandFactory().createBand(BandFactoryTypes.EXPENSIVE)
    )

    fun initShows() = mapOf(
        "cachen" to Show("Cachengued", bandRepository.findByName("La Vela Puerca").get().id, facilityRepository.findByName("Gran Rex").get().id).apply {
            band = initBands["vela"]!!
            facility = initFacilities["rex"]!!
            initBaseCost()
            },
        "4you" to Show("4 You", bandRepository.findByName("Pearl Jam").get().id, facilityRepository.findByName("La Bombonera").get().id).apply {
            band = initBands["pearl"]!!
            facility = initFacilities["boca"]!!
            initBaseCost()
        },
        "demons" to Show("Demons of Hell Rise", bandRepository.findByName("AC/DC").get().id, facilityRepository.findByName("River Plate").get().id).apply {
            band = initBands["acdc"]!!
            facility = initFacilities["river"]!!
            initBaseCost()
        },
        "showcito" to Show("Unipersonal", bandRepository.findByName("AC/DC").get().id, facilityRepository.findByName("Teatro Peque").get().id).apply {
            band = initBands["acdc"]!!
            facility = initFacilities["peque"]!!
            initBaseCost()
        }
    )

    val initUsers = mapOf(
        "pablo" to UserFactory().createUser(UserFactoryTypes.ADMIN),
        "sol" to UserFactory().createUser(UserFactoryTypes.NORMAL),
        "ana" to UserFactory().createUser(UserFactoryTypes.POOR),
        "carolina" to UserFactory().createUser(UserFactoryTypes.NOIMAGE),
        "juan" to UserFactory().createUser(UserFactoryTypes.RICH),
    )

    fun initShowDates() = mutableListOf(
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.MINUS, hydrousService.getHydrousShow(showRepository.findByName("Cachengued").get(), ShowFieldsToHydrous.FACILITY)),
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.MINUS, hydrousService.getHydrousShow(showRepository.findByName("4 You").get(), ShowFieldsToHydrous.FACILITY)),
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.MINUS, hydrousService.getHydrousShow(showRepository.findByName("Demons of Hell Rise").get(), ShowFieldsToHydrous.FACILITY)),
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.PLUS, hydrousService.getHydrousShow(showRepository.findByName("4 You").get(), ShowFieldsToHydrous.FACILITY)),
        ShowDateFactory().createShowDate(ShowDateFactoryTypes.PLUS, hydrousService.getHydrousShow(showRepository.findByName("Unipersonal").get(), ShowFieldsToHydrous.FACILITY)),
    ).apply { addAll(ShowDateFactory().createShowDates(ShowDateFactoryTypes.PLUS, hydrousService.getHydrousShow(showRepository.findByName("Demons of Hell Rise").get(), ShowFieldsToHydrous.FACILITY), 3)) }

    fun initComments() = listOf(
        Comment(initUsers["pablo"]!!, showRepository.findByName("Cachengued").get().id, """La noche con La vela fue simplemente espectacular. Desde el primer acorde hasta
        |el último, la banda nos llevó en un viaje emocionante a través de su música icónica. Sebas irradiaba
        |energía en el escenario, y cada canción resonaba en lo más profundo de mi ser. La atmósfera estaba cargada
        |de emoción y camaradería, y el público se entregó por completo. 🎸🎶 #LaVela #ConciertoInolvidable""".trimMargin(), 5.0),
        Comment(initUsers["sol"]!!, showRepository.findByName("Cachengued").get().id, "Que divertido estuvo, la pase re bien con mis amigos.", 4.5),
        Comment(initUsers["ana"]!!, showRepository.findByName("Cachengued").get().id, "Pésimo. El sonido anduvo mal todo el show", 1.5)
    )

    fun initTickets(): List<Ticket> {
        val showDates = showDateService.getAllHydrousShowDates()
        val seatsACDC = showDates[6].show.facility.seats
        val seatsACDC2 = showDates[2].show.facility.seats

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
            Ticketfactory().createTicket(
                TicketFactoryTypes.EXPENSIVE,
                initUsers["sol"]!!,
                showDateRepository.save(showDates[2].apply { modifyOcupation(seatsACDC2[2]) }),
                seatsACDC2[2]
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
        persist(initShows().values.toList())
        println("All shows have been initialized")
        persist(initShowDates())
        println("All showDates have been initialized")
        setFriends()
        persist(initUsers.values.toList())
        println("All users have been initialized")
        persist(initTickets())
        println("All tickets have been initialized")
        persist(initComments())
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

    private fun <T> filterExistingObjects(objects: List<T>) =
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
                    val startOfDay = LocalDateTime.of(it.date.toLocalDate(), LocalTime.MIN)
                    val endOfDay = LocalDateTime.of(it.date.toLocalDate(), LocalTime.MAX)

                    println("caca")
                    val showDate = showDateRepository.getByDateAndShowId(startOfDay,endOfDay, it.show.id).getOrNull()
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