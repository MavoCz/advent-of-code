package net.voldrich.aoc2022

import net.voldrich.BaseDay

// https://adventofcode.com/2022/day/1
fun main() {
    Day1().run()
}

// Written mostly by copilot
class Day1 : BaseDay() {

    override fun task1() : Int {
        val groups = groupLinesSeparatedByEmptyLine(input.lines())

        return groups.map { calculateSumForList(it) }.maxOrNull() ?: 0
    }

    override fun task2() : Int {
        val groups = groupLinesSeparatedByEmptyLine(input.lines())

        return find3maxItemsInListAndSumThem(groups.map { calculateSumForList(it) })
    }

    fun find3maxItemsInListAndSumThem(list: List<Int>): Int {
        val sorted = list.sorted()
        return sorted[sorted.size - 1] + sorted[sorted.size - 2] + sorted[sorted.size - 3]
    }

    fun calculateSumForList (list: List<String>): Int {
        return list.map { it.toInt() }.sum()
    }

    fun groupLinesSeparatedByEmptyLine(lines: List<String>) : List<List<String>> {
        val groups = mutableListOf<List<String>>()
        var currentGroup = mutableListOf<String>()
        lines.forEach {
            if (it.isBlank()) {
                groups.add(currentGroup)
                currentGroup = mutableListOf()
            } else {
                currentGroup.add(it)
            }
        }
        if (currentGroup.isNotEmpty()) {
            groups.add(currentGroup)
        }
        return groups
    }
}

