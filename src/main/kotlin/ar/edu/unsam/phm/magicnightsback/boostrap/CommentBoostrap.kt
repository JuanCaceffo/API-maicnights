package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.StadiumSeatType
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@Order(4)
@DependsOn("showBoostrap", "userBoostrap")
class CommentBoostrap(
    showBoostrap: ShowBoostrap,
    userBoostrap: UserBoostrap,
) : InitializingBean {

    val ticket = Ticket(showBoostrap.shows["BigShow"]!!, showBoostrap.shows["BigShow"]!!.dates.first() ,StadiumSeatType.UPPERLEVEL)

    val sol = userBoostrap.users["Sol"]!!
    val pablo = userBoostrap.users["Pablo"]!!
    val juan = userBoostrap.users["Juan"]!!
    val denise = userBoostrap.users["Denise"]!!

    val commentMap = mapOf(
        "BigShowComment1" to Comment(
            showBoostrap.shows["BigShow"]!!,
            "Que divertido estuvo",
            4.0),

        "BigShowComment2" to Comment(
            showBoostrap.shows["BigShow"]!!,
            "genial!!!",
            5.0),

        "BigShowComment3" to Comment(
            showBoostrap.shows["BigShow"]!!,
            "Buenardo",
            5.0),

        "BigShowComment4" to Comment(
            showBoostrap.shows["BigShow"]!!,
            "Estuvo safable",
            3.0),
    )

    fun addComments() {
        sol.addComment(commentMap["BigShowComment1"]!!,ticket)
        pablo.addComment(commentMap["BigShowComment2"]!!,ticket)
        juan.addComment(commentMap["BigShowComment3"]!!,ticket)
        denise.addComment(commentMap["BigShowComment4"]!!,ticket)
    }

    override fun afterPropertiesSet() {
        println("Comment creation process starts")
        addComments()
        println("Comment creation process ends")
    }

}