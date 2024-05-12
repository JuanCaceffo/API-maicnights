package ar.edu.unsam.phm.magicnightsback.domain.factory

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.UserRole
import org.springframework.stereotype.Component
import java.time.LocalDate

enum class UserFactoryTypes {
    ADMIN, NORMAL, POOR, NOIMAGE
}

@Component
class UserFactory() {
    fun createUser(type: UserFactoryTypes): User = when (type) {
        UserFactoryTypes.ADMIN -> AdminUser().build()
        UserFactoryTypes.NORMAL -> NormalUser().build()
        UserFactoryTypes.POOR -> PoorUser().build()
        UserFactoryTypes.NOIMAGE -> NoImageUser().build()
    }
}

interface FactoryUser : FactoryObject<User>

class AdminUser(
) : FactoryUser {
    override fun build() =
        User(
            firstName = "Pablo",
            lastName = "Foglia",
            username = "madescoces",
            password = "istheadmin",
        ).apply {
            role = UserRole.ADMIN
            balance = 200000.0
            dni = 16365114
            profileImgUrl = "pablito.jpeg"
            birthday = LocalDate.of(1978, 10, 20)
        }
}

class NormalUser : FactoryUser {
    override fun build() =
        User(
            firstName = "Sol",
            lastName = "Lopez",
            username = "lopezsol7",
            password = "notadmin",
        ).apply {
            role = UserRole.USER
            balance = 100000.0
            dni = 33002244
            profileImgUrl = "sol.jpeg"
            birthday = LocalDate.of(2001, 2, 15)
        }
}

class PoorUser: FactoryUser {
    override fun build() =
        User(
            firstName = "Ana",
            lastName = "Martinez",
            username = "anam",
            password = "notadmin",
        ).apply {
            role = UserRole.USER
            dni = 27365465
            profileImgUrl = "anita.jpeg"
            birthday = LocalDate.of(1995, 4, 12)
        }
}

class NoImageUser: FactoryUser {
    override fun build() =
        User(
            firstName = "Carolina",
            lastName = "Rodriguez",
            username = "carolrodri",
            password = "notadmin",
        ).apply {
            role = UserRole.USER
            balance = 50000.0
            dni = 47435764
            birthday = LocalDate.of(1990, 8, 8)
        }
}
