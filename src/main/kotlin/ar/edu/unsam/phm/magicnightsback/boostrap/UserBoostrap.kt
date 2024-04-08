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

    val smallshowGranrex = showBoostrap.shows["LaVelaPuerca_GranRex"]!!
    val smallShowTeatroColon = showBoostrap.shows["LaVelaPuerca_TeatroColon"]!!
    val bigShowRiver = showBoostrap.shows["PearlJam_River"]!!
    val bigShowLaBombonera = showBoostrap.shows["PearlJam_LaBombonera"]!!
    val bestSmallShowMovistarArena = showBoostrap.shows["AcDc_MovistarArena"]!!
    val bestSmallShowTeatroOpera = showBoostrap.shows["AcDc_TeatroOpera"]!!
    val losRedondosHipodromoDePalermo = showBoostrap.shows["LosRedondos_HipodromoDePalermo"]!!
    val oneDirectionLunaPark = showBoostrap.shows["OneDirection_LunaPark"]!!
    val queenGranRex = showBoostrap.shows["Queen_GranRex"]!!


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
            dni = 18274535,
            birthday = LocalDate.of(2003, 2, 1),
            password = "asdf",

            profileImage = "juan.jpeg"
        ), "Sol" to User(
            name = "Sol",
            surname = "Lopez",
            username = "mariasol",
            dni = 130293745,
            birthday = LocalDate.of(2001, 2, 15),
            password = "asdf",
            profileImage = "sol.jpeg"
        ), "Denise" to User(
            name = "Denise",
            surname = "Amarfil",
            username = "Denise123",
            dni = 94528553,
            birthday = LocalDate.of(2001, 5, 15),
            password = "asdf",
            profileImage = "denise.jpeg"
        ),
        "Carolina" to User(
            name = "Carolina",
            surname = "Rodriguez",
            username = "CarolRodri",
            dni = 47435764,
            birthday = LocalDate.of(1990, 8, 8),
            password = "asdf"
        ),
        "Marcos" to User(
            name = "Marcos",
            surname = "Gonzalez",
            username = "marcosg",
            dni = 95644456,
            birthday = LocalDate.of(1985, 11, 25),
            password = "asdf"
        ),
        "Ana" to User(
            name = "Ana",
            surname = "Martinez",
            username = "anam",
            dni = 27365465,
            birthday = LocalDate.of(1995, 4, 12),
            password = "asdf"
        )
    )


    fun createUsers() {
        users.values.forEach { user -> userRepository.apply { create(user) } }
    }

    fun addFriends() {
        val pablo = users["Pablo"]!!
        val juan = users["Juan"]!!
        val sol = users["Sol"]!!
        val denise = users["Denise"]!!
        val carolina = users["Carolina"]!!
        val marcos = users["Marcos"]!!
        val ana = users["Ana"]!!

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
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            repeat(2) {
                addTicket(
                    Ticket(
                        smallShowTeatroColon,
                        smallShowTeatroColon.dates.elementAt(1),
                        pullman,
                        smallShowTeatroColon.ticketPrice(pullman)
                    )
                )
            }
            repeat(2) {
                addTicket(
                    Ticket(
                        bigShowRiver,
                        bigShowRiver.dates.elementAt(0),
                        pullman,
                        bigShowRiver.ticketPrice(pullman)
                    )
                )
            }
            addTicket(
                Ticket(
                    bigShowLaBombonera,
                    bigShowLaBombonera.dates.elementAt(0),
                    upperlevel,
                    bigShowLaBombonera.ticketPrice(upperlevel)
                )
            )
        }

        users["Sol"]!!.apply {
            repeat(2) {
                addTicket(
                    Ticket(
                        smallshowGranrex,
                        smallshowGranrex.dates.elementAt(0),
                        lowerlevel,
                        smallshowGranrex.ticketPrice(lowerlevel)
                    )
                )
            }
            repeat(2) {
                addTicket(
                    Ticket(
                        bigShowRiver,
                        bigShowRiver.dates.elementAt(0),
                        pullman,
                        bigShowRiver.ticketPrice(pullman)
                    )
                )
            }
        }

        users["Juan"]!!.apply {
            repeat(3) {
                addTicket(
                    Ticket(
                        smallshowGranrex,
                        smallshowGranrex.dates.elementAt(0),
                        pullman,
                        smallshowGranrex.ticketPrice(pullman)
                    )
                )
            }
            repeat(2) {
                addTicket(
                    Ticket(
                        bigShowRiver,
                        bigShowRiver.dates.elementAt(0),
                        pullman,
                        bigShowRiver.ticketPrice(pullman)
                    )
                )
            }
        }

        users["Denise"]!!.apply {
            addTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            repeat(2) {
                addTicket(
                    Ticket(
                        bigShowRiver,
                        bigShowRiver.dates.elementAt(0),
                        pullman,
                        bigShowRiver.ticketPrice(pullman)
                    )
                )
            }
        }

        users["Carolina"]!!.apply {
            addTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            addTicket(
                Ticket(
                    bestSmallShowMovistarArena,
                    bestSmallShowMovistarArena.dates.elementAt(0),
                    lowerlevel,
                    bestSmallShowMovistarArena.ticketPrice(lowerlevel)
                )
            )
        }

        users["Marcos"]!!.apply {
            addTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            addTicket(
                Ticket(
                    bestSmallShowTeatroOpera,
                    bestSmallShowTeatroOpera.dates.elementAt(0),
                    pullman,
                    bestSmallShowTeatroOpera.ticketPrice(pullman)
                )
            )
        }

        users["Ana"]!!.apply {
            addTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            addTicket(
                Ticket(
                    losRedondosHipodromoDePalermo,
                    losRedondosHipodromoDePalermo.dates.elementAt(0),
                    field,
                    losRedondosHipodromoDePalermo.ticketPrice(field)
                )
            )
        }
    }

            fun addAttendees() {
                smallshowGranrex.apply {
                    dates.elementAt(0).addAttendee(users["Pablo"]!!)
                    dates.elementAt(0).addAttendee(users["Sol"]!!)
                    dates.elementAt(0).addAttendee(users["Denise"]!!)
                    dates.elementAt(0).addAttendee(users["Juan"]!!)
                    dates.elementAt(0).addAttendee(users["Ana"]!!)
                    dates.elementAt(0).addAttendee(users["Marcos"]!!)
                    dates.elementAt(0).addAttendee(users["Carolina"]!!)
                }
                smallShowTeatroColon.apply {
                    dates.elementAt(1).addAttendee(users["Pablo"]!!)
                }
                bigShowRiver.apply {
                    dates.elementAt(1).addAttendee(users["Pablo"]!!)
                    dates.elementAt(1).addAttendee(users["Sol"]!!)
                    dates.elementAt(1).addAttendee(users["Juan"]!!)
                    dates.elementAt(1).addAttendee(users["Denise"]!!)
                }
                bigShowLaBombonera.apply {
                    dates.elementAt(0).addAttendee(users["Pablo"]!!)
                }
                bestSmallShowMovistarArena.apply {
                    dates.elementAt(0).addAttendee(users["Carolina"]!!)
                }
                bestSmallShowTeatroOpera.apply {
                    dates.elementAt(0).addAttendee(users["Marcos"]!!)
                }
                losRedondosHipodromoDePalermo.apply {
                    dates.elementAt(0).addAttendee(users["Ana"]!!)
                }
            }

            override fun afterPropertiesSet() {
                println("User creation process starts")
                createUsers()
                addFriends()
                addAttendees()
                buyTickets()
                println("User creation process ends")
            }
        }