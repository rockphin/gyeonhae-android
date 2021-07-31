package us.gijuno.gyeonhae.data.mappers

interface Mapper<T, R> {
    fun map(obj: T): R
}
