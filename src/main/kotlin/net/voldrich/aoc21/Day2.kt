package net.voldrich.aoc21

// https://adventofcode.com/2021/day/2
fun main() {
    Day2().run()
}

class Day2 : BaseDay() {

    override fun task1() {
        var depth = 0;
        var forward = 0;
        parseInstructions().forEach {
            when (it.instruction) {
                "forward" -> forward += it.value
                "up" -> depth -= it.value
                "down" -> depth += it.value
            }
        }

        println("forward ${forward}, depth $depth, multiplied ${forward * depth}")
    }

    override fun task2() {
        var aim = 0;
        var depth = 0;
        var forward = 0;
        parseInstructions().forEach {
            when (it.instruction) {
                "forward" -> {
                    forward += it.value
                    depth += aim * it.value
                }
                "up" -> aim -= it.value
                "down" -> aim += it.value
            }
        }

        println("forward ${forward}, depth $depth, multiplied ${forward * depth}")
    }

    data class Instruction (val instruction: String, val value: Int)

    private fun parseInstructions(): List<Instruction> {
        val regex = Regex("([a-z]+) ([0-9]+)")
        return inputLines
            .map { it.lowercase().trim() }
            .map { parseInstruction(it, regex) }
            .toList()
    }

    private fun parseInstruction(str: String, regex: Regex): Instruction {
        val matches = regex.find(str) ?: throw Exception("No match for $str");
        val instruction = matches.groups.get(1)?.value
        val num = matches.groups.get(2)?.value?.toInt()
        if (instruction == null || num == null) {
            throw Exception("No match for $str")
        }
        return Instruction(instruction, num);
    }
}




