package ar.edu.unsam.phm.magicnightsback.bootstrap

import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Component
@Profile("baseBootstrap")
class UserBootstrap : InitializingBean {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var showRepository: ShowRepository

    val users = mapOf(
        "Pablo" to User(
            name = "Pablo",
            surname = "Foglia",
            username = "madescoces",
            password = "asdf",
        ).apply {
            dni = 26765114
            birthday = LocalDate.of(1978, 10, 20)
            isAdmin = true
            profileImgUrl = "pablito.jpeg"
        },
        "Juan" to User(
            name = "Juan",
            surname = "Caccefo",
            username = "juanceto01",
            password = "asdf"
        ).apply {
            dni = 18274535
            birthday = LocalDate.of(2003, 2, 1)
            profileImgUrl = "juan.jpeg"
        },
        "Sol" to User(
            name = "Sol",
            surname = "Lopez",
            username = "mariasol",
            password = "asdf"
        ).apply {
            dni = 130293745
            birthday = LocalDate.of(2001, 2, 15)
            profileImgUrl = "sol.jpeg"
        },
        "Denise" to User(
            name = "Denise",
            surname = "Amarfil",
            username = "Denise123",
            password = "asdf"
        ).apply {
            dni = 94528553
            birthday = LocalDate.of(2001, 5, 15)
            profileImgUrl = "denise.jpeg"
        },
        "Caro" to User(
            name = "Carolina",
            surname = "Rodriguez",
            username = "CarolRodri",
            password = "asdf"
        ).apply {
            dni = 47435764
            birthday = LocalDate.of(1990, 8, 8)
        },
        "Marcos" to User(
            name = "Marcos",
            surname = "Gonzalez",
            username = "marcosg",
            password = "asdf"
        ).apply {
            dni = 95644456
            birthday = LocalDate.of(1985, 11, 25)
        },
        "Ana" to User(
            name = "Ana",
            surname = "Martinez",
            username = "anam",
            password = "asdf"
        ).apply {
            dni = 27365465
            birthday = LocalDate.of(1995, 4, 12)
        }
    )

    fun addFriends() {
        users["Pablo"]?.apply {
            addFriend(users["Juan"]!!)
            addFriend(users["Sol"]!!)
        }

        users["Juan"]?.apply {
            addFriend(users["Pablo"]!!)
            addFriend(users["Sol"]!!)
            addFriend(users["Denise"]!!)
            addFriend(users["Caro"]!!)
            addFriend(users["Marcos"]!!)
            addFriend(users["Ana"]!!)
        }

        users["Sol"]?.apply {
            addFriend(users["Pablo"]!!)
            addFriend(users["Juan"]!!)
            addFriend(users["Denise"]!!)
            addFriend(users["Caro"]!!)
            addFriend(users["Marcos"]!!)
            addFriend(users["Ana"]!!)
        }

        users["Denise"]?.apply {
            addFriend(users["Juan"]!!)
            addFriend(users["Sol"]!!)
        }

        users["Caro"]?.apply {
            addFriend(users["Juan"]!!)
            addFriend(users["Sol"]!!)
            addFriend(users["Marcos"]!!)
            addFriend(users["Ana"]!!)
        }

        users["Marcos"]?.apply {
            addFriend(users["Juan"]!!)
            addFriend(users["Sol"]!!)
            addFriend(users["Caro"]!!)
        }

        users["Ana"]?.apply {
            addFriend(users["Juan"]!!)
            addFriend(users["Sol"]!!)
            addFriend(users["Caro"]!!)
        }
    }

    fun addCredits() {
        users.values.forEach { it.modifyBalance(100000.0) }
    }

    fun createUsers() {
        users.values.forEach {
            val userInRepo = userRepository.findByUsername(it.name).getOrNull()
            if (userInRepo != null) {
                it.id = userInRepo.id
            } else {
                userRepository.save(it)
                println("User ${it.name} created")
            }
        }
    }

    override fun afterPropertiesSet() {
        println("User creation process starts")
        addCredits()
        addFriends()
        createUsers()
    }
}