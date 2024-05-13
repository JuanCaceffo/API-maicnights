package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.utils.notNegative
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Seat(
    val type: SeatTypes,
    val maxCapacity: Int
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    init {
        require(maxCapacity > 0) { throw BusinessException("maxCapacity must be greater than zero") }
    }

    val price = type.price

    private var usedCapacity: Int = 0

    fun modifyUsedCapacity(value: Int) {
        validateNonNegativalidveCapacity()
        validadUsedCapacityIsNotUnderZero(value)
        usedCapacity.plus(value)
    }

    fun available() = maxCapacity.minus(usedCapacity)

    fun validateNonNegativalidveCapacity() {
        available().notNegative(BusinessException("Exceeded max capacity"))
    }

    fun validadUsedCapacityIsNotUnderZero(value: Int) {
        usedCapacity.plus(value).notNegative(BusinessException("Used capacity can't be a negative value"))
    }
}