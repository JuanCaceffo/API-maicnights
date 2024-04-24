package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.repository.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Show Controller Tests")
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

    val mapper = ObjectMapper()

    @Test
    fun `llamada al metodo post para crear una funcion por un usuario que no es admin falla`() {
        val newDate = """
            {
                "showId": 1,
                "userId": 1,                
                "fecha": "2024-04-18T12:30:45Z",                
            }
        """.trimIndent()

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/admin_dashboard/show/new-show-date")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(newDate))
        )
            .andExpect(status().is4xxClientError)
    }
}