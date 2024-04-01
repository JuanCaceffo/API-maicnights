package ar.edu.unsam.phm.magicnightsback.helpers

fun String.removeSpaces(): String {
    return this.trim().replace("\\s+".toRegex(), "")
}