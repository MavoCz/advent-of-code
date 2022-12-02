package net.voldrich.aoc2021

import net.voldrich.BaseDay

// link to task
fun main() {
    DayN().run()
}

class DayN : BaseDay() {
    override fun task1() : Int {
        input.lines().forEach { println(it) }
        return input.lines().count()
    }

    override fun task2() : Int {
        return 0
    }
}
