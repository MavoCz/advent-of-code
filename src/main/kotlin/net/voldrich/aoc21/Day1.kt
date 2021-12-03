package net.voldrich.aoc21

// https://adventofcode.com/2021/day/1
fun main() {
    Day1().run()
}

class Day1 : BaseDay() {

    val elevations: IntArray

    init {
        this.elevations = inputLines
            .map { it.toInt() }
            .toIntArray()
    }

    override fun task1() {
        var incrementCount = 0

        for (i in 1 until elevations.size) {
            if (elevations[i-1] < elevations[i]) {
                incrementCount++
            }
        }

        println("Increments: $incrementCount")
    }

    override fun task2() {
        var incrementCount = 0
        var lastAggregatedElevation = getAggregate(elevations,0)
        for (i in 1 until elevations.size - 2) {
            val elev = getAggregate(elevations, i)

            if (lastAggregatedElevation < elev) {
                incrementCount++
            }
            lastAggregatedElevation = elev
        }

        println("Increments: $incrementCount")
    }

    private fun getAggregate(elevations: IntArray, i: Int) = elevations[i] + elevations[i+1] + elevations[i+2]
}

