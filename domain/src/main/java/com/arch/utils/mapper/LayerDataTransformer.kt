package com.arch.utils.mapper

/**
 * A data transformer interface which is used to enable entity transformation between layers.
 * transform() function can be used to convert data type <F> to <T>
 * restore() function can be used convert <T> back to <F>
 */
interface LayerDataTransformer<F, T> {
    fun transform(): T
    fun transform(from: Collection<F>): Collection<T>
    fun restore(data: T): F
    fun restore(from: Collection<T>): Collection<F>
}
