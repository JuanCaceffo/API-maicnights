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
    val comments = mutableListOf<Comment>()
    var credit = 0.0
    fun addFriend(user: User) {
        friends.add(user)
    }

    fun removeFriend(user: User) {
        friends.remove(user)
    }

    fun addComment(comment: Comment) {
        comments.add(comment)
    }

    fun removeComment(comment: Comment) {
        comments.remove(comment)
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
        if (!this.validateEnoughCredit(price)) {
            throw BusinessException(UserError.MSG_NOT_ENOUGH_CREDIT)
        }
        removeCredit(price)

    }

    ///// VALIDATORS ///////////////////////////////////////////
    private fun validateEnoughCredit(price: Double) = this.credit >= price
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

class Comment