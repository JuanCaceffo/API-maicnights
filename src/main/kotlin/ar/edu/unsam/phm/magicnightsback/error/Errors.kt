package ar.edu.unsam.phm.magicnightsback.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

object UserError {
    const val MSG_NOT_ENOUGH_CREDIT = "No tiene saldo suficiente para realizar la compra"
    const val BAD_CREDENTIALS = "El usuario o la contraseña no son validos"
    const val USER_IS_NOT_ADMIN = "Usuario sin privilegios"
    const val NONEXISTENT_USER_COMMENT = "El comentario que intenta eliminar no existe"
}

object CommentError {
    const val INVALID_RATTING = "El rating debe estar entre 1 y 5"
    const val SHOWDATE_NOT_PASSED = "No se puede agregar una opinion a un showdate si este no ocurrió"
    const val INVALID_DELETE = "No se puede borrar un comentario que no le pertenece"
}

object showError {
    const val BAND_ERROR = "El show debe tener una banda"
    const val FACILITY_ERROR = "El show debe tener un lugar"
    const val USER_CANT_COMMENT = "El usaurio no puede comentar el show"
    const val MSG_SETS_UNAVILABLES = "Excedio la cantidad de asientos disponibles"
    const val TICKET_CART_NOT_FOUND = "No se encontró la fecha en el show especificado"
}
object ShowDateError{
    const val EXCEEDED_CAPACITY = "Excedio la capacidad maxima de asientos para esta ubicacion"
    const val MSG_DATE_NOT_FOUND = "La fecha que intento buscar no existe"
    const val DATE_ALREADY_EXISTS = "La fecha que desea agregar ya existe"
    const val NEW_SHOW_INVALID_CONDITIONS = "Las condiciones no permiten agregar una nueva fecha para el show"

}
object RepositoryError {
    const val ID_NOT_FOUND = "El ID no corresponde con ningún elemento del repositorio"
    const val ELEMENT_NOT_FOUND = "Elemento no encontrado"
}

object FacilityError {
    const val INVALID_SEAT_TYPE = "El tipo de asiento ingresado no es valido para este tipo de instalacion"
    const val NEGATIVE_CAPACITY = "El área no puede tener una cantidad de asientos negativa"
    const val NEGATIVE_PRICE = "El precio no puede ser un valor negativo"
}

object cartError {
    const val CART_FOR_USER_NOT_FOUND = "El carrito para el usaurio especificado no fue encontrado"
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

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerError(msg: String) : RuntimeException(msg)