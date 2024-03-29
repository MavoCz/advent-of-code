package net.voldrich.aoc2021

import net.voldrich.BaseDay
import net.voldrich.minMax

// https://adventofcode.com/2021/day/14
fun main() {
    Day14().run()
}

class Day14 : BaseDay() {
    override fun task1() : Long {
        val polymer = Polymer()
        for (i in (1 .. 10)) polymer.multiply()
        val (min, max) = polymer.getCharCount().values.minMax { it }
        return max - min
    }

    override fun task2() : Long {
        val polymer = Polymer()
        for (i in (1 .. 40)) polymer.multiply()
        val (min, max) = polymer.getCharCount().values.minMax { it }
        return max - min
    }

    inner class Polymer {
        private val seed = input.lines()[0]
        private val polymerCounts = HashMap<String, Long>()
        private val pairInsertions = HashMap<String, Pair<String, String>>()

        init {
            for (i in (1 until seed.length)) {
                addPolymerCount(seed.substring(i-1, i+1), 1)
            }
            val regex = Regex("([A-Z]+) -> ([A-Z])")
            for ( i in 2 until input.lines().size) {
                val matches = regex.find(input.lines()[i])!!
                val key = matches.groups[1]!!.value
                val middle = matches.groups[2]!!.value

                pairInsertions[key] = Pair(key.substring(0, 1) + middle, middle + key.substring(1, 2))
            }
        }

        private fun addPolymerCount(polymer: String, count: Long) {
            polymerCounts.compute(polymer) { _, value -> (value ?: 0L) + count }
        }

        fun multiply() {
            // get list of changes in the polymer counts
            // we can not apply them directly to the same map as that would impact the analysis of current loop
            val changes = pairInsertions.flatMap { (polymer, pair) ->
                if (polymerCounts.containsKey(polymer)) {
                    val count = polymerCounts[polymer]!!
                    listOf(Pair(pair.first, count),
                        Pair(pair.second, count),
                        Pair(polymer, -count))
                } else {
                    listOf()
                }
            }

            changes.forEach { (polymer, count) -> addPolymerCount(polymer, count) }
        }

        fun getCharCount() : Map<Char, Long> {
            val countMap = HashMap<Char, Long>()
            polymerCounts.entries.forEach { (key, value) ->
                key.toCharArray().forEach { char -> countMap.compute(char) { _, charValue -> (charValue ?: 0L) + value } }
            }

            // Each character is represented twice in neighbour elements
            countMap.forEach {(key, value) ->
                countMap[key] = value / 2
            }

            // add the edge characters as they are not duplicated
            countMap[seed.first()] = countMap[seed.first()]!! + 1
            countMap[seed.last()] = countMap[seed.last()]!! + 1

            return countMap
        }
    }


}
