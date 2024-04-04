package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.StadiumSeatType
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(4)
@DependsOn("showBoostrap", "userBoostrap")
class CommentBoostrap(
    showBoostrap: ShowBoostrap,
    userBoostrap: UserBoostrap,
) : InitializingBean {

    val ticket = Ticket(showBoostrap.shows["LaVelaPuerca_GranRex"]!!, showBoostrap.shows["LaVelaPuerca_GranRex"]!!.dates.first() ,StadiumSeatType.UPPERLEVEL, showBoostrap.shows["LaVelaPuerca_GranRex"]!!.ticketPrice(StadiumSeatType.UPPERLEVEL))

    val sol = userBoostrap.users["Sol"]!!
    val pablo = userBoostrap.users["Pablo"]!!
    val juan = userBoostrap.users["Juan"]!!
    val denise = userBoostrap.users["Denise"]!!

    val commentMap = mapOf(
        "BigShowComment1" to Comment(
            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
            "Que divertido estuvo, la pase re bien con mis amigos.",
            4.0),

        "BigShowComment2" to Comment(
            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
            "genial!!! Ame!! A parte los chicos re copados, al finalizar el show se quedaron para darnos autografos! <3",
            5.0),

        "BigShowComment3" to Comment(
            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
            "Buenardo. Fue el regalo de cumple de mi pareja y quede re bien.",
            5.0),

        "BigShowComment4" to Comment(
            showBoostrap.shows["LaVelaPuerca_GranRex"]!!,
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