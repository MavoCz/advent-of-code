package net.voldrich.aoc2022

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day08Test {

    @Test
    fun testExample() {
        val day = Day08()
        day.input = TestInput("""
        30373
        25512
        65332
        33549
        35390
        """, true)
        assertEquals(21, day.task1())
        assertEquals(8, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day08()
        assertEquals(1859, day.task1())
        assertEquals(332640, day.task2())
    }
}
