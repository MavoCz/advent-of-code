package net.voldrich.aoc2021

import net.voldrich.BaseDay

// https://adventofcode.com/2021/day/9/
fun main() {
    Day9().run()
}

class Day9 : BaseDay() {

    override fun task1() : Int {
        val inputNums = input.lines().map { line -> line.trim().toCharArray().map { it.digitToInt() }.toList() }
        val heightMap = HeightMap(inputNums)
        return heightMap.getLowPoints().sumOf { it.value + 1 }
    }

    override fun task2() : Int {
        val inputNums = input.lines().map { line -> line.trim().toCharArray().map { it.digitToInt() }.toList() }
        val heightMap = HeightMap(inputNums)
        val basins = heightMap.getLowPoints().map { heightMap.findBasin(it) }.sortedDescending()

        return basins.take(3).fold(1) { v, prev -> v * prev }
    }

    class HeightMap(private val heightMap: List<List<Int>>) {
        private val sx = heightMap.size
        private val sy = heightMap[0].size

        fun getLowPoints() : List<Point> {
            val lowPoints = ArrayList<Point>()
            for (y in 0 until sx) {
                for (x in 0 until sy) {
                    val num = heightMap[y][x]
                    var isLocalMin = true
                    if (y > 0 && num >= heightMap[y-1][x]) isLocalMin = false
                    if (y+1 < sx  && num >= heightMap[y+1][x]) isLocalMin = false
                    if (x > 0 && num >= heightMap[y][x-1]) isLocalMin = false
                    if (x+1 < sy && num >= heightMap[y][x+1]) isLocalMin = false

                    if (isLocalMin) {
                        lowPoints.add(Point(y, x, num))
                    }
                }
            }
            return lowPoints
        }

        fun findBasin(point: Point): Int {
            val basin = HashSet<Point>()
            basin.add(point)
            expandBasin(point, basin)

            return basin.size
        }

        private fun expandBasin(startPoint : Point, basin : HashSet<Point>) {
            if (startPoint.y > 0 ) considerBasinPoint(startPoint.y - 1, startPoint.x, startPoint, basin)
            if (startPoint.y+1 < sx ) considerBasinPoint(startPoint.y + 1, startPoint.x, startPoint, basin)
            if (startPoint.x > 0 ) considerBasinPoint(startPoint.y, startPoint.x - 1, startPoint, basin)
            if (startPoint.x+1 < sy ) considerBasinPoint(startPoint.y, startPoint.x + 1, startPoint, basin)
        }

        private fun considerBasinPoint(y : Int, x : Int, prevPoint : Point, basin : HashSet<Point>) {
            val point = Point(y, x, heightMap[y][x])

            if (basin.contains(point) || point.value <= prevPoint.value || point.value == 9) return

            basin.add(point)
            expandBasin(point, basin)
        }
    }

    data class Point(val y: Int, val x: Int, val value: Int)
}
