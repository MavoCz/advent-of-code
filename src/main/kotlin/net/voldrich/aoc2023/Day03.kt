package net.voldrich.aoc2023

import net.voldrich.BaseDay

// https://adventofcode.com/2023/day/3
fun main() {
    Day03().run()
}

class Day03 : BaseDay() {

    val numberPattern = "\\d+".toRegex()
    val specialCharPattern = "[^.\\d]".toRegex()

    data class Line(val index: Int, val numbers: List<LineNum>, val specialChars: List<Pair<Int, String>>)

    data class LineNum(val start: Int, val end: Int, val value: Int)

    override fun task1(): Int {
        val parsed = parseLines()

        return parsed.flatMap { line ->
            line.numbers.filter { findSpecialSymbol(parsed, line.index, it) }
        }.sumOf { it.value }
    }

    override fun task2(): Int {
        val parsed = parseLines()
        return parsed.flatMap { line ->
            line.specialChars.filter { it.second == "*" }.map { calculateGearNum(parsed, line.index, it) }
        }.sumOf { it }
    }

    private fun calculateGearNum(parsed: List<Line>, index: Int, gear: Pair<Int, String>): Int {
        val gearNums = collectNearbyLines(index, parsed).flatMap { it.numbers }
            .filter { gear.first in (it.start - 1..it.end + 1) }
            .map { it.value }
            .toList()

        return if (gearNums.size > 1) {
            gearNums.reduce { a, b -> a * b }
        } else {
            0
        }
    }

    private fun parseLines() = input.lines().filter { it.isNotBlank() }.mapIndexed { index, line ->
        val numbers = numberPattern.findAll(line)
            .map { LineNum(it.range.start, it.range.endInclusive, it.value.toInt()) }
            .toList()
        val specialChars = specialCharPattern.findAll(line)
            .map { it.range.start to it.value }
            .toList()
        Line(index, numbers, specialChars)
    }.toList()

    private fun findSpecialSymbol(parsed: List<Line>, index: Int, number: LineNum): Boolean {
        return collectNearbyLines(index, parsed).flatMap { it.specialChars }
            .any { it.first in (number.start - 1..number.end + 1) }
    }

    private fun collectNearbyLines(index: Int, parsed: List<Line>): List<Line> {
        val lines = mutableListOf<Line>()
        if (index - 1 >= 0) {
            lines.add(parsed.get(index - 1))
        }
        lines.add(parsed.get(index))

        if (index + 1 < parsed.size) {
            lines.add(parsed.get(index + 1))
        }
        return lines
    }
}

