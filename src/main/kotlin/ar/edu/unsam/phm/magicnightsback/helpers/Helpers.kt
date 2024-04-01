package ar.edu.unsam.phm.magicnightsback.helpers

import ar.edu.unsam.phm.magicnightsback.domain.TheaterSeatType

fun String.removeSpaces(): String {
    return this.trim().replace("\\s+".toRegex(), "")
}