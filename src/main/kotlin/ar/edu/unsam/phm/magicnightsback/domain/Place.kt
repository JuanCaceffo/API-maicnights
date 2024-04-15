package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import jakarta.persistence.*

@Entity
data class Place(
    @ManyToOne
    val seat: Seat,
    var capacity: Int = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    init {
        require(capacity >= 0) { throw BusinessException(FacilityError.NEGATIVE_CAPACITY) }
    }
}

@Entity
data class Seat(
    @Transient
    val type: SeatTypes
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    val price: Double = type.price
    @Column(length = 50)
    val name: String = type.name

    init {
        require(price >= 0) { throw BusinessException(FacilityError.NEGATIVE_PRICE) }
    }
}

enum class SeatTypes(val price:Double){
    UPPERLEVEL(10000.0),
    FIELD(15000.0),
    BOX(20000.0),
    LOWERLEVEL(10000.0),
    PULLMAN(15000.0)
}


