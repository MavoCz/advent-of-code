package net.voldrich.aoc21

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

    override fun task1() {
        val dangerPointCount = calculateDangerPoints(listOf(
            HorizontalLineRenderer(),
            VerticalLineRenderer()
        ))
        println("result $dangerPointCount")
    }

    override fun task2() {
        val dangerPointCount = calculateDangerPoints(listOf(
            HorizontalLineRenderer(),
            VerticalLineRenderer(),
            DiagonalLineRenderer()
        ))
        println("result $dangerPointCount")
    }

    interface LineRenderer {
        fun renderLine(line: VentLine, grid: Array<IntArray>)
    }

    class HorizontalLineRenderer : LineRenderer {
        override fun renderLine(line: VentLine, grid: Array<IntArray>) {
            if (line.start.x == line.end.x) {
                for (y in min(line.start.y, line.end.y) .. max(line.start.y, line.end.y)) {
                    grid[y][line.start.x] += 1
                }
            }
        }
    }

    class VerticalLineRenderer : LineRenderer {
        override fun renderLine(line: VentLine, grid: Array<IntArray>) {
            if (line.start.y == line.end.y) {
                for (x in min(line.start.x, line.end.x) .. max(line.start.x, line.end.x)) {
                    grid[line.start.y][x] += 1
                }
            }
        }
    }

    class DiagonalLineRenderer : LineRenderer {
        override fun renderLine(line: VentLine, grid: Array<IntArray>) {
            val dimensionsX = abs(line.start.x - line.end.x)
            val dimensionsY = abs(line.start.y - line.end.y)
            if (dimensionsX == dimensionsY) {
                var y = line.start.y
                val yIncrement = if (line.start.y < line.end.y) 1 else -1
                var x = line.start.x
                val xIncrement = if (line.start.x < line.end.x) 1 else -1
                grid[y][x] += 1
                for (i in 0 until dimensionsX) {
                    y += yIncrement
                    x += xIncrement
                    grid[y][x] += 1
                }
            }
        }
    }

    fun calculateDangerPoints(lineParsers : List<LineRenderer>) : Int {
        val lines = inputLines.map { parseLine(it) }.toList()

        val maxx = lines.maxOf { max(it.start.x, it.end.x) }
        val maxy = lines.maxOf { max(it.start.y, it.end.y) }

        val grid = Array(maxy + 1) { IntArray(maxx + 1) }
        lines.forEach { line ->
            lineParsers.forEach { it.renderLine(line, grid) }
        }

        return grid.flatMap { it.filter { it > 1 } }.size
    }

    val lineRegex = Regex("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)")

    fun parseLine(line : String) : VentLine {
        val matches = lineRegex.find(line) ?: throw Exception("Failed to parse vent line $line")

        return VentLine(
            Point(
                matches.groups[1]?.value?.toInt() ?: throw Exception("Failed to parse vent line $line"),
                matches.groups[2]?.value?.toInt() ?: throw Exception("Failed to parse vent line $line")),
            Point(
                matches.groups[3]?.value?.toInt() ?: throw Exception("Failed to parse vent line $line"),
                matches.groups[4]?.value?.toInt() ?: throw Exception("Failed to parse vent line $line")),
        )
    }

    data class Point(val x: Int, val y: Int)

    data class VentLine(val start: Point, val end: Point)
}


