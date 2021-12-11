package net.voldrich.aoc21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test {

    @Test
    fun testExample() {
        val day = Day11()
        day.input = TestInput("""
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """, true)
        assertEquals(1656, day.task1())
        assertEquals(195, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day11()
        assertEquals(1649, day.task1())
        assertEquals(256, day.task2())
    }
}