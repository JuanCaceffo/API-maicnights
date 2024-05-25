package ar.edu.unsam.phm.magicnightsback.utils

fun String.removeSpaces(): String {
    return this.trim().replace("\\s+|/".toRegex(), "")
}

object Comparar {
    fun total(buscado: String, listaComparar: List<String>, caseSense: Boolean = true) =
        listaComparar.any { it.equals(buscado, ignoreCase = caseSense) }

    fun parcial(busquedoParcial: String, listaComparar: List<String>, caseSense: Boolean = true) =
        listaComparar.any { it.contains(busquedoParcial, ignoreCase = caseSense) }
}