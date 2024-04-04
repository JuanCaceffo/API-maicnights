package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.error.NotFoundException
import ar.edu.unsam.phm.magicnightsback.error.RepositoryError
import org.springframework.stereotype.Repository

abstract class Iterable {
    var id: Long = 0
    fun id(newId: Long) {
        id = newId
    }
}

@Repository
class CustomRepository<T : Iterable> {
    var elements = mutableMapOf<Long, T>()
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
}
