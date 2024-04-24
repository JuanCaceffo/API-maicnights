package ar.edu.unsam.phm.magicnightsback.controller


import ar.edu.unsam.phm.magicnightsback.dto.ShowDateDTO
import ar.edu.unsam.phm.magicnightsback.factory.ShowTypes
import ar.edu.unsam.phm.magicnightsback.factory.TestFactory
import ar.edu.unsam.phm.magicnightsback.factory.UserTypes
import ar.edu.unsam.phm.magicnightsback.repository.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.mockk.every
import jakarta.transaction.Transactional
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Show Controller Tests")
@Transactional
class ShowControllerTest(@Autowired val mockMvc: MockMvc) {

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var facilityRepository: FacilityRepository

    @Autowired
    lateinit var showRepository: ShowRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var bandRepository: BandRepository

    val mapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

    val factory = TestFactory()

    @Test
    fun `llamada al metodo post para crear una funcion por un usuario que no es admin falla`() {
        val user = userRepository.save(factory.createUser(UserTypes.NORMAL))
        val newShowDate = ShowDateDTO()

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/admin_dashboard/show/new-show-date")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", user.id.toString())
                .content(mapper.writeValueAsString(newShowDate))
        )
            .andExpect(status().is4xxClientError)
            .andExpect(status().isUnauthorized())
    }

    @Test
    fun `llamada al metodo post para crear una funcion por un usuario que es admin falla debido a que la fecha es anterior a la actual`() {
        factory.createMockedDate()

        val admin = userRepository.save(factory.createUser(UserTypes.ADMIN))
        val show = showRepository.save(factory.createShow(ShowTypes.BIGSHOW))
        val newShowDate = ShowDateDTO(showId = show.id, date = LocalDateTime.now().minusDays(1))

        mockMvc.perform(
            post("/api/admin_dashboard/show/new-show-date")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", admin.id.toString())
                .content(mapper.writeValueAsString(newShowDate))
        )
            .andExpect(status().is4xxClientError)
            .andExpect(status().reason(containsString("posterior")))
    }

    @Test
    fun `llamada al metodo post para crear una funcion por un usuario que es admin falla debido a que no se cumplen las condiciones del negocio`() {
        factory.createMockedDate()

        val admin = userRepository.save(factory.createUser(UserTypes.ADMIN))
        val show = showRepository.save(factory.createShow(ShowTypes.BIGSHOW))

        val newShowDate = ShowDateDTO(showId = show.id, date = LocalDateTime.now().plusDays(1))

        mockMvc.perform(
            post("/api/admin_dashboard/show/new-show-date")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", admin.id.toString())
                .content(mapper.writeValueAsString(newShowDate))
        )
            .andExpect(status().is4xxClientError)
            .andExpect(status().reason(containsString("condiciones")))
    }

    @Test
    fun `llamada al metodo post para crear una funcion por un usuario que es admin falla debido a que ya existe la fecha`() {
        factory.createMockedDate()

        val newDate = LocalDateTime.now().plusDays(1)

        val admin = userRepository.save(factory.createUser(UserTypes.ADMIN))
        val show = showRepository.save(factory.createShow(ShowTypes.BIGSHOW))

        val newShowDate = ShowDateDTO(showId = show.id, date = newDate)

        show.initialDates(listOf(newDate))

        mockMvc.perform(
            post("/api/admin_dashboard/show/new-show-date")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", admin.id.toString())
                .content(mapper.writeValueAsString(newShowDate))
        )
            .andExpect(status().is4xxClientError)
            .andExpect(status().reason(containsString("ya existe")))
    }
}