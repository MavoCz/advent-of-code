package net.voldrich.aoc2022

import net.voldrich.BaseDay

// https://adventofcode.com/2022/day/4
fun main() {
    Day04().run()
}


class Day04 : BaseDay() {

    override fun task1() : Int {
        return input.lines().map(::parse).map { (a,b) ->
             contains(a, b) || contains(b, a)
        }.map { if (it) 1 else 0 }.sum()
    }

    override fun task2() : Int {
        return input.lines().map(::parse).sumOf { (a, b) ->
            overlap(a, b)
        }
    }

    // proposed by copilot (part_
    private fun overlap(a: IntRange, b: IntRange): Int {
        for (i in a) {
            if (i in b) {
                return 1
            }
        }
        return 0
    }

    // proposed by copilot
    private fun contains(range: IntRange, range2: IntRange): Boolean {
        return range.first <= range2.first && range.last >= range2.last
    }

    private fun parse(line: String): Pair<IntRange, IntRange> {
        val (a1, a2, b1, b2) = line.split(",", "-").map { it.toInt() }
        return Pair(a1..a2, b1..b2)
    }
}

