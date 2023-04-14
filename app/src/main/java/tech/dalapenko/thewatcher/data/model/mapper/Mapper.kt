package tech.dalapenko.thewatcher.data.model.mapper

abstract class Mapper<SRC, DST> {

    abstract fun map(source: SRC): DST

    fun mapToList(sourceList: List<SRC>): List<DST> {
        return sourceList.map(this::map)
    }
}