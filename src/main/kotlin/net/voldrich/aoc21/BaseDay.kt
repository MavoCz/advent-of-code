package net.voldrich.aoc21

interface InputLoader {
    fun lines() : List<String>

    fun getFirstLineAsIntList(): List<Int> {
        return lines()[0].split(",").map { it.toInt() }.toList()
    }
}

class TestInput(private val lines: List<String>) : InputLoader {
    override fun lines(): List<String> = lines
}

class ResourceInput(resourcePath: String) : InputLoader {

    private val lines : List<String>

    init {
        lines = getResourceAsLines(resourcePath)
    }

    override fun lines() = lines

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