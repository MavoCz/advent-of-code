package net.voldrich.aoc2022

import net.voldrich.BaseDay
import java.util.*
import kotlin.collections.ArrayList

// https://adventofcode.com/2022/day/5
fun main() {
    Day05().run()
}


class Day05 : BaseDay() {

    val instructionRegex = Regex("move ([0-9]+) from ([0-9]+) to ([0-9]+)")

    override fun task1() : String {
        val separator = input.getEmptyLineIndex()

        val queues = parseQueues(separator)

        for (i in separator + 1 until input.lines().size) {
            val (count, from, to) = parseInstruction(i)
            for (j in 0 until count) {
                queues[to-1].addFirst(queues[from - 1].pop())
            }
        }

        return queues.map { it.first }.joinToString("")
    }

    private fun parseQueues(separator: Int): ArrayList<LinkedList<Char>> {
        val indexes = input.lines()[separator - 1].mapIndexed { index, c ->
            if (c != ' ') {
                index
            } else {
                -1
            }
        }.filter { it != -1 }

        val queues = ArrayList<LinkedList<Char>>(indexes.size)

        for (i in indexes.indices) {
            queues.add(LinkedList())
        }
        for (i in 0 until separator - 1) {
            val line = input.lines()[i]
            for (q in indexes.indices) {
                val j = indexes[q]
                if (j < line.length && line[j] != ' ') {
                    queues[q].add(line[j])
                }
            }
        }
        return queues
    }

    override fun task2() : String {
        val separator = input.getEmptyLineIndex()

        val queues = parseQueues(separator)

        for (i in separator + 1 until input.lines().size) {
            val (count, from, to) = parseInstruction(i)

            val holder = LinkedList<Char>()
            for (j in 0 until count) {
                holder.addFirst(queues[from - 1].pop())
            }
            for (j in 0 until count) {
                queues[to-1].addFirst(holder.pop())
            }
        }

        return queues.map { it.first }.joinToString("")
    }

    private fun parseInstruction(i: Int): Triple<Int, Int, Int> {
        val line = input.lines()[i]
        val matches = instructionRegex.find(line)!!
        val count = matches.groupValues[1].toInt()
        val from = matches.groupValues[2].toInt()
        val to = matches.groupValues[3].toInt()
        return Triple(count, from, to)
    }
}

