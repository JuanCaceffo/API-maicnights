package ar.edu.unsam.phm.magicnightsback.bootstrap

import ar.edu.unsam.phm.magicnightsback.domain.Cart
import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
//import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("baseBootstrap")
@DependsOn("showBootstrap", "userBootstrap")
class CartBootstrap(
    val cartRepository: CartRepository,
    userRepository: UserRepository,
    showRepository: ShowRepository,
//    seatRepository: SeatRepository
) : InitializingBean {

    val smallshowGranrex = showRepository.findById(1).get()
    val smallShowTeatroColon = showRepository.findById(2).get()
    val bigShowRiver = showRepository.findById(3).get()
    val bigShowLaBombonera = showRepository.findById(4).get()
    val bestSmallShowMovistarArena = showRepository.findById(5).get()
    val bestSmallShowTeatroOpera = showRepository.findById(6).get()
    val losRedondosClubDePolo = showRepository.findById(7).get()

//    val pullman = seatRepository.findByName(SeatTypes.PULLMAN.name).get()
//    val upperlevel = seatRepository.findByName(SeatTypes.UPPERLEVEL.name).get()
//    val SeatTypes.LOWERLEVEL = seatRepository.findByName(SeatTypes.LOWERLEVEL.name).get()
//    val field = seatRepository.findByName(SeatTypes.FIELD.name).get()

    val carts = mapOf(
        "CartPablo" to Cart(userRepository.findByUsername("madescoces").get()),
        "CartSol" to Cart(userRepository.findByUsername("mariasol").get()),
        "CartJuan" to Cart(userRepository.findByUsername("juanceto01").get()),
        "CartDenise" to Cart(userRepository.findByUsername("Denise123").get()),
        "CartCarolina" to Cart(userRepository.findByUsername("CarolRodri").get()),
        "CartMarcos" to Cart(userRepository.findByUsername("marcosg").get()),
        "CartAna" to Cart(userRepository.findByUsername("anam").get())
    )

    //TODO: no persitir carritos de usaurio ya persitidos
    fun createCarts() {
        carts.values.forEach {
            cartRepository.save(it)
            println("Cart for ${it.user.name} created")
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
                    smallShowTeatroColon,
                    smallShowTeatroColon.dates.elementAt(1),
                    SeatTypes.PULLMAN,
                    2
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver,
                    bigShowRiver.dates.elementAt(0),
                    SeatTypes.UPPERLEVEL,
                    2
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
                    bigShowRiver,
                    bigShowRiver.dates.elementAt(0),
                    SeatTypes.UPPERLEVEL,
                    2
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
                    bigShowRiver,
                    bigShowRiver.dates.elementAt(0),
                    SeatTypes.UPPERLEVEL,
                    2
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
                    bigShowRiver,
                    bigShowRiver.dates.elementAt(0),
                    SeatTypes.UPPERLEVEL,
                    2
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