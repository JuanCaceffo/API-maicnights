//package ar.edu.unsam.phm.magicnightsback.factory
//
//import ar.edu.unsam.phm.magicnightsback.domain.*
//import org.springframework.stereotype.Component
//
//enum class UserTypes {
//    ADMIN, POOR, NORMAL
//}
//
//enum class ShowTypes {
//    BIGSHOW, SMALLSHOW, BADSMALLSHOW
//}
//
//enum class FacilityTypes {
//    STADIUM, THEATER, BADTHEATER
//}
//
//@Component
//class TestFactory {
//    fun createBand() = Band("Test Band", 100000.0)
//    fun createFacility(type: FacilityTypes): Facility {
//        return when (type) {
//            FacilityTypes.STADIUM -> TestStadium().build()
//            FacilityTypes.THEATER -> TestTheater().build()
//            FacilityTypes.BADTHEATER -> TestBadTheater().build()
//        }
//    }
//
//    fun createShow(type: ShowTypes) = when (type) {
//        ShowTypes.BIGSHOW -> BaseShow(
//            ShowTypes.BIGSHOW.name, createBand(), createFacility(FacilityTypes.STADIUM)
//        ).build()
//
//        ShowTypes.SMALLSHOW -> BaseShow(
//            ShowTypes.SMALLSHOW.name, createBand(), createFacility(FacilityTypes.THEATER)
//        ).build()
//
//        ShowTypes.BADSMALLSHOW -> BaseShow(
//            ShowTypes.BADSMALLSHOW.name, createBand(), createFacility(FacilityTypes.BADTHEATER)
//        ).build()
//    }
//
//    fun createUser(type: UserTypes) = when (type) {
//        UserTypes.ADMIN -> AdminUser().build()
//        UserTypes.POOR -> PoorUser().build()
//        UserTypes.NORMAL -> CommonUser().build()
//    }
//}
//
//interface TestObject<T> {
//    fun build(): T
//}
//
//interface TestShow : TestObject<Show> {
//    val name: String
//    val band: Band
//    val facility: Facility
//}
//
//
//class TestStadium : TestObject<Facility> {
//    override fun build(): Stadium = Stadium("Test Stadium", Point(1.0, 1.0), 100000.0).apply {
//        addPlace(SeatTypes.FIELD, 30000)
//        addPlace(SeatTypes.UPPERLEVEL, 20000)
//        addPlace(SeatTypes.BOX, 10000)
//    }
//}
//
//class TestTheater : TestObject<Facility> {
//    override fun build(): Theater = Theater("Test Theater", Point(1.0, 1.0)).apply {
//        addPlace(SeatTypes.LOWERLEVEL, 3000)
//        addPlace(SeatTypes.PULLMAN, 2000)
//        hasGoodAcoustics = true
//    }
//}
//
//class TestBadTheater : TestObject<Facility> {
//    override fun build(): Theater = Theater("Test Bad Theater", Point(1.0, 1.0)).apply {
//        addPlace(SeatTypes.LOWERLEVEL, 3000)
//        addPlace(SeatTypes.PULLMAN, 2000)
//    }
//}
//
//class BaseShow(override val name: String, override val band: Band, override val facility: Facility) : TestShow {
//
//    override fun build() = Show("Big Show", band, facility)
//}
//
//class AdminUser : TestObject<User> {
//    override fun build() = User("admin", "admin", "admin", "asdf").apply {
//        isAdmin = true
//        modifyBalance(1000000.0)
//    }
//}
//
//class PoorUser : TestObject<User> {
//    override fun build() = User("admin", "admin", "admin", "asdf").apply { modifyBalance( 0.0) }
//}
//
//class CommonUser : TestObject<User> {
//    override fun build() = User("admin", "admin", "admin", "asdf").apply {
//        modifyBalance( 100000.0)
//    }
//}