//package ar.edu.unsam.phm.magicnightsback.boostrap
//
//import ar.edu.unsam.phm.magicnightsback.domain.SeatType
//import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
//import ar.edu.unsam.phm.magicnightsback.repository.SeatTypeRepository
//import org.springframework.beans.factory.InitializingBean
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Component
//import kotlin.jvm.optionals.getOrNull
//
//@Component
//class SeatTypeBoostrap : InitializingBean {
//    @Autowired
//    lateinit var seatTypeRepository: SeatTypeRepository
//
//    val seats = listOf(
//        SeatType(SeatTypes.UPPERLEVEL, 10000.0),
//        SeatType(SeatTypes.FIELD, 15000.0),
//        SeatType(SeatTypes.BOX, 20000.0),
//        SeatType(SeatTypes.PULLMAN, 10000.0),
//        SeatType(SeatTypes.LOWERLEVEL, 15000.0)
//    )
//
//    fun createSeats() {
//        seats.forEach {
//            val seatTypeInRepo = seatTypeRepository.findByName(it.name).getOrNull()
//
//            if (seatTypeInRepo != null) {
//                it.id = seatTypeInRepo.id
//            } else {
//                seatTypeRepository.save(it)
//                println("SeatType ${it.name} created")
//            }
//        }
//    }
//    override fun afterPropertiesSet() {
//        println("Seats creation process starts")
//        createSeats()
//    }
//}