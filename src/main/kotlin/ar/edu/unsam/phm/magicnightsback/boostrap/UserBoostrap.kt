package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserBoostrap(
  val userRepository: UserRepository
) : InitializingBean {
  private val users = mapOf(
    "Pablo" to User(
      name = "Pablo",
      surname = "Foglia",
      username = "MadEscoces",
      dni = 26765114,
      birthday = LocalDate.of(1978, 10, 20),
      password = "asdf"
    ), "Juan" to User(
      name = "Juan",
      surname = "Caccefo",
      username = "juanceto01",
      dni = 1,
      birthday = LocalDate.of(2003, 2, 1),
      password = "asdf",
    ), "Sol" to User(
      name = "Sol",
      surname = "Lopez",
      username = "mariasol",
      dni = 1,
      birthday = LocalDate.of(2001, 2, 15),
      password = "asdf"
    ), "Alejo" to User(
      name = "Alejo",
      surname = "Menini",
      username = "juanceto01",
      dni = 1,
      birthday = LocalDate.of(2001, 5, 15),
      password = "asdf"
    )
  )

  fun createUsers() {
    users.values.forEach { user -> userRepository.apply { create(user) } }
  }

  override fun afterPropertiesSet() {
    println("User creation process starts")
    this.createUsers()
    println("User creation process ends")
  }
}