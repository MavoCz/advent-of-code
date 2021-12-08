package net.voldrich.aoc21

import java.math.BigInteger

// https://adventofcode.com/2021/day/6
fun main() {
    Day6().run()
}

class Day6 : BaseDay() {

    override fun task1() : Any {
        return procreateFish(80)
    }

    override fun task2() : Any {
        return procreateFish(256)
    }

    fun procreateFish(dayCount : Int) : BigInteger {
        val fishList = input.getFirstLineAsIntList()

        val dayIndex = (0..8).map { BigInteger.ZERO }.toMutableList()

        fishList.forEach { dayIndex[it] = dayIndex[it].inc() }

        for (day in 1 .. dayCount) {
            val fishCountToProcreate = dayIndex[0]

            for (i in 1 .. 8) {
                dayIndex[i-1] = dayIndex[i]
            }

            dayIndex[6] = dayIndex[6].add(fishCountToProcreate)
            dayIndex[8] = fishCountToProcreate
        }

        return dayIndex.fold(BigInteger.ZERO, BigInteger::add)
    }

}
