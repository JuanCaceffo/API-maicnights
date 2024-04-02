package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDate

class User(
    val name: String,
    val surname: String,
    val username: String,
    val birthday: LocalDate,
    val dni: Int,
    var password: String,
    //TODO: analizar la posibilidad de un strategy de roles
    var isAdmin: Boolean = false,
    val profileImage: String = "default.jpg"
) : Iterable() {
    val friends = mutableSetOf<User>()
    val tickets = mutableListOf<Ticket>()
    val comments = mutableListOf<Comment>()
    var credit = 0.0

    fun addFriend(user: User) {
        if (id != user.id) {
            friends.add(user)
        }
    }

    fun removeFriend(user: User) {
        friends.remove(user)
    }

    fun removeFriendById(id: Long) {
        friends.removeIf { friend -> friend.id == id }
    }

    fun addComment(comment: Comment, ticket: Ticket) {
        validComment(ticket)
        comments.add(comment)
    }

    private fun validComment(ticket: Ticket){
        if (!ticket.showDate.datePassed()) throw BusinessException(showError.MSG_DATE_NOT_PASSED)
    }
    fun isMyFriend(userId: Long) = friends.any { it.id == userId }

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
        removeCredit(price)
    }

    ///// VALIDATORS ///////////////////////////////////////////
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }

    fun throwIfNotAdmin(msg: String) {
        //TODO: cambiar a autenthicationException
        if (!isAdmin) throw AuthenticationException(msg)
    }
}