//package ar.edu.unsam.phm.magicnightsback.controller
//
//import ar.edu.unsam.phm.magicnightsback.boostrap.ShowBoostrap
//import ar.edu.unsam.phm.magicnightsback.boostrap.UserBoostrap
//import ar.edu.unsam.phm.magicnightsback.domain.*
//import ar.edu.unsam.phm.magicnightsback.dto.CommentCreateDTO
//import ar.edu.unsam.phm.magicnightsback.dto.TicketCreateDTO
//import ar.edu.unsam.phm.magicnightsback.dto.toTicketDTO
//import ar.edu.unsam.phm.magicnightsback.dto.toUserCommentDTO
//import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
//import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.databind.SerializationFeature
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
//import io.mockk.every
//import io.mockk.mockkStatic
//import org.junit.jupiter.api.*
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import org.uqbar.geodds.Point
//import java.time.LocalDate
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DisplayName("Controller de usuario")
//class UserControllerTest(
//    @Autowired val mockMvc: MockMvc,
//    @Autowired val userRepository: UserRepository,
//    @Autowired val showRepository: ShowRepository,
//    @Autowired val userBoostrap: UserBoostrap,
//    @Autowired val showBoostrap: ShowBoostrap
//) {
//    val mapper = ObjectMapper()
//
//    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
//    val generalDateTime: LocalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231", formatter)
//
//    val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL, 500)
//    val pullman = SeatType(TheaterSeatType.PULLMAN, 300)
//
//
//    @BeforeAll
//    fun init() {
//        mapper.registerModules(JavaTimeModule())
//        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//    }
//    @BeforeEach
//    fun start() {
//        userRepository.clear()
//        showRepository.clear()
//        userRepository.create(
//            User(
//                name = "Juan",
//                surname = "Caccefo",
//                username = "juanceto01",
//                dni = 1,
//                birthday = LocalDate.of(2003, 2, 1),
//                password = "asdf",
//                profileImage = ""
//            )
//        )
//
//        showRepository.create(
//            Show(
//                "Cachenged",
//                Band(
//                    "La Vela Puerca",
//                    10000.0
//                ),
//                Facility(
//                    name = "Gran Rex",
//                    location = Point(-34.60356, -58.38013),
//                    seatStrategy = TheaterStrategy()
//                ).apply {
//                    addSeatType(pullman)
//                    addSeatType(lowerLevel)
//                }
//            ).apply {
//                repeat(5) { addDate(generalDateTime.plusDays(11 + it.toLong())) }
//            }
//        )
//    }
//
//    @AfterAll
//    fun end() {
//        userBoostrap.afterPropertiesSet()
//        showBoostrap.afterPropertiesSet()
//    }
//
//    fun setUserWithTicket(): Ticket {
//        val user = userRepository.getById(0)
//        val show = showRepository.getById(0)
//        val ticket =
//            Ticket(show, show.dates.first(), TheaterSeatType.PULLMAN, show.ticketPrice(TheaterSeatType.PULLMAN))
//        user.reservedTickets.add(ticket)
//        return ticket
//    }
//
//
//
//
//
//
//
//
//
//
//    @Test
//    fun `Un usuario al llamar al endpoint get de comments puede obtener todos los comentarios que realizo a a algun show sin exceptions`() {
//        val user = userRepository.getById(0)
//        val show = showRepository.getById(0)
//        val ticket = Ticket(show, show.dates.first(), TheaterSeatType.PULLMAN, show.ticketPrice(TheaterSeatType.PULLMAN))
//        val comment = Comment(show,"goood",4.0)
//
//        mockkStatic(LocalDateTime::class)
//        every { LocalDateTime.now() }  returns generalDateTime.plusDays(12)
//
//        user.addTicket(ticket)
//        user.addComment(comment, ticket.show)
//
//        mockMvc.perform(
//            MockMvcRequestBuilders
//                .get("/user/0/comments")
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(
//                MockMvcResultMatchers.status().isOk
//            )
//            .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(listOf(comment.toUserCommentDTO()))))
//    }
//
//    fun userWithBuyedTicket(): CommentCreateDTO {
//        val user = userRepository.getById(0)
//        val ticket = setUserWithTicket()
//        val comment = CommentCreateDTO(0L,"godd",4.0)
//
//        user.addTicket(ticket)
//        return comment
//    }
//
//    @Test
//    fun `Un usuario al llamar al endpoint para crear un comentario sale mal ya que la funcion a la que intenta comentar no fue dada aun`() {
//        val comment = userWithBuyedTicket()
//        mockkStatic(LocalDateTime::class)
//        every { LocalDateTime.now() }  returns generalDateTime
//
//
//        mockMvc.perform(
//            MockMvcRequestBuilders
//                .put("/user/0/create-comment")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(comment))
//        )
//            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
//    }
//
//    @Test
//    fun `Un usuario al llamar al endpoint para crear un comenatrio a un show sale bien`() {
//        val comment = userWithBuyedTicket()
//        mockkStatic(LocalDateTime::class)
//        every { LocalDateTime.now() }  returns generalDateTime.plusDays(20)
//
//        mockMvc.perform(
//            MockMvcRequestBuilders
//                .put("/user/0/create-comment")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(comment))
//        )
//            .andExpect(MockMvcResultMatchers.status().isOk)
//    }
//
//    @Test
//    fun `Un usaurio al llamar a un endpoint para eliminar un comentario funciona bien`(){
//        val user = userRepository.getById(0)
//        val show = showRepository.getById(0)
//        val comment = Comment(show,"goood",4.0)
//
//        user.comments.add(comment)
//
//        mockMvc.perform(
//            MockMvcRequestBuilders
//                .delete("/user/0/delete-comment/0")
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(MockMvcResultMatchers.status().isOk)
//    }
//
//
//    //TODO: hacer este test bien, sin when
//    /*
//    @Test
//    fun `Un usuario se puede logear con las credenciales correctas`() {
//        // Arrange
//        val userToLogin = LoginUserDTO("Nombre", "password")
//        val userId = 0L
//
//        `when`(userRepository.getLoginUser(userToLogin)).thenReturn(userId)
//
//        // Act & Assert
//        mockMvc.perform(
//            MockMvcRequestBuilders.post("/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(ObjectMapper().writeValueAsString(userToLogin))
//        )
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andExpect(MockMvcResultMatchers.content().string(userId.toString()))
//    }
//
//    @Test
//    fun `deleteUserFriend elimina un amigo del usuario`() {
//        val user = User(
//            "Nombre",
//            "Apellido",
//            "usuario",
//            LocalDate.now(),
//            123,
//            "password"
//        )
//
//        val friend = User(
//            "Nombre",
//            "Apellido",
//            "usuario",
//            LocalDate.now(),
//            123,
//            "password"
//        )
//
//        user.id = 0L
//        friend.id = 1L
//
//        user.addFriend(friend)
//
//        `when`(userRepository.getById(user.id)).thenReturn(user)
//
//        // Act & Assert
//        mockMvc.perform(
//            MockMvcRequestBuilders.delete("/user_profile/${user.id}/friends/${friend.id}")
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(MockMvcResultMatchers.status().isOk)
//
//        user.friends shouldNotContain  friend
//    }
//    */
//}
