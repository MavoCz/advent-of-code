package net.voldrich.aoc21

// link to task
fun main() {
    DayN().run()
}

private class DayN : BaseDay() {
    override fun task1() : Int {
        input.lines().forEach { println(it) }
        return input.lines().count()
    }

    override fun task2() : Int {
        return 0
    }
}
