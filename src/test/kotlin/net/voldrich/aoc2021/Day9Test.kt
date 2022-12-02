package net.voldrich.aoc2021

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day9Test {
    @Test
    fun testExample() {
        val day = Day9()
        day.input = TestInput("""
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
        """, true)
        assertEquals(15, day.task1())
        assertEquals(1134, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day9()
        assertEquals(594, day.task1())
        assertEquals(858494, day.task2())
    }
}