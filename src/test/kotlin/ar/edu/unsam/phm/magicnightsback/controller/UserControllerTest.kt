package ar.edu.unsam.phm.magicnightsback.controller;


import ar.edu.unsam.phm.magicnightsback.boostrap.ShowBoostrap
import ar.edu.unsam.phm.magicnightsback.boostrap.UserBoostrap
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.dto.TicketCreateDTO
import ar.edu.unsam.phm.magicnightsback.dto.toCartDTO
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
@DisplayName("Controller de usuario")
class UserControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val userRepository: UserRepository,
    @Autowired val showRepository: ShowRepository,
    @Autowired val userBoostrap: UserBoostrap,
    @Autowired val showBoostrap: ShowBoostrap
) {
    val mapper = ObjectMapper()

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
    val generalDateTime: LocalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231", formatter)

    val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL, 500)
    val pullman = SeatType(TheaterSeatType.PULLMAN, 300)

    val show = Show(
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
    val ticket = Ticket(show, show.dates.first(), TheaterSeatType.PULLMAN, show.ticketPrice(TheaterSeatType.PULLMAN))

    @BeforeAll
    fun init() {
        mapper.registerModules(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    @BeforeEach
    fun start() {
        userRepository.clear()
        showRepository.clear()
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
        showRepository.create(show)
    }

    @AfterAll
    fun end() {
        userBoostrap.afterPropertiesSet()
        showBoostrap.afterPropertiesSet()
    }

    fun userWithPenddingTicket(){
        val user = userRepository.getById(0)
        user.pendingTickets.add(ticket)
    }

    @Test
    fun `Dado un endpoint para obtener los tickets del carrito de un usuario con un ticket reservado funciona bien`() {
        userWithPenddingTicket()
        //assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/user-profile/0/pending-tickets")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(
                    mapper.writeValueAsString(
                        mutableListOf(
                            ticket.toCartDTO(
                                0,
                                listOf(LocalDateTime.parse("2024-03-30T16:57:04.074472231").plusDays(11)),
                                8110.0,
                                1
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun `Dado un endpoint para obtener los tickets del carrito de un mismo show con funciones diferentes de un usuario funciona bien`() {
        userWithPenddingTicket()
        val user = userRepository.getById(0)
        val ticketDifferentDate = Ticket(show, show.dates.last(), TheaterSeatType.LOWERLEVEL, show.ticketPrice(TheaterSeatType.LOWERLEVEL))

        user.pendingTickets.add(ticketDifferentDate)

        //assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/user-profile/0/pending-tickets")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(
                    mapper.writeValueAsString(
                        mutableListOf(
                            ticket.toCartDTO(
                                0,
                                listOf(generalDateTime.plusDays(11), generalDateTime.plusDays(11 + 4.toLong())),
                                24220.0,
                                2
                            )
                        )
                    )
                )
            )
    }

    @Test
    fun `Un usuario reserva 1 ticket para un show de forma exitosa`() {
        val show = showRepository.getById(0)
        val data = TicketCreateDTO(0, 0, show.ticketPrice(TheaterSeatType.PULLMAN), AllSetTypeNames.PULLMAN, 1)

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/user-profile/0/reserve-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data))
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        )
    }

    @Test
    fun `Un usuario reserva una cantidad no permitida de tickets para un show y falla`() {
        val show = showRepository.getById(0)
        val data = TicketCreateDTO(0, 0, show.ticketPrice(TheaterSeatType.PULLMAN), AllSetTypeNames.PULLMAN, 1000)

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/user-profile/0/reserve-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data))
        ).andExpect(
            MockMvcResultMatchers.status().is4xxClientError
        )
    }

    @Test
    fun `Un usuario reserva un ticket con un asiento no disponible para ese show y falla`() {
        val show = showRepository.getById(0)
        val data = TicketCreateDTO(0, 0, show.ticketPrice(TheaterSeatType.PULLMAN), AllSetTypeNames.UPPERLEVEL, 1000)

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/user-profile/0/reserve-tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(data))
        ).andExpect(
            MockMvcResultMatchers.status().is4xxClientError
        )
    }

    @Test
    fun `Un usaurio puede eliminar todos los tickets que tiene reservados`() {
        userWithPenddingTicket()

        mockMvc.perform(
            MockMvcRequestBuilders
                .put("/user-profile/0/remove-reserve-tickets")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        )
    }
}
