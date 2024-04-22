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

//    lateinit var show: Show
//    lateinit var band: Band
//    lateinit var theater: Theater
//    lateinit var admin: User
//    lateinit var normalUser: User

    val mapper = ObjectMapper()

//    @BeforeEach
//    fun init() {
//        admin = User("admin", "amin", "admin", "asdf").apply { isAdmin = true }
//        normalUser = User("user", "user", "user", "asdf")
//        theater = Theater("test_theater", Point(1.0, 1.0))
//        band = Band("test_band")
//        show = Show("test_show", band, theater)
//    }

    @Test
    fun `The initial values of the DB are as expected`(){
        val seats = seatRepository.findAll().toList()
        val bands = bandRepository.findAll().toList()
        val facilities = facilityRepository.findAll().toList()
        val users = userRepository.findAll().toList()
        val shows = showRepository.findAll().toList()
        assertEquals(5, seats.size)
        assertEquals(1, bands.size)
        assertEquals(1, facilities.size)
        assertEquals(2, users.size)
        assertEquals(1, shows.size)
    }

    @Test
    fun `llamada al metodo post para crear una funcion por un usuario que no es admin falla`() {
        val newDate = """
            {
                "showId": 1,
                "userId": 2,                
                "fecha": "2024-04-18T12:30:45Z",                
            }
        """.trimIndent()

//        mockMvc.perform(
//            MockMvcRequestBuilders
//                .post("/api/admin_dashboard/show/new-show-date")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(newDate))
//        )
//            .andExpect(status().is4xxClientError)
    }
}