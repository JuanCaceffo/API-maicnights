package ar.edu.unsam.phm.magicnightsback.utils

fun String.removeSpaces(): String {
    return this.trim().replace("\\s+|/".toRegex(), "")
}