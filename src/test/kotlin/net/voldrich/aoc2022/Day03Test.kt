package net.voldrich.aoc2022

import net.voldrich.TestInput
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day03Test {

    @Test
    fun testCharToPriority() {
        val day = Day03()
        println('A'.code)
        println('a'.code)
        println('a'.code - 96)
        println('z'.code - 96)
        println('A'.code - 65 + 27)
        println('Z'.code - 65 + 27)
        assertEquals(1, day.charToPriority('a'))
        assertEquals(26, day.charToPriority('z'))
        assertEquals(27, day.charToPriority('A'))
        assertEquals(52, day.charToPriority('Z'))
    }

    @Test
    fun testExample() {
        val day = Day03()
        day.input = TestInput("""
        vJrwpWtwJgWrhcsFMMfFFhFp
        jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
        PmmdzqPrVvPwwTWBwg
        wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
        ttgJtRGJQctTZtZT
        CrZsJsPPZsGzwwsLwLmpwMDw
        """, true)
        assertEquals(157, day.task1())
        assertEquals(70, day.task2())
    }
}