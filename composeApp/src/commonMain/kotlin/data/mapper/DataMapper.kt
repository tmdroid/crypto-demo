package data.mapper

interface DataMapper<INPUT, OUTPUT> {
    fun map(input: INPUT): OUTPUT
}