package ar.edu.unsam.phm.magicnightsback.bootstraptest

//import ar.edu.unsam.phm.magicnightsback.domain.Comment
//import ar.edu.unsam.phm.magicnightsback.domain.StadiumSeatType
//import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.repository.CommentRepository
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import ar.edu.unsam.phm.magicnightsback.service.UserService
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
//import org.springframework.context.annotation.DependsOn
//import org.springframework.core.annotation.Order
//import org.springframework.stereotype.Component

@Component
@Profile("baseBoostrap")
@DependsOn("userBoostrap", "showBoostrap")
class CommentBoostrap(
    @Autowired
    userService: UserService,
    @Autowired
    showService: ShowService
) : InitializingBean {
    @Autowired
    lateinit var commentRepository: CommentRepository

    val madescoces = userService.findByUsername("madescoces")
    val cachengued = showService.findByName("Cachenged!!")
    val comentarioPearlJam = """La noche con Pearl Jam fue simplemente espectacular. Desde el primer acorde hasta
        |el último, la banda nos llevó en un viaje emocionante a través de su música icónica. Eddie Vedder irradiaba
        |energía en el escenario, y cada canción resonaba en lo más profundo de mi ser. La atmósfera estaba cargada
        |de emoción y camaradería, y el público se entregó por completo. 🎸🎶 #PearlJam #ConciertoInolvidable""".trimMargin()


    val comments = listOf<Comment>(
        Comment(madescoces, cachengued, comentarioPearlJam, 5.0)
    )

    //        "GrandRexComment1" to Comment(
//            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//            "Que divertido estuvo, la pase re bien con mis amigos.",
//            4.0
//        ),
//
//        "GrandRexComment2" to Comment(
//            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//            "genial!!! Ame!! A parte los chicos re copados, al finalizar el show se quedaron para darnos autografos! <3",
//            5.0
//        ),
//
//        "GrandRexComment3" to Comment(
//            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//            "Buenardo. Fue el regalo de cumple de mi pareja y quede re bien.",
//            5.0
//        ),
//
//        "GrandRexComment4" to Comment(
//            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//            "Estuvo safable",
//            3.0
//        ),
//        "RiverComment1" to Comment(
//            showBoostrap.shows["PearlJam_River"]!!,
//            "¡Espectáculo impresionante! Los artistas dieron lo mejor de sí mismos en el escenario, creando una experiencia inolvidable para el público.",
//            4.0
//        ),
//
//        "RiverComment2" to Comment(
//            showBoostrap.shows["PearlJam_River"]!!,
//            "Una noche llena de energía y emoción. La combinación perfecta de música, luces y talento hizo que este show fuera realmente especial.",
//            5.0
//        ),
//
//        "RiverComment3" to Comment(
//            showBoostrap.shows["PearlJam_River"]!!,
//            "Increíblemente entretenido. Desde el momento en que comenzó hasta el último bis, cada momento estuvo lleno de sorpresas y diversión.",
//            5.0
//        ),
//
//        "RiverComment4" to Comment(
//            showBoostrap.shows["PearlJam_River"]!!,
//            "¡No te lo pierdas! Este show es una obra maestra de creatividad y habilidad. Una experiencia que te dejará con ganas de más.",
//            4.0
//        ),
//        "ColonComments" to Comment(
//            showBoostrap.shows["LaVelaPuerca_TeatroColon"]!!,
//            "Una mezcla perfecta de nostalgia y modernidad. Este show combina clásicos atemporales con elementos frescos e innovadores, atrayendo a audiencias de todas las edades.",
//            4.0
//        ),
//    )
    private fun addComments() {
        comments.forEach {
            commentRepository.save(it)
            println("Comment ${it.id} created")
        }
    }

    override fun afterPropertiesSet() {
        println("Comment creation process starts")
        addComments()
    }
}

//    showBoostrap: ShowBoostrap,
//    userBoostrap: UserBoostrap,
//) : InitializingBean {
//    val ticket1 = Ticket(
//        showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//        showBoostrap.shows["LaVelaPuerca_GranRex"]!!.dates.first(),
//        StadiumSeatType.UPPERLEVEL,
//        showBoostrap.shows["LaVelaPuerca_GranRex"]!!.ticketPrice(StadiumSeatType.UPPERLEVEL)
//    )
//    val ticket2 = Ticket(
//        showBoostrap.shows["PearlJam_River"]!!,
//        showBoostrap.shows["PearlJam_River"]!!.dates.first(),
//        StadiumSeatType.UPPERLEVEL,
//        showBoostrap.shows["PearlJam_River"]!!.ticketPrice(StadiumSeatType.UPPERLEVEL)
//    )
//    val ticket3 = Ticket(
//        showBoostrap.shows["LaVelaPuerca_TeatroColon"]!!,
//        showBoostrap.shows["LaVelaPuerca_TeatroColon"]!!.dates.first(),
//        StadiumSeatType.UPPERLEVEL,
//        showBoostrap.shows["LaVelaPuerca_GranRex"]!!.ticketPrice(StadiumSeatType.UPPERLEVEL)
//    )
//
//    val sol = userBoostrap.users["Sol"]!!
//    val pablo = userBoostrap.users["Pablo"]!!
//    val juan = userBoostrap.users["Juan"]!!
//    val denise = userBoostrap.users["Denise"]!!
//
//    val commentMap = mapOf(
//        "GrandRexComment1" to Comment(
//            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//            "Que divertido estuvo, la pase re bien con mis amigos.",
//            4.0
//        ),
//
//        "GrandRexComment2" to Comment(
//            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//            "genial!!! Ame!! A parte los chicos re copados, al finalizar el show se quedaron para darnos autografos! <3",
//            5.0
//        ),
//
//        "GrandRexComment3" to Comment(
//            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//            "Buenardo. Fue el regalo de cumple de mi pareja y quede re bien.",
//            5.0
//        ),
//
//        "GrandRexComment4" to Comment(
//            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
//            "Estuvo safable",
//            3.0
//        ),
//        "RiverComment1" to Comment(
//            showBoostrap.shows["PearlJam_River"]!!,
//            "¡Espectáculo impresionante! Los artistas dieron lo mejor de sí mismos en el escenario, creando una experiencia inolvidable para el público.",
//            4.0
//        ),
//
//        "RiverComment2" to Comment(
//            showBoostrap.shows["PearlJam_River"]!!,
//            "Una noche llena de energía y emoción. La combinación perfecta de música, luces y talento hizo que este show fuera realmente especial.",
//            5.0
//        ),
//
//        "RiverComment3" to Comment(
//            showBoostrap.shows["PearlJam_River"]!!,
//            "Increíblemente entretenido. Desde el momento en que comenzó hasta el último bis, cada momento estuvo lleno de sorpresas y diversión.",
//            5.0
//        ),
//
//        "RiverComment4" to Comment(
//            showBoostrap.shows["PearlJam_River"]!!,
//            "¡No te lo pierdas! Este show es una obra maestra de creatividad y habilidad. Una experiencia que te dejará con ganas de más.",
//            4.0
//        ),
//        "ColonComments" to Comment(
//            showBoostrap.shows["LaVelaPuerca_TeatroColon"]!!,
//            "Una mezcla perfecta de nostalgia y modernidad. Este show combina clásicos atemporales con elementos frescos e innovadores, atrayendo a audiencias de todas las edades.",
//            4.0
//        ),
//    )
//
//    fun addComments() {
//        sol.addComment(commentMap["GrandRexComment1"]!!, ticket1.show)
//        pablo.addComment(commentMap["GrandRexComment2"]!!, ticket1.show)
//        juan.addComment(commentMap["GrandRexComment3"]!!, ticket1.show)
//        denise.addComment(commentMap["GrandRexComment4"]!!, ticket1.show)
//        sol.addComment(commentMap["RiverComment1"]!!, ticket2.show)
//        pablo.addComment(commentMap["RiverComment2"]!!, ticket2.show)
//        juan.addComment(commentMap["RiverComment3"]!!, ticket2.show)
//        denise.addComment(commentMap["RiverComment4"]!!, ticket2.show)
//        pablo.addComment(commentMap["ColonComments"]!!, ticket3.show)
//
//    }
//
//    override fun afterPropertiesSet() {
//        println("Comment creation process starts")
//        addComments()
//        println("Comment creation process ends")
//    }
//
//}