package net.voldrich.aoc21

abstract class BaseDay {
    protected val inputLines : List<String>

    init {
        inputLines = getResourceAsLines("/${this.javaClass.simpleName.lowercase()}-input.txt")
    }

    fun run() {
        task1()
        task2()
    }

    abstract fun task1()

    abstract fun task2()

    fun getResourceAsText(path: String): String {
        val resource = this.javaClass.getResource(path) ?: throw Exception("Resource was not found: $path")
        return resource.readText()
    }

    fun getResourceAsLines(path: String): List<String> {
        return getResourceAsText(path)
            .split("\n")
            .map { it.trim() } // needed for windows removal of \r
    }
}