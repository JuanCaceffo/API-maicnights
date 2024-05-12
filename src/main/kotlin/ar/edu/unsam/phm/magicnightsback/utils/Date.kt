package ar.edu.unsam.phm.magicnightsback.utils

//import ar.edu.unsam.phm.magicnights.exceptions.CreationError
//import ar.edu.unsam.phm.magicnights.exceptions.IllegalArgumentException

fun fromNow(minutes: Int) = System.currentTimeMillis() + minutes * 60 * 1000