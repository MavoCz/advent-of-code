package net.voldrich.aoc2022

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Test {

    @Test
    fun testExample() {
        val day = Day06()
        day.input = TestInput("mjqjpqmgbljsphdztnvjfqwrcgsmlb", false)
        assertEquals(7, day.task1())
        assertEquals(19, day.task2())
    }
}