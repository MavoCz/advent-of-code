package net.voldrich.aoc21

interface InputLoader {
    fun lines() : List<String>

    fun getFirstLineAsIntList(): List<Int> {
        return lines()[0].split(",").map { it.toInt() }.toList()
    }
}

class TestInput(private val parsedLines: List<String>) : InputLoader {

    constructor(line: String, isMultiline: Boolean = false)
            : this(if (isMultiline) line.trim().split("\n") else listOf(line.trim()))

    override fun lines(): List<String> = parsedLines
}

class ResourceInput(private val resourcePath: String) : InputLoader {

    private lateinit var parsedLines : List<String>

    override fun lines() : List<String> {
        if (!this::parsedLines.isInitialized) {
            parsedLines = getResourceAsLines(resourcePath)
        }
        return parsedLines
    }

    private fun getResourceAsText(path: String): String {
        val resource = this.javaClass.getResource(path) ?: throw Exception("Resource was not found: $path")
        return resource.readText()
    }

    private fun getResourceAsLines(path: String): List<String> {
        return getResourceAsText(path)
            .split("\n")
            .map { it.trim() } // needed for windows removal of \r
    }
}

abstract class BaseDay {
    var input : InputLoader = ResourceInput("/${this.javaClass.simpleName.lowercase()}-input.txt")

    fun run() {
        println("Result of task1: ${task1()}")
        println()
        println("Result of task2: ${task2()}")
    }

    abstract fun task1() : Any

    abstract fun task2() : Any

}

inline fun <T, R : Comparable<R>> Iterable<T>.minMax(selector: (T) -> R): Pair<R,R> {
    val iterator = iterator()
    if (!iterator.hasNext()) throw NoSuchElementException()
    var minValue = selector(iterator.next())
    var maxValue = minValue
    while (iterator.hasNext()) {
        val v = selector(iterator.next())
        if (minValue > v) {
            minValue = v
        }
        if (maxValue < v) {
            maxValue = v
        }
    }
    return Pair(minValue, maxValue)
}