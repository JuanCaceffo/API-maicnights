package ar.edu.unsam.phm.magicnightsback.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

object UserError {
    const val MSG_NOT_ENOUGH_CREDIT = "No tiene saldo suficiente"
}

object showError {
    const val MSG_SETS_UNAVILABLES = "Excedio la cantidad de asientos disponibles"
}

object RepositoryError {
    const val ID_NOT_FOUND = "El ID no corresponde con ningún elemento del repositorio"
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