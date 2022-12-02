package net.voldrich.aoc2021

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day15Test {
    @Test
    fun testExample() {
        val day = Day15()
        day.input = TestInput("""
            1163751742
            1381373672
            2136511328
            3694931569
            7463417111
            1319128137
            1359912421
            3125421639
            1293138521
            2311944581
        """, true)
        assertEquals(40, day.task1())
        assertEquals(315, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day15()
        assertEquals(429, day.task1())
        assertEquals(2844, day.task2())
    }

    @Test
    fun performanceTest() {
        val day = Day15()

        day.performanceTest(100)
    }
}