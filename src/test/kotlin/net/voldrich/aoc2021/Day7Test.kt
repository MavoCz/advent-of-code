package net.voldrich.aoc2021

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day7Test {
    @Test
    fun testExample() {
        val day = Day7()
        day.input = TestInput("16,1,2,0,4,2,7,1,2,14")
        assertEquals(37, day.task1())
        assertEquals(168, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day7()
        assertEquals(348996, day.task1())
        assertEquals(98231647, day.task2())
    }
}