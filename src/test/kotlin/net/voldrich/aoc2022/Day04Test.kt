package net.voldrich.aoc2022

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun testExample() {
        val day = Day04()
        day.input = TestInput("""
        2-4,6-8
        2-3,4-5
        5-7,7-9
        2-8,3-7
        6-6,4-6
        2-6,4-8
        """, true)
        assertEquals(2, day.task1())
        assertEquals(4, day.task2())
    }
}