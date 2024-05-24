//package ar.edu.unsam.phm.magicnightsback.domain
//
//import ar.edu.unsam.phm.magicnightsback.factory.TestFactory
//import ar.edu.unsam.phm.magicnightsback.factory.UserTypes
//import io.kotest.core.spec.IsolationMode
//import io.kotest.core.spec.style.DescribeSpec
//import io.kotest.matchers.collections.shouldContain
//
//
//class UserTests : DescribeSpec({
//    isolationMode = IsolationMode.InstancePerTest
//
//    val factory = TestFactory()
//
//    describe("Tests friens") {
//        val user = factory.createUser(UserTypes.NORMAL)
//
//        it("Se pueden agregar amigos a un usuario") {
//            val friend = factory.createUser(UserTypes.POOR)
//            user.addFriend(friend)
//            user.friends shouldContain friend
//        }
//    }
//})