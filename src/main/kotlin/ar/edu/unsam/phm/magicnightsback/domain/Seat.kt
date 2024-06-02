package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.utils.notNegative
import jakarta.persistence.*
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.mapping.Document

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
}

//TODO: ver si es necesario que sea una documento aparte en la base de mongo

@Document
class SeatOcupation(
    val seatId: Long
) {
    private var usedCapacity: Int = 0
    @Transient
    lateinit var seat: Seat

    fun modifyUsedCapacity(value: Int) {
        validateMaxCapacity()
        validateUsedCapacityIsNotUnderZero(value)
        usedCapacity += value
    }

    fun available():Int = seat.maxCapacity.minus(usedCapacity)

    private fun validateMaxCapacity() {
        available().notNegative(BusinessException("Exceeded max capacity"))
    }

    private fun validateUsedCapacityIsNotUnderZero(value: Int) {
        usedCapacity.plus(value).notNegative(BusinessException("Used capacity can't be a negative value"))
    }
}