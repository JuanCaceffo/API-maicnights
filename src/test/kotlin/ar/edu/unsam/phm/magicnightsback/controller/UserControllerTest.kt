package ar.edu.unsam.phm.magicnightsback.controller;

import ar.edu.unsam.phm.magicnightsback.boostrap.ShowBoostrap
import ar.edu.unsam.phm.magicnightsback.boostrap.UserBoostrap
import ar.edu.unsam.phm.magicnightsback.domain.StadiumSeatType
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.dto.FriendDTO
import ar.edu.unsam.phm.magicnightsback.dto.toCartDTO
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import ar.edu.unsam.phm.magicnightsback.serializers.LoginUserDTO
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.kotest.matchers.collections.shouldNotContain
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Controller de usuario")
class UserControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val showRepository: ShowRepository,
//    @Autowired val userBoostrap: UserBoostrap,
//    @Autowired val showBoostrap: ShowBoostrap
) {
    val mapper = ObjectMapper()


    @MockBean
    lateinit var userRepository: UserRepository

    @BeforeAll
    fun init() {
        mapper.registerModules(JavaTimeModule())
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
    val generalDateTime: LocalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231", formatter)

    @Test
    fun `Un usuario se puede logear con las credenciales correctas`() {
        // Arrange
        val userToLogin = LoginUserDTO("Nombre", "password")
        val userId = 0L

        `when`(userRepository.getLoginUser(userToLogin)).thenReturn(userId)

        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(userToLogin))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(userId.toString()))
    }

    @Test
    fun `deleteUserFriend elimina un amigo del usuario`() {
        val user = User(
            "Nombre",
            "Apellido",
            "usuario",
            LocalDate.now(),
            123,
            "password"
        )

        val friend = User(
            "Nombre",
            "Apellido",
            "usuario",
            LocalDate.now(),
            123,
            "password"
        )

        user.id = 0L
        friend.id = 1L

        user.addFriend(friend)

        `when`(userRepository.getById(user.id)).thenReturn(user)

        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/user_profile/${user.id}/friends/${friend.id}")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        user.friends shouldNotContain  friend
    }
}
