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
            isAdmin = true
        ), "Juan" to User(
            name = "Juan",
            surname = "Caccefo",
            username = "juanceto01",
            dni = 1,
            birthday = LocalDate.of(2003, 2, 1),
            password = "asdf",
            profileImage = ""
        ), "Sol" to User(
            name = "Sol",
            surname = "Lopez",
            username = "mariasol",
            dni = 1,
            birthday = LocalDate.of(2001, 2, 15),
            password = "asdf"
        ), "Denise" to User(
            name = "Denise",
            surname = "Amarfil",
            username = "Denise123",
            dni = 1,
            birthday = LocalDate.of(2001, 5, 15),
            password = "asdf"
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
            repeat(3) {
                addTicket(
                    Ticket(
                        bigShow,
                        bigShow.dates.elementAt(1),
                        box,
                        bigShow.ticketPrice(box)
                    )
                )
            }
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
            repeat(2) {
                addTicket(
                    Ticket(
                        smallShow,
                        smallShow.dates.elementAt(1),
                        lowerlevel,
                        smallShow.ticketPrice(lowerlevel)
                    )
                )
            }
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
            repeat(3) {
                addTicket(
                    Ticket(
                        bestSmallShow,
                        bestSmallShow.dates.elementAt(3),
                        pullman,
                        bestSmallShow.ticketPrice(pullman)
                    )
                )
            }
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
        }
    }

    fun addAttendees() {
        smallShow.apply {
            smallShow.dates.elementAt(0).addAttendee(users["Pablo"]!!)
            smallShow.dates.elementAt(1).addAttendee(users["Sol"]!!)
            smallShow.dates.elementAt(4).addAttendee(users["Denise"]!!)
        }
        bigShow.apply {
            bigShow.dates.elementAt(1).addAttendee(users["Pablo"]!!)
            bigShow.dates.elementAt(2).addAttendee(users["Sol"]!!)
            bigShow.dates.elementAt(2).addAttendee(users["Denise"]!!)
            bigShow.dates.elementAt(0).addAttendee(users["Juan"]!!)
        }
        bestSmallShow.apply {
            bestSmallShow.dates.elementAt(3).addAttendee(users["Juan"]!!)
        }
    }

    override fun afterPropertiesSet() {
        println("User creation process starts")
        createUsers()
        addFriends()
        buyTickets()
        addAttendees()
        println("User creation process ends")
    }
}