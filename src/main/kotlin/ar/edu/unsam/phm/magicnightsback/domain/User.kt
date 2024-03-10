package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.UserErrors
import java.time.LocalDate

class User (
  val name: String,
  val surname: String,
  val birthday: LocalDate,
  val dni: Double,
  var credit: Double,
  val username: String,
  var password: String,
  val friends: MutableList<User>,
  val tickets: MutableList<Ticket>,
  val comments: MutableList<Comment>
){
  fun addFriend(user: User){ friends.add(user) }
  fun removeFriend(user: User){ friends.remove(user) }
  fun addComment(comment: Comment){ comments.add(comment) }
  fun removeComment(comment: Comment){ comments.remove(comment) }
  fun addTicket(ticket: Ticket){ tickets.add(ticket) }
  fun removeTicket(ticket: Ticket){ tickets.remove(ticket) }
  fun addCredit(credit: Double){ this.credit += credit }
  fun removeCredit(credit: Double){ this.credit -= credit }
  fun age(): Int = ageCalculator.calculate(this.birthday)

  fun pay(price: Double){
    if (!this.validateEnoughCredit(price)) {
      throw BusinessException(UserErrors.MSG_NOT_ENOUGH_CREDIT)
    }
    removeCredit(price)

  }

  ///// VALIDATORS ///////////////////////////////////////////
  private fun validateEnoughCredit(price: Double) = this.credit >= price
}
class Comment