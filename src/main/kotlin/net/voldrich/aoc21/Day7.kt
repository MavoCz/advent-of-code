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
        val (min, max) = horizontalPositions.minMax { it }
        return (min..max).minOf { pos -> horizontalPositions.sumOf { value -> fuelFnc.invoke(value, pos) } }
    }

}
