package com.arch.utils.mapper

/**
 * Implementation of the LayerDataTransformer.
 * Extend this abstract class to any of the entity classes<F> and provide their domain counterpart<T> as well.
 * override transform() and/or restore() and to convert them
 *
 * <pre>
 *   data class PersonEntity(var name: String):
 *          BaseLayerDataTransformer<PersonEntity, Person>() {
 *
 *      override fun transform(): Person {
 *          return Person(name)
 *       }
 *
 *      override fun restore(data: Person): PersonEntity {
 *          return PersonEntity(data.name)
 *      }
 *
 *  }
 * </pre>
 * transform() Usage : (Entity class must override transform() function)
 * var person:Person = personEntity.transform()
 *
 * restore() Usage : (Entity class must override restore() function)
 * var personEntity:PersonEntity = PersonEntity().restore(person)
 *
 */

abstract class BaseLayerDataTransformer<F, T> : LayerDataTransformer<F, T> {
    override fun transform(from: Collection<F>): Collection<T> {
        val transformed: MutableList<T> = ArrayList(from.size)
        for (fromObject in from) {
            transformed.add(transform())
        }
        return transformed
    }

    override fun restore(from: Collection<T>): Collection<F> {
        val transformed: MutableList<F> = ArrayList(from.size)
        for (fromObject in from) {
            transformed.add(restore(fromObject))
        }
        return transformed
    }

    override fun transform(): T {
        TODO("Not yet implemented. Default implementation should throw this as an exception when transform() is called without overriding the function")
    }

    override fun restore(data: T): F {
        TODO("Not yet implemented. Default implementation should throw this as an exception when restore() is called without overriding the function")
    }
}
