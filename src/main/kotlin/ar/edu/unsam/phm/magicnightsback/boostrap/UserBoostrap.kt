package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.StadiumSeatType
import ar.edu.unsam.phm.magicnightsback.domain.TheaterSeatType
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Order(3)
class UserBoostrap(
    val userRepository: UserRepository,
    showBoostrap: ShowBoostrap
) : InitializingBean {

    val smallShow = showBoostrap.shows["SmallShow"]!!
    val bigShow = showBoostrap.shows["BigShow"]!!
    val bestSmallShow = showBoostrap.shows["BestSmallShow"]!!

    val users = mapOf(
        "Pablo" to User(
            name = "Pablo",
            surname = "Foglia",
            username = "MadEscoces",
            dni = 26765114,
            birthday = LocalDate.of(1978, 10, 20),
            password = "asdf",
            isAdmin = true,
            profileImage = "pablito.jpeg"
        ), "Juan" to User(
            name = "Juan",
            surname = "Caccefo",
            username = "juanceto01",
            dni = 1,
            birthday = LocalDate.of(2003, 2, 1),
            password = "asdf",
            profileImage = "juan.jpeg"
        ), "Sol" to User(
            name = "Sol",
            surname = "Lopez",
            username = "mariasol",
            dni = 1,
            birthday = LocalDate.of(2001, 2, 15),
            password = "asdf",
            profileImage = "sol.jpeg"
        ), "Denise" to User(
            name = "Denise",
            surname = "Amarfil",
            username = "Denise123",
            dni = 1,
            birthday = LocalDate.of(2001, 5, 15),
            password = "asdf",
            profileImage = "denise.jpeg"
        )
    )

    fun createUsers() {
        users.values.forEach { user -> userRepository.apply { create(user) } }
    }

    fun addFriends() {
        users["Pablo"]!!.apply {
            addFriend(users["Juan"]!!)
            addFriend(users["Sol"]!!)
        }

        users["Juan"]!!.apply {
            addFriend(users["Pablo"]!!)
            addFriend(users["Denise"]!!)
        }

        users["Sol"]!!.apply {
            addFriend(users["Pablo"]!!)
            addFriend(users["Denise"]!!)
        }

        users["Denise"]!!.apply {
            addFriend(users["Juan"]!!)
            addFriend(users["Sol"]!!)
        }

        // Actualizar los usuarios en el repositorio
        users.values.forEach { userRepository.update(it) }
    }

    fun buyTickets() {
        val lowerlevel = TheaterSeatType.LOWERLEVEL
        val pullman = TheaterSeatType.PULLMAN
        val upperlevel = StadiumSeatType.UPPERLEVEL
        val field = StadiumSeatType.FIELD
        val box = StadiumSeatType.BOX

        users["Pablo"]!!.apply {
            addTicket(
                Ticket(
                    smallShow,
                    smallShow.dates.elementAt(0),
                    pullman,
                    smallShow.ticketPrice(pullman)
                )
            )
            addTicket(
                Ticket(
                    bigShow,
                    bigShow.dates.elementAt(1),
                    box,
                    bigShow.ticketPrice(box),
                    3
                )
            )

            addTicket(
                Ticket(
                    bigShow,
                    bigShow.dates.elementAt(0),
                    upperlevel,
                    bigShow.ticketPrice(upperlevel)
                )
            )
        }

        users["Sol"]!!.apply {
                addTicket(
                    Ticket(
                        smallShow,
                        smallShow.dates.elementAt(1),
                        lowerlevel,
                        smallShow.ticketPrice(lowerlevel),
                        2
                    )
                )

            addTicket(
                Ticket(
                    bigShow,
                    bigShow.dates.elementAt(2),
                    box,
                    bigShow.ticketPrice(box)
                )
            )
            addTicket(
                Ticket(
                    bigShow,
                    bigShow.dates.elementAt(0),
                    upperlevel,
                    bigShow.ticketPrice(upperlevel)
                )
            )
        }

        users["Juan"]!!.apply {
                addTicket(
                    Ticket(
                        bestSmallShow,
                        bestSmallShow.dates.elementAt(3),
                        pullman,
                        bestSmallShow.ticketPrice(pullman),
                        3
                    )
                )
            addTicket(
                Ticket(
                    bigShow,
                    bigShow.dates.elementAt(0),
                    upperlevel,
                    bigShow.ticketPrice(upperlevel)
                )
            )
        }

        users["Denise"]!!.apply {
            addTicket(
                Ticket(
                    smallShow,
                    smallShow.dates.elementAt(4),
                    pullman,
                    smallShow.ticketPrice(pullman)
                )
            )
            addTicket(
                Ticket(
                    bigShow,
                    bigShow.dates.elementAt(2),
                    field,
                    bigShow.ticketPrice(field)

                )
            )
            addTicket(
                Ticket(
                    bestSmallShow,
                    bestSmallShow.dates.elementAt(3),
                    pullman,
                    bestSmallShow.ticketPrice(pullman)
                )
            )
            addTicket(
                Ticket(
                    bigShow,
                    bigShow.dates.elementAt(0),
                    field,
                    bigShow.ticketPrice(field)

                )
            )
        }
    }

    override fun afterPropertiesSet() {
        println("User creation process starts")
        createUsers()
        addFriends()
        buyTickets()
        println("User creation process ends")
    }
}