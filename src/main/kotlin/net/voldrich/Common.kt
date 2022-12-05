package net.voldrich

interface InputLoader {
    fun lines() : List<String>

    fun getEmptyLineIndex() : Int {
        return lines().indexOfFirst { it.isBlank() }
    }

    fun getFirstLineAsIntList(): List<Int> {
        return lines()[0].split(",").map { it.toInt() }.toList()
    }
}

class TestInput(private val parsedLines: List<String>) : InputLoader {

    constructor(line: String, isMultiline: Boolean = false)
            : this(if (isMultiline) line.trimIndent().split("\n") else listOf(line))

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
            //.map { it.trim() } // needed for windows removal of \r
    }
}

abstract class BaseDay() {

    var input : InputLoader

    init {
        val yearDir = this.javaClass.packageName.substringAfterLast('.')
        val fileName = this.javaClass.simpleName.lowercase()
        val resourcePath = "/$yearDir/$fileName-input.txt"
        println("Loading input from $resourcePath")
        input = ResourceInput(resourcePath)
    }

    fun run() {
        println("Result of task1: ${task1()}")
        println()
        println("Result of task2: ${task2()}")
    }

    abstract fun task1() : Any

    abstract fun task2() : Any

}

fun String.binaryString(): String {
    return toCharArray().joinToString("") { it.digitToInt(16).toString(2).padStart(4, '0') }
}

fun String.decodeHex(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }

    val byteIterator = chunkedSequence(2)
        .map { it.toInt(16).toByte() }
        .iterator()

    return ByteArray(length / 2) { byteIterator.next() }
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