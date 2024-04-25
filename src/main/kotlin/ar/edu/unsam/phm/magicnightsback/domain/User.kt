package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.dto.toDTO
import ar.edu.unsam.phm.magicnightsback.error.AuthenticationException
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
//import ar.edu.unsam.phm.magicnightsback.error.showError
import ar.edu.unsam.phm.magicnightsback.error.UserError
import jakarta.persistence.*
//import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDate

@Entity
@Table(name = "spectator")
class User(
    @Column(length = 100)
    var name: String,
    @Column(length = 100)
    var surname: String,
    @Column(length = 100)
    val username: String,
    @Column(length = 20)
    var password: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var birthday: LocalDate = LocalDate.now()
    var dni: Int = 0
    var isAdmin: Boolean = false

    @Column(length = 100)
    var profileImgUrl: String = "default.jpg"

    @ManyToMany(fetch = FetchType.LAZY)
    val friends = mutableListOf<User>()

    @OneToMany(fetch = FetchType.LAZY)
    val tickets = mutableListOf<Ticket>()
    var credit = 0.0

    fun addFriend(user: User) {
        if (id != user.id) {
            friends.add(user)
        }
    }

    fun removeFriend(user: User) {
        friends.remove(user)
    }

    fun isMyFriend(user: User) = friends.contains(user)

//    private fun validComment(show: Show){
//        if (!show.canBeCommented(this)) throw BusinessException(showError.USER_CANT_COMMENT)
//    }

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

    fun decreaseCredits(amount: Double) {
        credit = (credit - amount).throwErrorIfNegative(BusinessException(UserError.MSG_NOT_ENOUGH_CREDIT)).toDouble()
    }
}