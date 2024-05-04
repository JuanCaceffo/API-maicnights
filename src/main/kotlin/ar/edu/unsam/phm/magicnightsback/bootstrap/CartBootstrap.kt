package ar.edu.unsam.phm.magicnightsback.bootstrap

import ar.edu.unsam.phm.magicnightsback.domain.Cart
import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
//import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("baseBootstrap")
@DependsOn("showBootstrap", "userBootstrap")
class CartBootstrap(
    val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    showRepository: ShowRepository,
    repository: UserRepository,
) : InitializingBean {

    val smallshowGranrex = showRepository.findById(1).get()
    val smallShowTeatroColon = showRepository.findById(2).get()
    val bigShowRiver = showRepository.findById(3).get()
    val bigShowLaBombonera = showRepository.findById(4).get()
    val bestSmallShowMovistarArena = showRepository.findById(5).get()
    val bestSmallShowTeatroOpera = showRepository.findById(6).get()
    val losRedondosClubDePolo = showRepository.findById(7).get()

    val users = listOf(
        userRepository.findByUsername("madescoces").get(),
        userRepository.findByUsername("mariasol").get(),
        userRepository.findByUsername("juanceto01").get(),
        userRepository.findByUsername("Denise123").get(),
        userRepository.findByUsername("CarolRodri").get(),
        userRepository.findByUsername("marcosg").get(),
        userRepository.findByUsername("anam").get()
    )

    val carts = mapOf(
        "CartPablo" to Cart(users[0]),
        "CartSol" to Cart(users[1]),
        "CartJuan" to Cart(users[2]),
        "CartDenise" to Cart(users[3]),
        "CartCarolina" to Cart(users[4]),
        "CartMarcos" to Cart(users[5]),
        "CartAna" to Cart(users[6])
    )

    fun createCarts() {
        carts.values.forEach {
            cartRepository.save(it)
            println("Cart for ${it.user.name} created")
        }
        saveUsers()
    //TODO: guardar los shows que estan persisitidos
    }

    fun saveUsers() {
        users.forEach {
            userRepository.save(it)
            println("User ${it.name} saved ")
        }
    }

    fun addTickets() {
        carts["CartPablo"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    SeatTypes.PULLMAN,
                )
            )
            reserveTicket(
                Ticket(
                    smallShowTeatroColon, smallShowTeatroColon.dates.elementAt(1), SeatTypes.PULLMAN, 2
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver, bigShowRiver.dates.elementAt(0), SeatTypes.UPPERLEVEL, 2
                )
            )
            reserveTicket(
                Ticket(
                    bigShowLaBombonera,
                    bigShowLaBombonera.dates.elementAt(0),
                    SeatTypes.UPPERLEVEL,
                )
            )
        }
        carts["CartSol"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    SeatTypes.LOWERLEVEL,
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver, bigShowRiver.dates.elementAt(0), SeatTypes.UPPERLEVEL, 2
                )
            )
        }
        carts["CartJuan"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    SeatTypes.PULLMAN,
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver, bigShowRiver.dates.elementAt(0), SeatTypes.UPPERLEVEL, 2
                )
            )
        }
        carts["CartDenise"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    SeatTypes.PULLMAN,
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver, bigShowRiver.dates.elementAt(0), SeatTypes.UPPERLEVEL, 2
                )
            )
        }
        carts["CartCarolina"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    SeatTypes.PULLMAN,
                )
            )
            reserveTicket(
                Ticket(
                    bestSmallShowMovistarArena,
                    bestSmallShowMovistarArena.dates.elementAt(0),
                    SeatTypes.LOWERLEVEL,
                )
            )
        }
        carts["CartMarcos"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    SeatTypes.PULLMAN,
                )
            )
            reserveTicket(
                Ticket(
                    bestSmallShowTeatroOpera,
                    bestSmallShowTeatroOpera.dates.elementAt(0),
                    SeatTypes.PULLMAN,
                )
            )
        }
        carts["CartAna"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    SeatTypes.PULLMAN,
                )
            )
            reserveTicket(
                Ticket(
                    losRedondosClubDePolo,
                    losRedondosClubDePolo.dates.elementAt(0),
                    SeatTypes.FIELD,
                )
            )
        }
    }

    fun buyCarts() {
        carts.values.forEach {
            it.buyReservedTickets()
        }
    }

    override fun afterPropertiesSet() {
        println("Cart creation process starts")
        addTickets()
        buyCarts()
        createCarts()
    }
}