package net.voldrich.aoc21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day13Test {
    @Test
    fun testExample() {
        val day = Day13()
        day.input = TestInput("""
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
            
            fold along y=7
            fold along x=5
        """, true)
        assertEquals(17, day.task1())
        assertEquals(16, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day13()
        assertEquals(710, day.task1())
        assertEquals(97, day.task2())
    }
}