package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@DependsOn("seatBootstrap", "showBoostrap")
class CartBoostrap(
    val cartRepository: CartRepository,
    userBoostrap: UserBoostrap,
    showRepository: ShowRepository,
    seatRepository: SeatRepository
) : InitializingBean {

    val smallshowGranrex = showRepository.findById(1).get()
    val smallShowTeatroColon = showRepository.findById(2).get()
    val bigShowRiver = showRepository.findById(3).get()
    val bigShowLaBombonera = showRepository.findById(4).get()
    val bestSmallShowMovistarArena = showRepository.findById(5).get()
    val bestSmallShowTeatroOpera = showRepository.findById(6).get()
    val losRedondosClubDePolo = showRepository.findById(7).get()

    val pullman = seatRepository.findByName(SeatTypes.PULLMAN.name).get()
    val upperlevel = seatRepository.findByName(SeatTypes.UPPERLEVEL.name).get()
    val lowerlevel = seatRepository.findByName(SeatTypes.LOWERLEVEL.name).get()
    val field = seatRepository.findByName(SeatTypes.FIELD.name).get()


    val carts = mapOf(
        "CartPablo" to Cart(userBoostrap.users[0]),
        "CartSol" to Cart(userBoostrap.users[1]),
        "CartJuan" to Cart(userBoostrap.users[2]),
        "CartDenise" to Cart(userBoostrap.users[3]),
        "CartCarolina" to Cart(userBoostrap.users[4]),
        "CartMarcos" to Cart(userBoostrap.users[5]),
        "CartAna" to Cart(userBoostrap.users[6])
    )

    //TODO: no persitir carritos de usaurio ya persitidos
    fun createCarts() {
        carts.values.forEach {
//            val cartInRepo = cartRepository.findByUserId(it.user.id!!).getOrNull()
//            if (cartInRepo != null) {
//                it.id = cartInRepo.id
//            } else {
                cartRepository.save(it)
                println("Cart for ${it.user.name} created")
//            }
        }
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
        println("Cart creation process ends")
    }
}