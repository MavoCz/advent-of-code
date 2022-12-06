package net.voldrich.aoc2022

import net.voldrich.BaseDay

// https://adventofcode.com/2022/day/3
fun main() {
    Day03().run()
}

// Written mostly by copilot
class Day03 : BaseDay() {

    override fun task1() : Int {
        return input.lines().map { line ->
            val (left, right) = splitStringIntoHalf(line)
            val leftCharMap = stringToCharacterMap(left)
            val rightCharMap = stringToCharacterMap(right)

            val commonChars = leftCharMap.keys.intersect(rightCharMap.keys)

            commonChars.map{charToPriority(it)}.sum()
        }.sum()
    }

    override fun task2() : Int {
        return input.lines().chunked(3).map { group ->
            val (left, middle, right) = group
            val leftCharMap = stringToCharacterMap(left)
            val middleCharMap = stringToCharacterMap(middle)
            val rightCharMap = stringToCharacterMap(right)

            val intersected = leftCharMap.keys
                .intersect(middleCharMap.keys)
                .intersect(rightCharMap.keys)

            charToPriority(intersected.first())
        }.sum()
    }

    fun charToPriority(char: Char) : Int {
        if (char.code < 'a'.code) {
            return char.code  - 65 + 27
        } else {
            return char.code - 96
        }
    }

    // proposed by copilot
    fun splitStringIntoHalf(string: String): Pair<String, String> {
        val half = string.length / 2
        return Pair(string.substring(0, half), string.substring(half))
    }

    // proposed by copilot
    fun stringToCharacterMap(string: String): Map<Char, Int> {
        return string.groupingBy { it }.eachCount()
    }
}

