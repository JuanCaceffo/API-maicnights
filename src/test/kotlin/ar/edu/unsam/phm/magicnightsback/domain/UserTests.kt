package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.factory.UserFactory
import ar.edu.unsam.phm.magicnightsback.domain.factory.UserFactoryTypes
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
class UserTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Tests friens") {
        val user = UserFactory().createUser(UserFactoryTypes.POOR)

        it("Se pueden agregar amigos a un usuario") {
            val friend = UserFactory().createUser(UserFactoryTypes.RICH)
            user.addFriend(friend)
            user.friends shouldContain friend
        }
    }
})