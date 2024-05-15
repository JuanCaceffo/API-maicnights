package ar.edu.unsam.phm.magicnightsback.exceptions

import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

object UserError {


    const val USER_IS_NOT_ADMIN = "Usuario sin privilegios"
    const val NONEXISTENT_USER_COMMENT = "El comentario que intenta eliminar no existe"
}

object CommentError {
    const val IS_NOT_ATTENDEE = "No puede opinar sobre un show al que nunca fue"
    const val INVALID_RATTING = "El rating debe estar entre 1 y 5"
    const val SHOWDATE_NOT_PASSED = "No se puede agregar una opinion a un showdate si este no ocurrió"
    const val INVALID_DELETE = "No se puede borrar un comentario que no le pertenece"
    const val SHOW_ALREADY_COMMENTED = "No se puede agregar mas de una opinion a un showdate"
}

object ShowError {
    const val BAND_ERROR = "El show debe tener una banda"
    const val FACILITY_ERROR = "El show debe tener un lugar"
    const val USER_CANT_COMMENT = "El usaurio no puede comentar el show"
    const val MSG_SETS_UNAVILABLES = "Excedio la cantidad de asientos disponibles"
    const val TICKET_CART_NOT_FOUND = "No se encontró la fecha en el show especificado"
}
object ShowDateError{
    const val EXCEEDED_CAPACITY = "Excedio la capacidad maxima de asientos para esta ubicacion"
    const val MSG_DATE_NOT_FOUND = "La fecha que intento buscar no existe"
    const val INVALID_DATE = "La fecha debe ser posterior a la actual"
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

object CartError {
    const val CART_FOR_USER_NOT_FOUND = "El carrito para el usaurio especificado no fue encontrado"
}

object FindError {
    @Suppress("FunctionName")
    fun NOT_FOUND(id: Long, name: String = "entity") = "Can't find $name with id: $id."

    @Suppress("FunctionName")
    fun INVALID_SEAT_TYPE(name: String = "facility") = "The seat type is not valid for a $name."

    @Suppress("FunctionName")
    fun NAME_NOT_FOUND(username: String, name: String = "entity") = "Can't find $name with value: $username."

    const val BAD_CREDENTIALS = "Invalid user credentials."

    const val ZERO_CAPACITY = "The seat capacity must be greater than zero."
}

object UpdateError {
    const val NEGATIVE_BALANCE = "User Balance Can't be negative"
}

object CreationError {
    @Suppress("FunctionName")
    fun CANNOT_CREATE(name: String = "entity") = "Can't create $name"

    const val DATE_NOT_PASSED = "The date has not been passed"
    const val ALREADY_PASSED = "The date has already been passed"
    const val NO_CAPACITY = "Not enough seat capacity"
    const val NEGATIVE_PRICE = "Fixed price can't be negative."
}

object ModifyError {
    const val NEGATIVE_BALANCE = "Not enough funds"
    @Suppress("FunctionName")
    fun CANNOT_MODIFY(uuid: UUID, name: String = "entity") = "Can't modify $name with id: $uuid"
}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResponseFindException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BusinessException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
class BadArgumentException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
class NotImplementedError(msg: String) : RuntimeException(msg)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthenticationException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class InternalServerError(msg: String) : RuntimeException(msg)