package net.voldrich.aoc21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day16Test {
    @Test
    fun testExample() {
        val day = Day16()
        day.input = TestInput("""A0016C880162017C3686B18A3D4780""", false)
        assertEquals(31, day.task1())
    }

    @Test
    fun testReal() {
        val day = Day16()
        assertEquals(963, day.task1())
        assertEquals(1549026292886L, day.task2())
    }
}