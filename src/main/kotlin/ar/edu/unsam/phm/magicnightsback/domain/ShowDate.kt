package ar.edu.unsam.phm.magicnightsback.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime


@Document("ShowDates")
data class ShowDate(
    val show: Show,
    val date: LocalDateTime
) {
    @Id
    lateinit var id: String

    // Availability
    fun beenStaged(): Boolean = date.isBefore(LocalDateTime.now())
}