package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
//import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.error.UserError
//import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.time.LocalDate

@Entity
class User(
    @Column(length = 100) var name: String = "",
    @Column(length = 100)  var surname: String = "",
    @Column(length = 100)  val username: String = "",
    @Column  val birthday: LocalDate = LocalDate.now(),
    @Column  val dni: Int = 0,
    @Column  var password: String,
    //TODO: analizar la posibilidad de un strategy de roles
    @Column  var isAdmin: Boolean = false,
    @Column  val profileImage: String = "default.jpg"
){
    @Id
    @GeneratedValue()
    var id: Long = 0
    @OneToMany(fetch= FetchType.LAZY)
    val friends = mutableListOf<User>()
//    @OneToMany(fetch= FetchType.LAZY)
//    val tickets = mutableListOf<Ticket>()
//    @OneToMany(fetch= FetchType.LAZY)
//    val comments = mutableListOf<Comment>()
    @Column  var credit = 0.0

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

//    fun addComment(comment: Comment, show: Show) {
//        validComment(show)
//        comments.add(comment)
//    }

//    private fun validComment(show: Show){
//        if (!show.canBeCommented(this)) throw BusinessException(showError.USER_CANT_COMMENT)
//    }
    fun isMyFriend(user: User) = friends.contains(user)

//    fun removeComment(comment: Comment) {
//        comments.remove(comment)
//    }

//    fun addTicket(ticket: Ticket) {
//        ticket.showDate.addAttendee(this)
//        tickets.add(ticket)
//    }

//    fun removeTicket(ticket: Ticket) {
//        ticket.showDate.attendees.remove(this)
//        tickets.remove(ticket)
//    }

    fun addCredit(credit: Double) {
        this.credit += credit
    }

    fun age(): Int = birthday.calculateAge()

    fun decreaseCredits(qunatity: Double){
        credit = (credit - qunatity).throwErrorIfNegative(BusinessException(UserError.MSG_NOT_ENOUGH_CREDIT)).toDouble()
    }

    ///// VALIDATORS ///////////////////////////////////////////
    fun throwIfNotAdmin(msg: String) {
        if (!isAdmin) throw AuthenticationException(msg)
    }
}