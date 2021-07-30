package us.gijuno.gyeonhae.data.mappers

interface EntityMapper<T, R> {
    fun fromObject(obj: R): T
    fun toObject(entity: T): R
}
