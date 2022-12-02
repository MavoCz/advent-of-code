package net.voldrich.aoc2021

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day12Test {
    @Test
    fun testExample() {
        val day = Day12()
        day.input = TestInput("""
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
        """, true)
        assertEquals(10, day.task1())
        assertEquals(36, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day12()
        assertEquals(4885, day.task1())
        assertEquals(117095, day.task2())
    }
}