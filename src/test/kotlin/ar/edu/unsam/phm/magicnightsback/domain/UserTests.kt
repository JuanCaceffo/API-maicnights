package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.collections.shouldNotContainAll
import io.kotest.matchers.doubles.shouldBeLessThan
import io.kotest.matchers.shouldBe

class UserTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Tests friens") {
        val user = User("Nombre", "Apellido", "usuario", "Password")

        it("Se pueden agregar amigos a un usuario") {
            val userFriend = User("Amigo", "Apellido Amigo", "amigo", "Password")

            user.addFriend(userFriend)

            user.friends shouldNotContain (userFriend)
        }

    }
})