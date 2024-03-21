package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps
import java.time.LocalDate

class User(
    val name: String,
    val surname: String,
    val username: String,
    val birthday: LocalDate,
    val dni: Int,
    var password: String,
    val kart: Kart = Kart(),
) : RepositoryProps() {
    val friends = mutableListOf<User>()
    val tickets = mutableListOf<Ticket>()
    val opinions = mutableListOf<Opinion>()
    var credit = 0.0
    fun addFriend(user: User) {
        friends.add(user)
    }

    fun removeFriend(user: User) {
        friends.remove(user)
    }

    fun addOpinion(opinion: Opinion) {
        opinions.add(opinion)
    }

    fun removeOpinion(opinion: Opinion) {
        opinions.remove(opinion)
    }

    fun addTicket(ticket: Ticket) {
        tickets.add(ticket)
    }

    fun removeTicket(ticket: Ticket) {
        tickets.remove(ticket)
    }

    fun addCredit(credit: Double) {
        this.credit += credit
    }

    fun removeCredit(credit: Double) {
        this.credit -= credit
    }

    fun age(): Int = birthday.calculateAge()

    fun pay(price: Double) {
        removeCredit(price)
    }

    ///// VALIDATORS ///////////////////////////////////////////
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

class Comment