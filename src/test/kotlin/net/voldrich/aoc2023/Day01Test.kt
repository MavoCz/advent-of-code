package net.voldrich.aoc2023

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day01Test {

    @Test
    fun testExample() {
        val day = Day01()
        day.input = TestInput("""
        1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet
        """, true)
        assertEquals(142, day.task1())
    }

    @Test
    fun testExample2() {
        val day = Day01()
        day.input = TestInput("""
        two1nine
eightwothree
abcone2threexyz
xtwone3four
4nineeightseven2
zoneight234
7pqrstsixteen
        """, true)
        assertEquals(281, day.task2())
        //assertEquals(0, day.task2())
    }
}
