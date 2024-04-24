package ar.edu.unsam.phm.magicnightsback.bootstraptest

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
@Profile("baseBoostrap")
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
        Show(name = "Arriba!!", lavela, pocker)
    )

    fun createShows() {
        shows.forEach {
            val showInRepo = showRepository.findByName(it.name)
            if (!showInRepo.isEmpty && showInRepo.get().facility.name == it.facility.name) {
                it.id = showInRepo.get().id
            } else {
                showRepository.save(it)
                println("Show ${it.name} created")
            }
        }
    }

    fun createShowDates() {

        val generalDateTime = LocalDateTime.parse("2024-03-30T16:57:04.074472231")

        shows[0].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(2) { index ->
                dates.add(generalDateTime.minusDays(3 + index.toLong()))
                dates.add(generalDateTime.plusDays(11 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[1].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(2) { index ->
                dates.add(generalDateTime.minusDays(5 + index.toLong()))
                dates.add(generalDateTime.plusDays(14 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[2].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(4) { index ->
                dates.add(generalDateTime.minusDays(7 + index.toLong()))
                dates.add(generalDateTime.plusDays(10 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[3].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(3) { index ->
                dates.add(generalDateTime.minusDays(10 + index.toLong()))
                dates.add(generalDateTime.plusDays(12 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[4].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(2) { index ->
                dates.add(generalDateTime.minusDays(2 + index.toLong()))
                dates.add(generalDateTime.plusDays(13 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[5].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(1) { index ->
                dates.add(generalDateTime.minusDays(4 + index.toLong()))
                dates.add(generalDateTime.plusDays(15 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[6].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(2) { index ->
                dates.add(generalDateTime.minusDays(8 + index.toLong()))
                dates.add(generalDateTime.plusDays(9 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[7].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(2) { index ->
                dates.add(generalDateTime.minusDays(6 + index.toLong()))
                dates.add(generalDateTime.plusDays(16 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[8].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(2) { index ->
                dates.add(generalDateTime.minusDays(1 + index.toLong()))
                dates.add(generalDateTime.plusDays(17 + index.toLong()))
            }
            initialDates(dates)
        }
        shows[9].apply {
            val dates: MutableList<LocalDateTime> = mutableListOf()
            repeat(2) { index ->
                dates.add(generalDateTime.minusDays(1 + index.toLong()))
                dates.add(generalDateTime.plusDays(17 + index.toLong()))
            }
            initialDates(dates)
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




