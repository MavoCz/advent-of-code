package net.voldrich.aoc21

import kotlin.math.abs

// https://adventofcode.com/2021/day/7
fun main() {
    Day7().run()
}

class Day7 : BaseDay() {

    override fun task1() : Int {
        return minimizeFuel { value, pos -> abs(pos - value) }
    }

    override fun task2() : Int {
        return minimizeFuel { value, pos ->
            val distance = abs(pos - value)
            (1..distance).sum()
        }
    }

    private fun minimizeFuel(fuelFnc: (Int, Int) -> Int): Int {
        val horizontalPositions = input.getFirstLineAsIntList()

        var min = Int.MAX_VALUE
        var max = Int.MIN_VALUE
        var sum = 0
        horizontalPositions.forEach {
            if (it < min) min = it
            if (it > max) max = it
            sum += it
        }

        val avg = sum/horizontalPositions.size

        println("min $min, max $max, sum $sum, avg $avg")

        return (min..max).minOf { pos -> horizontalPositions.sumOf { value -> fuelFnc.invoke(value, pos) } }
    }

}
