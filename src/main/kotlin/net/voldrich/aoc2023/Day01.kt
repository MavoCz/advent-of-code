package net.voldrich.aoc2023

import net.voldrich.BaseDay

// https://adventofcode.com/2023/day/1
fun main() {
    Day01().run()
}

// Written mostly by copilot
class Day01 : BaseDay() {

    override fun task1() : Int {
        return input.lines().filter { it.isNotBlank() }.sumOf { line ->
            val digits = line.filter { it.isDigit() }
            val first = Character.getNumericValue(digits.first())
            val last = Character.getNumericValue(digits.last())
            first * 10 + last
        }
    }

    private val numbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9
    )

    override fun task2() : Int {
        return input.lines().filter { it.isNotBlank() }.sumOf { line ->
            val first = line.findAnyOf(numbers.keys)?.second?.let { numbers.getOrDefault(it, 0) } ?: 0
            val last = line.findLastAnyOf(numbers.keys)?.second?.let { numbers.getOrDefault(it, 0) } ?: 0
            first * 10 + last
        }
    }

}

