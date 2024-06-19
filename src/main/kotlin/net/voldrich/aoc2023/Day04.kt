package net.voldrich.aoc2023

import net.voldrich.BaseDay

// https://adventofcode.com/2023/day/4
fun main() {
    Day04().run()
}


class Day04 : BaseDay() {

    override fun task1() : Int {
       return input.lines()
           .filter { it.isNotBlank() }
           .map { parseLine(it) }
           .map { it.leftNumbers.intersect(it.rightNumbers).size }
           .map { (1 .. it).reduce { acc, i ->  i * 2} }
           .sumOf { it }
    }

    override fun task2() : Int {
        return 0
    }

    data class Card(val cardNumber: Int, val leftNumbers: Set<Int>, val rightNumbers: Set<Int>)

    fun parseLine(line: String): Card {
        val parts = line.split("|", ":")
        val cardNumberParts = parts[0].trim().split("\\s+".toRegex())
        val cardNumber = cardNumberParts[1].toInt()
        val leftNumbers = parts[1].trim().split("\\s+".toRegex()).drop(2).map { it.toInt() }.toSet()
        val rightNumbers = parts[2].trim().split("\\s+".toRegex()).map { it.toInt() }.toSet()
        return Card(cardNumber, leftNumbers, rightNumbers)
    }

}

