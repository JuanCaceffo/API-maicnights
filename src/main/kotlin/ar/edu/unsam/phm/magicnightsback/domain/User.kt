package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDate

class User(
    var name: String,
    var surname: String,
    val username: String,
    val birthday: LocalDate,
    val dni: Int,
    var password: String,
    //TODO: analizar la posibilidad de un strategy de roles
    var isAdmin: Boolean = false,
    val profileImage: String = "default.jpg"
) : Iterable() {
    val friends = mutableListOf<User>()
    val reservedTickets = mutableListOf<Ticket>()
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

    fun addComment(comment: Comment, show: Show) {
        validComment(show)
        comments.add(comment)
    }

    private fun validComment(show: Show){
        if (!show.canBeCommented(this)) throw BusinessException(showError.USER_CANT_COMMENT)
    }
    fun isMyFriend(user: User) = friends.contains(user)

    fun removeComment(comment: Comment) {
        comments.remove(comment)
    }

    fun addTicket(ticket: Ticket) {
        ticket.showDate.addAttendee(this)
        tickets.add(ticket)
    }

    fun removeTicket(ticket: Ticket) {
        ticket.showDate.attendees.remove(this)
        tickets.remove(ticket)
    }

    fun addCredit(credit: Double) {
        this.credit += credit
    }

    fun age(): Int = birthday.calculateAge()

    fun buyReservedTickets() {
        val price = reservedTickets.sumOf { ticket -> ticket.price() }
        credit = (credit - price).throwErrorIfNegative(BusinessException(UserError.MSG_NOT_ENOUGH_CREDIT)).toDouble()

        reservedTickets.forEach { ticket -> addTicket(ticket) }
        reservedTickets.clear()
    }

    ///// VALIDATORS ///////////////////////////////////////////
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }

    fun throwIfNotAdmin(msg: String) {
        if (!isAdmin) throw AuthenticationException(msg)
    }
}