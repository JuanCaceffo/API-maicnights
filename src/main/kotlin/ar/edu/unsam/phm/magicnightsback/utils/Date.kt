package ar.edu.unsam.phm.magicnightsback.utils

//import ar.edu.unsam.phm.magicnights.exceptions.CreationError
//import ar.edu.unsam.phm.magicnights.exceptions.IllegalArgumentException
import java.time.LocalDateTime

fun LocalDateTime.validateIsNotBefore(): LocalDateTime {
    if (this.isBefore(LocalDateTime.now())) {
        throw IllegalArgumentException("CreationError.DATE_NOT_PASSED")
    }
    return this
}

fun fromNow(minutes: Int) = System.currentTimeMillis() + minutes * 60 * 1000