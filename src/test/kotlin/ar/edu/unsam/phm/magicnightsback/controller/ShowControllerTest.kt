//package ar.edu.unsam.phm.magicnightsback.controller
//
//import ar.edu.unsam.phm.magicnightsback.boostrap.FacilityBoostrap
//import ar.edu.unsam.phm.magicnightsback.boostrap.ShowBoostrap
//import ar.edu.unsam.phm.magicnightsback.boostrap.UserBoostrap
//import ar.edu.unsam.phm.magicnightsback.domain.*
//import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
//import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
//import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
//import com.fasterxml.jackson.databind.ObjectMapper
//import io.kotest.core.annotation.DisplayName
//import org.junit.jupiter.api.AfterAll
//import org.junit.jupiter.api.BeforeAll
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.TestInstance
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import org.uqbar.geodds.Point
//import java.time.LocalDate
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DisplayName("Controller de show")
//class ShowControllerTest(
//    @Autowired val mockMvc: MockMvc,
//    @Autowired val userRepository: UserRepository,
//    @Autowired val showsRepository: ShowRepository,
//    @Autowired val facilityRepository: FacilityRepository,
//    @Autowired val facilityBoostrap: FacilityBoostrap,
//    @Autowired val showBoostrap: ShowBoostrap,
//    @Autowired val userBoostrap: UserBoostrap
//) {
//    val baseFacility = Facility(
//        name = "Gran Rex",
//        location = Point(-34.60356, -58.38013),
//        seatStrategy = TheaterStrategy()
//    )
//
//    val baseShow = Show(
//        "Show de la Vela Puerca", Band("La Vela Puerca", 10000.0), baseFacility
//    )
//
//
//    val baseUser = User(
//        name = "Pablo",
//        surname = "Foglia",
//        username = "MadEscoces",
//        dni = 26765114,
//        birthday = LocalDate.of(1978, 10, 20),
//        password = "asdf"
//    )
//
//    @BeforeEach
//    fun init() {
//        userRepository.clear()
//        showsRepository.clear()
//        facilityRepository.clear()
//
//        userRepository.create(baseUser)
//        showsRepository.create(baseShow)
//        facilityRepository.create(baseFacility)
//    }
//
//    @AfterAll
//    fun end() {
//        userBoostrap.afterPropertiesSet()
//        facilityBoostrap.afterPropertiesSet()
//        showBoostrap.afterPropertiesSet()
//    }
//
//    val mapper = ObjectMapper()
//
//    //TODO: agregar caso feliz cuando este implementado
//    @Test
//    fun `llamada al metodo post para crear una funcion por un usuario que no es admin falla`() {
//        mockMvc.perform(
//            MockMvcRequestBuilders
//                .post("/show/0/create-date/user/0")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString("2024/05/13"))
//        )
//            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
//    }
//}