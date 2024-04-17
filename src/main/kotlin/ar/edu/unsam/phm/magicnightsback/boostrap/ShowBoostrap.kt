package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull


@Component
@DependsOn("bandBootstrap", "facilityBootstrap")
class ShowBoostrap(
    @Autowired
    bandRepository: BandRepository,
    @Autowired
    facilityRepository: FacilityRepository,
) : InitializingBean {
    @Autowired
    lateinit var showRepository: ShowRepository

    // Bands
    val lavela = bandRepository.findByName("La Vela Puerca").get()
    val pearljam = bandRepository.findByName("Pearl Jam").get()
    val acdc = bandRepository.findByName("Ac/Dc").get()
    val redondos = bandRepository.findByName("Los Redondos").get()
    val oned = bandRepository.findByName("One Direction").get()
    val queen = bandRepository.findByName("Queen").get()

    // Places
    val granrex = facilityRepository.findByName("Gran Rex").get()
    val colon = facilityRepository.findByName("Teatro Colon").get()
    val river = facilityRepository.findByName("River Plate").get()
    val boca = facilityRepository.findByName("La Bombonera").get()
    val marena = facilityRepository.findByName("Movistar Arena").get()
    val opera = facilityRepository.findByName("Teatro Opera").get()
    val polo = facilityRepository.findByName("Club De Polo").get()
    val luna = facilityRepository.findByName("Luna Park").get()
    val pocker = facilityRepository.findByName("Teatro Poker").get()

    val shows = listOf(
        Show(name = "Cachenged!!", lavela, granrex),

        Show(name = "Bailanta!!", lavela, colon),
        Show(name = "4 You", pearljam, river),
        Show(name = "4 You", pearljam, boca),
        Show(name = "Demon of Hell Rise Tour", acdc, marena),
        Show(name = "Demon of Hell Rise Tour", acdc, opera),
        Show(name = "Le ricote", redondos, polo),
        Show(name = "Midnight", oned, luna),
        Show(name = "Love of my life", queen, granrex),
        Show(name = "Midnight", lavela, pocker)
    )

    fun createShows() {
        shows.forEach {
            val showInRepo = showRepository.findByName(it.name).getOrNull()
            if (showInRepo != null) {
                it.id = showInRepo.id
            } else {
                showRepository.save(it)
                println("Show ${it.name} created")
            }
        }
    }

    fun createShowDates() {
        val generalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231")
        shows[0].apply {
            repeat(2) { addDate(generalDateTime.minusDays(3 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.minusDays(11 + it.toLong())) }
        }
        shows[1].apply {
            repeat(2) { addDate(generalDateTime.minusDays(5 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(14 + it.toLong())) }
        }
        shows[2].apply {
            repeat(1) { addDate(generalDateTime.minusDays(7 + it.toLong())) }
            repeat(1) { addDate(generalDateTime.plusDays(10 + it.toLong())) }
        }
        shows[3].apply {
            repeat(3) { addDate(generalDateTime.minusDays(10 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(12 + it.toLong())) }
        }
        shows[4].apply {
            repeat(2) { addDate(generalDateTime.minusDays(2 + it.toLong())) }
            repeat(1) { addDate(generalDateTime.plusDays(13 + it.toLong())) }
        }
        shows[5].apply {
            repeat(2) { addDate(generalDateTime.minusDays(4 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(15 + it.toLong())) }
        }
        shows[6].apply {
            repeat(1) { addDate(generalDateTime.minusDays(8 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.plusDays(9 + it.toLong())) }
        }
        shows[7].apply {
            repeat(2) { addDate(generalDateTime.minusDays(6 + it.toLong())) }
            repeat(4) { addDate(generalDateTime.plusDays(16 + it.toLong())) }
        }
        shows[8].apply {
            repeat(2) { addDate(generalDateTime.minusDays(1 + it.toLong())) }
            repeat(3) { addDate(generalDateTime.minusDays(17 + it.toLong())) }
        }
        shows[9].apply {
            repeat(1) { addDate(generalDateTime.minusDays(1 + it.toLong())) }
            repeat(1) { addDate(generalDateTime.plusDays(17 + it.toLong())) }
        }
    }
// Quizas conviene mas hacer el proceso normal de compra de tickets por medio del boostrap del usuario
//    fun addAttendees() {
//        shows["LaVelaPuerca_SmallFacility"].apply {
//            dates.first().apply{
//                reservedSeats[AllSetTypeNames.PULLMAN.name] = 100
//                reservedSeats[AllSetTypeNames.LOWERLEVEL.name] = 50
//            }
//            dates.last().apply{
//                reservedSeats[AllSetTypeNames.PULLMAN.name] = 500
//                reservedSeats[AllSetTypeNames.LOWERLEVEL.name] = 300
//            }
//        }
//    }

    override fun afterPropertiesSet() {
        println("Boostrap show started")
        createShowDates()
        createShows()
        println("Boostrap show finished")
//        addAttendees()
    }
}




