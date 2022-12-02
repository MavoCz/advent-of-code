package net.voldrich.aoc2022

import net.voldrich.BaseDay

// https://adventofcode.com/2022/day/1
fun main() {
    Day2().run()
}

// Written mostly by copilot
class Day2 : BaseDay() {

    enum class Type {
        ROCK, PAPER, SCISSORS
    }

    override fun task1() : Int {
        return input.lines().map {
            val (val1, val2) = it.split(" ").take(2)
            val elfMove = mapElfMove(val1)
            val myMove = mapMyMove(val2)

            calculateScore(elfMove, myMove)
        }.sum()
    }

    override fun task2() : Int {
        return input.lines().map {
            val (val1, val2) = it.split(" ").take(2)
            val elfMove = mapElfMove(val1)
            val myMove = mapMyMove2(val2, elfMove)

            calculateScore(elfMove, myMove)
        }.sum()
    }

    private fun mapMyMove2(val2: String, elfMove: Type): Type {
        return when (val2) {
            // loose
            "X" -> when (elfMove) {
                Type.ROCK -> Type.SCISSORS
                Type.PAPER -> Type.ROCK
                Type.SCISSORS -> Type.PAPER
            }
            // draw
            "Y" -> when (elfMove) {
                Type.ROCK -> Type.ROCK
                Type.PAPER -> Type.PAPER
                Type.SCISSORS -> Type.SCISSORS
            }
            //win
            "Z" -> when (elfMove) {
                Type.ROCK -> Type.PAPER
                Type.PAPER -> Type.SCISSORS
                Type.SCISSORS -> Type.ROCK
            }
            else -> throw IllegalArgumentException("Unknown move $val2")
        }
    }

    private fun mapMyMove(val2: String) = when {
        val2 == "X" -> Type.ROCK
        val2 == "Y" -> Type.PAPER
        val2 == "Z" -> Type.SCISSORS
        else -> throw Exception("Unknown move $val2")
    }

    private fun mapElfMove(val1: String) = when {
        val1 == "A" -> Type.ROCK
        val1 == "B" -> Type.PAPER
        val1 == "C" -> Type.SCISSORS
        else -> throw Exception("Unknown elf move $val1")
    }

    private fun calculateScore(elfMove: Type, myMove: Type): Int {
        return when (elfMove) {
            Type.ROCK -> {
                when (myMove) {
                    Type.ROCK -> 1 + 3
                    Type.PAPER -> 2 + 6
                    Type.SCISSORS -> 3 + 0
                }
            }
            Type.PAPER -> {
                when (myMove) {
                    Type.ROCK -> 1 + 0
                    Type.PAPER -> 2 + 3
                    Type.SCISSORS -> 3 + 6
                }
            }
            Type.SCISSORS -> {
                when (myMove) {
                    Type.ROCK -> 1 + 6
                    Type.PAPER -> 2 + 0
                    Type.SCISSORS -> 3 + 3
                }
            }
        }
    }
}

