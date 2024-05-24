package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ShowDateDTO(
    val id: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date: LocalDateTime = LocalDateTime.MIN
)

data class ShowDateRequest(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val date: LocalDateTime,
    var showId: Long,
    var userId: Long
)

fun ShowDate.toDTO() = ShowDateDTO(
    id = this.id,
    date = this.date,
)