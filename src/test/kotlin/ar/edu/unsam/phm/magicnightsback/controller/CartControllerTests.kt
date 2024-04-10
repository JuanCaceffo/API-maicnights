package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.boostrap.ShowBoostrap
import ar.edu.unsam.phm.magicnightsback.boostrap.UserBoostrap
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.dto.TicketCreateDTO
import ar.edu.unsam.phm.magicnightsback.dto.toTicketDTO
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.uqbar.geodds.Point
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Controller de carrito")
class CartControllerTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val userRepository: UserRepository,
    @Autowired val cartRepository: CartRepository,
    @Autowired val showRepository: ShowRepository,
    @Autowired val userBoostrap: UserBoostrap,
    @Autowired val showBoostrap: ShowBoostrap
) {
    val mapper = ObjectMapper()

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
    val generalDateTime: LocalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231", formatter)

    val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL, 500)
    val pullman = SeatType(TheaterSeatType.PULLMAN, 300)


    @BeforeAll
    fun init() {
        mapper.registerModules(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
    @BeforeEach
    fun start() {
        userRepository.clear()
        showRepository.clear()
        cartRepository.clear()
        userRepository.create(
            User(
                name = "Juan",
                surname = "Caccefo",
                username = "juanceto01",
                dni = 1,
                birthday = LocalDate.of(2003, 2, 1),
                password = "asdf",
                profileImage = ""
            )
        )
        cartRepository.create(Cart(userRepository.getById(0)))
        showRepository.create(
            Show(
                "Cachenged!!",
                Band(
                    "La Vela Puerca",
                    10000.0
                ),
                Facility(
                    name = "Gran Rex",
                    location = Point(-34.60356, -58.38013),
                    seatStrategy = TheaterStrategy()
                ).apply {
                    addSeatType(pullman)
                    addSeatType(lowerLevel)
                }
            ).apply {
                repeat(5) { addDate(generalDateTime.plusDays(11 + it.toLong())) }
            }
        )
    }

    @AfterAll
    fun end() {
        userBoostrap.afterPropertiesSet()
        showBoostrap.afterPropertiesSet()
    }

    fun setUserWithTicket(): Ticket {
        val cart = cartRepository.getCardFor(0)
        val show = showRepository.getById(0)
        val ticket =
            Ticket(show, show.dates.first(), TheaterSeatType.PULLMAN, show.ticketPrice(TheaterSeatType.PULLMAN))
        cart.reservedTickets.add(ticket)
        return ticket
    }

    @Test
    fun `Dado un endpoint para obtener los tickets del carrito de un usuario con un ticket reservado funciona bien`() {
        val user = userRepository.getById(0)
        val ticket = setUserWithTicket()
        //assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/cart/user/0/reserved-tickets")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(
                    mapper.writeValueAsString(
                        mutableListOf(
                            ticket.toTicketDTO(
                                user,
                                8110.0,
                                ticket.quantity
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun `Dado un endpoint para obtener los tickets del carrito de un mismo show con funciones diferentes de un usuario funciona bien`() {
        //arrange
        val user = userRepository.getById(0)
        val cart = cartRepository.getCardFor(0)
        val show = showRepository.getById(0)
        val ticket =
            Ticket(show, show.dates.first(), TheaterSeatType.LOWERLEVEL, show.ticketPrice(TheaterSeatType.LOWERLEVEL))
        val ticketDifferentDate =
            Ticket(show, show.dates.last(), TheaterSeatType.LOWERLEVEL, show.ticketPrice(TheaterSeatType.LOWERLEVEL))
        //active
        cart.reservedTickets.add(ticket)
        cart.reservedTickets.add(ticketDifferentDate)
        //assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/cart/user/0/reserved-tickets")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(
                    mapper.writeValueAsString(
                        mutableListOf(
                            ticket.toTicketDTO(
                                user,
                                12110.0,
                                1
                            ),
                            ticketDifferentDate.toTicketDTO(
                                user,
                                12110.0,
                                1
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun `Dado un endpoint para reservar tickets para un usuario al reservar un ticket funciona bien`() {
        val show = showRepository.getById(0)
        val data = TicketCreateDTO(0, 0, show.ticketPrice(TheaterSeatType.PULLMAN), AllSetTypeNames.PULLMAN, 1)
        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/cart/user/0/reserve-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data))
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        )
    }

    @Test
    fun `Al llamar al endpoint apra reservar un cantidad tickets no permitida para un usario falla`() {
        val show = showRepository.getById(0)
        val data = TicketCreateDTO(0, 0, show.ticketPrice(TheaterSeatType.PULLMAN), AllSetTypeNames.PULLMAN, 1000)
        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/cart/user/0/reserve-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data))
        ).andExpect(
            MockMvcResultMatchers.status().is4xxClientError
        )
    }

    @Test
    fun `Al llamar al endpoint para reservar un ticket para una ubicacion donde no hay disponibilidad falla`() {
        val show = showRepository.getById(0)
        val data = TicketCreateDTO(0, 0, show.ticketPrice(TheaterSeatType.PULLMAN), AllSetTypeNames.UPPERLEVEL, 1000)
        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/cart/user/0/reserve-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data))
        ).andExpect(
            MockMvcResultMatchers.status().is4xxClientError
        )
    }

    @Test
    fun `Al llamar un endpoint para eliminar todos los tickets reservados para un usaurio funciona bien`() {
        setUserWithTicket()

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/cart/user/0/remove-reserved-tickets")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        )
    }

    @Test
    fun `Al ejecutar el endpoint para comprar todos los tiquetes reservados de un usuario con credio suficiente sale bien`() {
        setUserWithTicket()
        val user = userRepository.getById(0)

        user.addCredit(100000.0)

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/cart/user/0/purchase-reserved-tickets")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `Al ejecutar el endpoint para comprar todos los tiquetes reservados de un usuario con crediotos insuficientes lanza una exepcion`() {
        setUserWithTicket()

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/cart/user/0/purchase-reserved-tickets")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}