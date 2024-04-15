package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import jakarta.persistence.*

@Entity
data class Place(
    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
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
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    val type: SeatTypes
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    val price: Double = type.price

    init {
        require(price >= 0) { throw BusinessException(FacilityError.NEGATIVE_PRICE) }
    }
}

enum class SeatTypes(val price:Double){
    UPPERLEVEL(10000.0),
    FIELD(150000.0),
    BOX(20000.0),
    LOWERLEVEL(10000.0),
    PULLMAN(150000.0)
}


