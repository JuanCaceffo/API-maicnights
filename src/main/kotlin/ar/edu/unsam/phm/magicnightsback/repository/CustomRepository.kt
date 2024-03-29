package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.error.NotFoundException
import ar.edu.unsam.phm.magicnightsback.error.RepositoryError
import ar.edu.unsam.phm.magicnightsback.serializers.View
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.stereotype.Repository

abstract class Iterable {
    @JsonView(View.Iterable::class)
    var id: Long? = null
    fun id(newId: Long) {
        id = newId
    }

    abstract fun validSearchCondition(value: String): Boolean
}

@Repository
class CustomRepository<T : Iterable> {
    var elements = mutableMapOf<Long?, T>()
    var idCounter : Long = 0

    fun clear() {
        elements.clear()
        idCounter = 0
    }

    fun getAll(): List<T> = elements.values.toList()

    fun getTotalEntities(): Int = this.getAll().size

    fun create(element: T): T {
        element.id(idCounter)
        elements[element.id] = element
        idCounter += 1
        return element
    }

    fun delete(element: T) {
        elements.remove(element.id)
    }

    fun massiveDelete(elements: Map<Int, T>) {
        elements.forEach { this.delete(it.value) }
    }

    fun update(updatedElement: T) {
        elements[updatedElement.id] = updatedElement
    }

    fun getById(id: Long): T {
        return elements[id] ?: throw NotFoundException(RepositoryError.ID_NOT_FOUND)
    }

    fun search(value: String): List<T> {
        return elements.values.filter { it.validSearchCondition(value) }
    }
}
