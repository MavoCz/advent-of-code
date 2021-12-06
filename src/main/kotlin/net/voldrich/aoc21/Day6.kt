package net.voldrich.aoc21

import java.math.BigInteger

// https://adventofcode.com/2021/day/6
fun main() {
    Day6().run()
}

class Day6 : BaseDay {

    constructor() : super()

    constructor(lines: List<String>) : super(lines)

    override fun task1() {
        val result = procreateFish(80);
        println("fish count: $result")
    }

    override fun task2() {
        val result = procreateFish(256);
        println("fish count: $result")
    }

    fun procreateFish(dayCount : Int) : BigInteger {
        val fishList = inputLines[0].split(",").map { it.toInt() }.toList()

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
