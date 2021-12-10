package net.voldrich.aoc21

import java.util.*

// https://adventofcode.com/2021/day/10
fun main() {
    Day10().run()
}

class Day10 : BaseDay() {

    override fun task1() : Int {
        val list = input.lines().map { analyzeLine(it) }
        return list.filter { it.type == Type.CORRUPTED }.sumOf { it.getCharValue() }
    }

    override fun task2() : Long {
        val list = input.lines().map { analyzeLine(it) }
        val sorted = list.filter { it.type == Type.INCOMPLETE }.map { it.calculateBracketScore() }.sorted()
        return sorted[sorted.size/2]
    }

    private val bracketMap = mapOf(
        '(' to ')',
        '<' to '>',
        '{' to '}',
        '[' to ']')

    private val valueMap = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137)

    private val scoreMap = mapOf(
        '(' to 1L,
        '[' to 2L,
        '{' to 3L,
        '<' to 4L)

    private fun analyzeLine(line: String) : LineStatus {
        val bracketStack = ArrayDeque<Char>()

        for ((index, ch) in line.toCharArray().withIndex()) {
            if (bracketMap.contains(ch)) {
                bracketStack.push(ch)
            } else {
                val expected = bracketStack.poll() ?: return LineStatus(Type.CORRUPTED, ch, bracketStack)
                val closingBracket = bracketMap[expected] ?: throw Exception("Failed to find closing bracket for $expected on $index")
                if (ch != closingBracket) {
                    return LineStatus(Type.CORRUPTED, ch, bracketStack)
                }
            }
        }
        // incomplete or ok
        return if (bracketStack.size > 0) {
            LineStatus(Type.INCOMPLETE, ' ', bracketStack)
        } else {
            LineStatus(Type.OK, ' ', bracketStack)
        }
    }

    enum class Type {
        OK, INCOMPLETE, CORRUPTED
    }

    inner class LineStatus(val type: Type, private val char : Char, private val remainingBracketStack : ArrayDeque<Char>) {
        fun getCharValue() : Int {
            return valueMap[char] ?: throw Exception("Unmapped character $char value")
        }

        fun calculateBracketScore() : Long {
            return remainingBracketStack.fold(0L) { prev, ch -> prev * 5 + scoreMap[ch]!! }
        }
    }
}
