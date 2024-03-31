package ar.edu.unsam.phm.magicnightsback.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

object UserError {
    const val MSG_NOT_ENOUGH_CREDIT = "No tiene saldo suficiente"
    const val BAD_CREDENTIALS = "El usuario o la contraseña no son validos"
    const val USER_NOT_AUTHORIZED_CREATE_DATE = "El usuario debe ser administrador para crear una nueva funcion"

}

object showError {
    const val MSG_SETS_UNAVILABLES = "Excedio la cantidad de asientos disponibles"
    const val MSG_DATE_NOT_PASSED = "La fecha del Show todavia no paso"
}

object RepositoryError {
    const val ID_NOT_FOUND = "El ID no corresponde con ningún elemento del repositorio"
}

object FacilityError {
    const val INVALID_SEAT_TYPE = "El tipo de asiento ingresado no es valido para este tipo de instalacion"
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BusinessException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
class IllegalArgumentException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
class NotImplementedError(msg: String) : RuntimeException(msg)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthenticationException(msg: String) : RuntimeException(msg)
