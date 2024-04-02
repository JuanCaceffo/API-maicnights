package ar.edu.unsam.phm.magicnightsback.controller;


import ar.edu.unsam.phm.magicnightsback.boostrap.ShowBoostrap
import ar.edu.unsam.phm.magicnightsback.boostrap.UserBoostrap
import ar.edu.unsam.phm.magicnightsback.domain.StadiumSeatType
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.dto.toCartDTO
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
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
import java.time.LocalDateTime

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
    @BeforeAll
    fun init(){
        mapper.registerModules(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
    @AfterEach
    fun end() {
        userBoostrap.afterPropertiesSet()
        showBoostrap.afterPropertiesSet()
    }

    val generalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231")

    @Test
    fun `Dado un endpoint para obtener los tickets del carrito de un usuario con un ticket reservado funciona bien`() {
        //arrange
        val user = userRepository.getById(1)
        val show = showRepository.getById(1)
        val ticket = Ticket(show, show.dates.first(), StadiumSeatType.UPPERLEVEL)
        //active
        user.cart.add(ticket)

        //assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/user-profile/1/tickets-cart")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(mapper.writeValueAsString(mutableListOf(ticket.toCartDTO(1, listOf(LocalDateTime.parse("2024-03-30T16:57:04.074472231").minusDays(3)), 10016.0,1))))
            )
        user.cart.clear()
    }
    @Test
    fun `Dado un endpoint para obtener los tickets del carrito de un mismo show con funciones diferentes de un usuario funciona bien`() {
        //arrange
        val user = userRepository.getById(1)
        val show = showRepository.getById(1)
        val ticket = Ticket(show, show.dates.first(), StadiumSeatType.UPPERLEVEL)
        val ticketDifferentDate = Ticket(show, show.dates.last(), StadiumSeatType.UPPERLEVEL)

        //active
        user.cart.add(ticket)
        user.cart.add(ticketDifferentDate)

        //assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/user-profile/1/tickets-cart")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content().json(mapper.writeValueAsString(mutableListOf(ticket.toCartDTO(1, listOf(generalDateTime.minusDays(3), generalDateTime.plusDays(11 + 2.toLong())), 20032.0,2))))
            )
        user.cart.clear()
    }
}
