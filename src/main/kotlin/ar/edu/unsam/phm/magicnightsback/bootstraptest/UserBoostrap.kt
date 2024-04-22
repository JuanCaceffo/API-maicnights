package ar.edu.unsam.phm.magicnightsback.bootstraptest
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Component
@Profile("app")
class UserBoostrap : InitializingBean {
    @Autowired
    lateinit var userRepository: UserRepository

    val users = listOf(
        User(
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
        User(
            name = "Juan",
            surname = "Caccefo",
            username = "juanceto01",
            password = "asdf"
        ).apply {
            dni = 18274535
            birthday = LocalDate.of(2003, 2, 1)
            profileImgUrl = "juan.jpeg"
        },
        User(
            name = "Sol",
            surname = "Lopez",
            username = "mariasol",
            password = "asdf"
        ).apply {
            dni = 130293745
            birthday = LocalDate.of(2001, 2, 15)
            profileImgUrl = "sol.jpeg"
        },
        User(
            name = "Denise",
            surname = "Amarfil",
            username = "Denise123",
            password = "asdf"
        ).apply {
            dni = 94528553
            birthday = LocalDate.of(2001, 5, 15)
            profileImgUrl = "denise.jpeg"
        },
        User(
            name = "Carolina",
            surname = "Rodriguez",
            username = "CarolRodri",
            password = "asdf"
        ).apply {
            dni = 47435764
            birthday = LocalDate.of(1990, 8, 8)
        },
        User(
            name = "Marcos",
            surname = "Gonzalez",
            username = "marcosg",
            password = "asdf"
        ).apply {
            dni = 95644456
            birthday = LocalDate.of(1985, 11, 25)
        },
        User(
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
        val pablo = users[0]
        val juan = users[1]
        val sol = users[2]
        val denise = users[3]
        val carolina = users[4]
        val marcos = users[5]
        val ana = users[6]

        pablo.apply {
            addFriend(juan)
            addFriend(sol)
        }

        juan.apply {
            addFriend(pablo)
            addFriend(sol)
            addFriend(denise)
            addFriend(carolina)
            addFriend(marcos)
            addFriend(ana)
        }

        sol.apply {
            addFriend(pablo)
            addFriend(juan)
            addFriend(denise)
            addFriend(carolina)
            addFriend(marcos)
            addFriend(ana)
        }

        denise.apply {
            addFriend(juan)
            addFriend(sol)
        }

        carolina.apply {
            addFriend(juan)
            addFriend(sol)
            addFriend(marcos)
            addFriend(ana)
        }

        marcos.apply {
            addFriend(juan)
            addFriend(sol)
            addFriend(carolina)
        }

        ana.apply {
            addFriend(juan)
            addFriend(sol)
            addFriend(carolina)
        }

    }

    fun addCredits() {
        users.forEach{ it.addCredit(100000.0) }
    }

    fun createUsers() {
        users.forEach {
            val userInRepo = userRepository.findByUsername(it.name).getOrNull()
            if (userInRepo != null) {
                it.id = userInRepo.id
            } else {
                userRepository.save(it)
                println("Band ${it.name} created")
            }
        }
    }

    override fun afterPropertiesSet() {
        println("User creation process starts")
        addFriends()
        addCredits()
        createUsers()
        println("User creation process ends")
    }
}