package net.voldrich.aoc2022

import net.voldrich.BaseDay

// https://adventofcode.com/2022/day/6
fun main() {
    Day06().run()
}

class Day06 : BaseDay() {

    override fun task1() : Int {
        val uniques = findUnique4CharactersNextToEachOther(input.lines().first(), 4)
        return uniques.first().second
    }

    override fun task2() : Int {
        val uniques = findUnique4CharactersNextToEachOther(input.lines().first(), 14)
        return uniques.first().second
    }

    fun findUnique4CharactersNextToEachOther(input: String, windowSize: Int): List<Pair<String, Int>> {
        return input
            .windowed(windowSize, 1)
            .mapIndexedNotNull { index, s ->
                if (s.toSet().size == windowSize) {
                    Pair(s, index + windowSize)
                } else {
                    null
                }
            }
    }



}

