package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.doubles.shouldBeLessThan
import io.kotest.matchers.shouldBe

class PlaceTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe("Place Tests") {

        it("Should return the total cost of the facility") {
            //Arrate & Assert
            shouldThrow<BusinessException> {
                Place(SeatTypes.BOX, -1)
            }.message shouldBe FacilityError.NEGATIVE_CAPACITY
        }
    }
})