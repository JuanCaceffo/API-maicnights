package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.UserError
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val friends = mutableSetOf<User>()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val tickets = mutableListOf<Ticket>()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var balances: MutableSet<BalanceHistory> = mutableSetOf()

    fun addFriend(user: User) {
        if (user != this) {
            friends.add(user)
        }
    }

    fun removeFriend(user: User) {
        friends.remove(user)
    }

    fun isMyFriend(user: User) = friends.any { it.id == user.id }

    fun addTicket(ticket: Ticket) {
        tickets.add(ticket)
    }

    fun removeTicket(ticket: Ticket) {
        ticket.showDate.attendees.remove(this)
        tickets.remove(ticket)
    }

    fun totalBalance() = balances.sumOf { it.amount }

    fun modifyBalance(amount: Double) {
        validateNotNegativeBalance(amount)
        balances.add(BalanceHistory(amount, LocalDateTime.now(), this))
    }

    fun age(): Int = birthday.calculateAge()

    fun validateNotNegativeBalance(amount: Double) {
        (totalBalance() + amount).throwErrorIfNegative(BusinessException(UserError.MSG_NOT_ENOUGH_CREDIT))
    }
}