package nl.mranderson.rijks.data.mapper

/**
 * This interface is used as guidance for transforming response data to domain models
 */
interface Mapper<T, S> {
    fun map(data: T): S
}