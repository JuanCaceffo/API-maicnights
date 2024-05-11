package ar.edu.unsam.phm.magicnights.utils

import kotlin.reflect.KClass


fun Any.getClassName(): String = this::class.simpleName ?: "anonymous class"

fun KClass<*>.stringMe(): String {
    return this.simpleName ?: "anonymous class"
}