package net.voldrich.aoc2021

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day17Test {
    @Test
    fun testExample() {
        val day = Day17()
        day.input = TestInput("""x=20..30, y=-10..-5""", false)
        assertEquals(45, day.task1())
        assertEquals(112, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day17()
        assertEquals(5050, day.task1())
        assertEquals(2223, day.task2())
    }
}