package net.voldrich.aoc2023

import net.voldrich.BaseDay

// https://adventofcode.com/2023/day/2
fun main() {
    Day02().run()
}

class Day02 : BaseDay() {

    override fun task1(): Int {
        val marbleCounts = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )
        return input.lines()
            .filter { it.isNotBlank() }
            .map { parseGame(it) }
            .filter { hasAtLeast(it, marbleCounts) }
            .sumOf { it.number }
    }

    private fun hasAtLeast(game: Game, marbleCounts: Map<String, Int>): Boolean {
        for ((color, count) in game.colors) {
            if (marbleCounts.getOrDefault(color, 0) < count) {
                return false
            }
        }
        return true
    }

    override fun task2(): Int {
        return input.lines()
            .filter { it.isNotBlank() }
            .map { parseGame(it) }
            .map { findMaxPerColorPower(it) }
            .sumOf { it }
    }

    private fun findMaxPerColorPower(game: Game): Int {
        val max = mutableMapOf<String, Int>()
        for ((color, count) in game.colors) {
            if (!max.containsKey(color)) {
                max[color] = count
            } else if (max.getOrDefault(color,0) < count) {
                max[color] = count
            }
        }
        return max.values.reduce { a, b -> a * b }
    }

    data class Game(val number: Int, val colors: List<Pair<String, Int>>)

    fun parseGame(line: String): Game {
        val parts = line.split(": ", ", ", "; ")
        val colors = mutableListOf<Pair<String, Int>>()
        var gameNumber = 0

        for (i in parts.indices) {
            when {
                parts[i].startsWith("Game") -> gameNumber = parts[i].split(" ")[1].toInt()
                else -> {
                    val colorParts = parts[i].split(" ")
                    val color = colorParts[1].trim()
                    val count = colorParts[0].toInt()
                    colors.add(Pair(color, count))
                }
            }
        }

        return Game(gameNumber, colors)
    }
}

