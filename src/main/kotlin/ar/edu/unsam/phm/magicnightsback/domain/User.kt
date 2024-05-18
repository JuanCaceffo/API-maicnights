package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.data.constants.ColumnLength
import ar.edu.unsam.phm.magicnightsback.domain.enums.UserRole
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.UpdateError
import ar.edu.unsam.phm.magicnightsback.utils.calculateAge
import ar.edu.unsam.phm.magicnightsback.utils.notNegative
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "people")
class User(
    @Column(length = ColumnLength.SMALL)
    var firstName: String,
    @Column(length = ColumnLength.SMALL)
    var lastName: String,
    @Column(length = ColumnLength.SMALL)
    val username: String,
    @Column(length = ColumnLength.LARGE)
    var password: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    var birthday: LocalDate = LocalDate.now()
    var dni: Int = 0

    @Column(length = ColumnLength.MEDIUM)
    var role: UserRole = UserRole.USER
    var balance = 0.0

    @Column(length = ColumnLength.LARGE)
    var profileImgUrl: String = "default.jpg"

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "user_friends",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "friend_id")]
    )
    val friends = mutableSetOf<User>()

//    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
//    val tickets = mutableListOf<Ticket>()

    fun addFriend(user: User) {
        if (user != this) {
            friends.add(user)
        }
    }

    fun removeFriend(user: User) {
        friends.remove(user)
    }

    fun isMyFriend(userId: Long) = friends.any { it.id == userId }

    

//    fun addTicket(ticket: Ticket) {
//        tickets.add(ticket)
//    }

//    fun removeTicket(ticket: Ticket) {
//        ticket.showDate.attendees.remove(this)
//        tickets.remove(ticket)
//    }

    fun age(): Int = birthday.calculateAge()

    fun modifyBalance(balance: Double) {
        validateNotNegativeBalance(balance)
        this.balance += balance
    }

    fun validateNotNegativeBalance(amount: Double) {
        (balance + amount).notNegative(BusinessException(UpdateError.NEGATIVE_BALANCE))
    }
}