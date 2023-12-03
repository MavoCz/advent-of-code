package net.voldrich.aoc2023

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
    fun testExample() {
        val day = Day03()
        day.input = TestInput("""
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
        """, true)
        assertEquals(4361, day.task1())
        assertEquals(467835, day.task2())
    }
}
