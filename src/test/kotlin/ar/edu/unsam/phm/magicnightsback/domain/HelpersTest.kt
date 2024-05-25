package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.utils.removeSpaces
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class HelpersTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Tests Helper String") {
        it("removeSpaces method should return a string with no spaces") {
            //Arrange
            var string = " Test S tri   ng "
            //Act
            string = string.removeSpaces()
            //Assert
            string shouldBe "TestString"
        }
    }
})