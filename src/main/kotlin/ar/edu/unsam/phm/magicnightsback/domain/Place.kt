package ar.edu.unsam.phm.magicnightsback.domain
//
//import ar.edu.unsam.phm.magicnightsback.error.BusinessException
//import ar.edu.unsam.phm.magicnightsback.error.FacilityError
//import jakarta.persistence.*
//
//@Entity
//class Place(
//    @Enumerated(EnumType.STRING)
//    val seatType: SeatTypes,
//    var capacity: Int = 0
//) {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long? = null
//    val price = seatType.price
//
//    init {
//        require(capacity >= 0) { throw BusinessException(FacilityError.NEGATIVE_CAPACITY) }
//    }
//}
//
//enum class SeatTypes(val price: Double) {
//    UPPERLEVEL(10000.0),
//    FIELD(15000.0),
//    BOX(20000.0),
//    LOWERLEVEL(10000.0),
//    PULLMAN(15000.0)
//}
//

