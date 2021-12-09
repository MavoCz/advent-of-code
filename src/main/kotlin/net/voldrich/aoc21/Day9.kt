package net.voldrich.aoc21

// https://adventofcode.com/2021/day/9/
fun main() {
    Day9().run()
}

class Day9 : BaseDay() {

    override fun task1() : Int {
        val inputNums = input.lines().map { it.trim().toCharArray().map { it.digitToInt() }.toList() }
        val heightMap = HeightMap(inputNums)
        return heightMap.getLowPoints().sumOf { it.value + 1 }
    }

    override fun task2() : Int {
        val inputNums = input.lines().map { it.trim().toCharArray().map { it.digitToInt() }.toList() }
        val heightMap = HeightMap(inputNums)
        val basins = heightMap.getLowPoints().map { heightMap.findBasin(it) }.sortedDescending()

        return basins.take(3).fold(1) { v, prev -> v * prev }
    }

    class HeightMap(val heightMap: List<List<Int>>) {
        val sizey = heightMap.size
        val sizex = heightMap[0].size

        fun getLowPoints() : List<Point> {
            val lowPoints = ArrayList<Point>()
            for (y in 0 until sizey) {
                for (x in 0 until sizex) {
                    val num = heightMap[y][x]
                    var isLocalMin = true;
                    if (y > 0 && num >= heightMap[y-1][x]) isLocalMin = false
                    if (y+1 < sizey  && num >= heightMap[y+1][x]) isLocalMin = false
                    if (x > 0 && num >= heightMap[y][x-1]) isLocalMin = false
                    if (x+1 < sizex && num >= heightMap[y][x+1]) isLocalMin = false

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

        fun expandBasin(startPoint: Point, basin : HashSet<Point>) {
            if (startPoint.y > 0 ) considerBasinPoint(startPoint.y - 1, startPoint.x, startPoint, basin)
            if (startPoint.y+1 < sizey ) considerBasinPoint(startPoint.y + 1, startPoint.x, startPoint, basin)
            if (startPoint.x > 0 ) considerBasinPoint(startPoint.y, startPoint.x - 1, startPoint, basin)
            if (startPoint.x+1 < sizex ) considerBasinPoint(startPoint.y, startPoint.x + 1, startPoint, basin)
        }

        private fun considerBasinPoint(y: Int, x: Int, prevPoint: Point, basin : HashSet<Point>) {
            val point = Point(y, x, heightMap[y][x])

            if (basin.contains(point) || point.value <= prevPoint.value || point.value == 9) return

            basin.add(point)
            expandBasin(point, basin)
        }
    }

    data class Point(val y: Int, val x: Int, val value: Int)
}
