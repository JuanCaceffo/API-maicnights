package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Cart
import ar.edu.unsam.phm.magicnightsback.domain.StadiumSeatType
import ar.edu.unsam.phm.magicnightsback.domain.TheaterSeatType
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(4)
@DependsOn("userBoostrap")
class CartBoostrap(
    val cartRepository: CartRepository,
    userBoostrap: UserBoostrap,
    showBoostrap: ShowBoostrap
) : InitializingBean {

    val smallshowGranrex = showBoostrap.shows["LaVelaPuerca_GranRex"]!!
    val smallShowTeatroColon = showBoostrap.shows["LaVelaPuerca_TeatroColon"]!!
    val bigShowRiver = showBoostrap.shows["PearlJam_River"]!!
    val bigShowLaBombonera = showBoostrap.shows["PearlJam_LaBombonera"]!!
    val bestSmallShowMovistarArena = showBoostrap.shows["AcDc_MovistarArena"]!!
    val bestSmallShowTeatroOpera = showBoostrap.shows["AcDc_TeatroOpera"]!!
    val losRedondosClubDePolo = showBoostrap.shows["LosRedondos_ClubDePolo"]!!

    val lowerlevel = TheaterSeatType.LOWERLEVEL
    val pullman = TheaterSeatType.PULLMAN
    val upperlevel = StadiumSeatType.UPPERLEVEL
    val field = StadiumSeatType.FIELD
    val box = StadiumSeatType.BOX

    val carts = mapOf(
        "CartPablo" to Cart(userBoostrap.users["Pablo"]!!),
        "CartSol" to Cart(userBoostrap.users["Sol"]!!),
        "CartJuan" to Cart(userBoostrap.users["Juan"]!!),
        "CartDenise" to Cart(userBoostrap.users["Denise"]!!),
        "CartCarolina" to Cart(userBoostrap.users["Carolina"]!!),
        "CartMarcos" to Cart(userBoostrap.users["Marcos"]!!),
        "CartAna" to Cart(userBoostrap.users["Ana"]!!)
    )
    fun createCarts() {
        carts.values.forEach { cart -> cartRepository.create(cart) }
    }

    fun addTickets(){
        carts["CartPablo"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            reserveTicket(
                Ticket(
                    smallShowTeatroColon,
                    smallShowTeatroColon.dates.elementAt(1),
                    pullman,
                    smallShowTeatroColon.ticketPrice(pullman),
                    2
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver,
                    bigShowRiver.dates.elementAt(0),
                    upperlevel,
                    bigShowRiver.ticketPrice(upperlevel),
                    2
                )
            )
            reserveTicket(
                Ticket(
                    bigShowLaBombonera,
                    bigShowLaBombonera.dates.elementAt(0),
                    upperlevel,
                    bigShowLaBombonera.ticketPrice(upperlevel)
                )
            )
        }
        carts["CartSol"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    lowerlevel,
                    smallshowGranrex.ticketPrice(lowerlevel)
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver,
                    bigShowRiver.dates.elementAt(0),
                    upperlevel,
                    bigShowRiver.ticketPrice(upperlevel),
                    2
                )
            )
        }
        carts["CartJuan"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver,
                    bigShowRiver.dates.elementAt(0),
                    upperlevel,
                    bigShowRiver.ticketPrice(upperlevel),
                    2
                )
            )
        }
        carts["CartDenise"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            reserveTicket(
                Ticket(
                    bigShowRiver,
                    bigShowRiver.dates.elementAt(0),
                    upperlevel,
                    bigShowRiver.ticketPrice(upperlevel),
                    2
                )
            )
        }
        carts["CartCarolina"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            reserveTicket(
                Ticket(
                    bestSmallShowMovistarArena,
                    bestSmallShowMovistarArena.dates.elementAt(0),
                    lowerlevel,
                    bestSmallShowMovistarArena.ticketPrice(lowerlevel)
                )
            )
        }
        carts["CartMarcos"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            reserveTicket(
                Ticket(
                    bestSmallShowTeatroOpera,
                    bestSmallShowTeatroOpera.dates.elementAt(0),
                    pullman,
                    bestSmallShowTeatroOpera.ticketPrice(pullman)
                )
            )
        }
        carts["CartAna"]!!.apply {
            reserveTicket(
                Ticket(
                    smallshowGranrex,
                    smallshowGranrex.dates.elementAt(0),
                    pullman,
                    smallshowGranrex.ticketPrice(pullman)
                )
            )
            reserveTicket(
                Ticket(
                    losRedondosClubDePolo,
                    losRedondosClubDePolo.dates.elementAt(0),
                    field,
                    losRedondosClubDePolo.ticketPrice(field)
                )
            )
        }
    }

    fun buyCarts(){
        carts.values.forEach{
            it.buyReservedTickets()
        }
    }

    override fun afterPropertiesSet() {
        println("Cart creation process starts")
        createCarts()
        addTickets()
        buyCarts()
        println("Cart creation process ends")
    }
}