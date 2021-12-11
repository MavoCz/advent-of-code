package net.voldrich.aoc21

import kotlin.math.max
import kotlin.math.min

// link to task
fun main() {
    Day11().run()
}

class Day11 : BaseDay() {
    override fun task1() : Int {
        val inputNums = input.lines().map { line -> line.trim().toCharArray().map { it.digitToInt() }.toMutableList() }.toMutableList()

        val grid = OctopusGrid(inputNums)
        return (1..100).sumOf { grid.simulateFlashStep() }
    }

    override fun task2() : Int {
        val inputNums = input.lines().map { line -> line.trim().toCharArray().map { it.digitToInt() }.toMutableList() }.toMutableList()

        val grid = OctopusGrid(inputNums)

        var step = 1
        while (grid.simulateFlashStep() != grid.totalOctopusCount()) {
            step++
        }

        return step
    }

    class OctopusGrid(private val grid: MutableList<MutableList<Int>>) {
        private val sx = grid.size
        private val sy = grid[0].size

        fun simulateFlashStep() : Int {
            val flashedPoints = HashSet<Point>()
            for (y in 0 until sx) {
                for (x in 0 until sy) {
                    testFlash(y,x, flashedPoints)
                }
            }

            flashedPoints.forEach {
                grid[it.y][it.x] = 0
            }

            return flashedPoints.size
        }

        private fun testFlash(y: Int, x : Int, flashedPoints : HashSet<Point>) {
            val p = Point(y,x)
            if (flashedPoints.contains(p)) {
                return
            }
            grid[y][x] += 1
            if (grid[y][x] > 9) {
                flash(p, flashedPoints)
            }
        }

        private fun flash(point: Point, flashedPoints : HashSet<Point>) {
            flashedPoints.add(point)

            for (y in max(point.y - 1, 0) .. min(point.y + 1, sy - 1)) {
                for (x in max(point.x - 1, 0) .. min(point.x + 1, sx - 1)) {
                    testFlash(y,x, flashedPoints)
                }
            }
        }

        fun totalOctopusCount(): Int {
            return sx * sy
        }
    }

    data class Point(val y: Int, val x: Int)
}
