package net.voldrich.aoc2021

import net.voldrich.BaseDay
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

// https://adventofcode.com/2021/day/5
// result 5632
// result 22213
fun main() {
    Day5().run()
}

private class Day5 : BaseDay() {

    override fun task1() : Int {
        return calculateDangerPoints { ventLine ->
            ventLine is HorizontalLine || ventLine is VerticalLine
        }
    }

    override fun task2() : Int {
        return calculateDangerPoints { true } // calculate all lines
    }

    fun calculateDangerPoints(lineFilter: (VentLine) -> Boolean): Int {
        val lines = input.lines().mapNotNull(this::parseLine).filter(lineFilter).toList()

        val maxx = lines.maxOf { max(it.start.x, it.end.x) }
        val maxy = lines.maxOf { max(it.start.y, it.end.y) }

        val grid = Array(maxy + 1) { IntArray(maxx + 1) }
        lines.forEach { it.render(grid) }

        return grid.flatMap { row -> row.filter { it > 1 } }.size
    }

    val lineRegex = Regex("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)")

    fun parseLine(line: String): VentLine? {
        val matches = lineRegex.find(line) ?: throw Exception("Failed to parse vent line $line")

        fun getIntResult(mr: MatchGroup?) = mr?.value?.toInt() ?: throw Exception("Failed to parse vent line $line")

        val start = Point(getIntResult(matches.groups[1]), getIntResult(matches.groups[2]))
        val end = Point(getIntResult(matches.groups[3]), getIntResult(matches.groups[4]))

        if (start.x == end.x) {
            return VerticalLine(start, end)
        } else if (start.y == end.y) {
            return HorizontalLine(start, end)
        } else if (abs(start.x - end.x) == abs(start.y - end.y)) {
            return DiagonalLine(start, end)
        }

        return null
    }

    data class Point(val x: Int, val y: Int)

    abstract class VentLine(val start: Point, val end: Point) {
        abstract fun render(grid: Array<IntArray>)
    }

    class HorizontalLine(start: Point, end: Point) : VentLine(start, end) {
        override fun render(grid: Array<IntArray>) {
            for (x in min(start.x, end.x) .. max(start.x, end.x)) {
                grid[start.y][x] += 1
            }
        }
    }

    class VerticalLine(start: Point, end: Point) : VentLine(start, end) {
        override fun render(grid: Array<IntArray>) {
            for (y in min(start.y, end.y) .. max(start.y, end.y)) {
                grid[y][start.x] += 1
            }
        }
    }

    class DiagonalLine(start: Point, end: Point) : VentLine(start, end) {
        override fun render(grid: Array<IntArray>) {
            val dimensionsX = abs(start.x - end.x)

            var y = start.y
            val yIncrement = if (start.y < end.y) 1 else -1
            var x = start.x
            val xIncrement = if (start.x < end.x) 1 else -1
            grid[y][x] += 1
            for (i in 0 until dimensionsX) {
                y += yIncrement
                x += xIncrement
                grid[y][x] += 1
            }
        }
    }
}


