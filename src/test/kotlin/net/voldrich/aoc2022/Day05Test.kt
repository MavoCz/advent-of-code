package net.voldrich.aoc2022

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day05Test {

    @Test
    fun testExample() {
        val day = Day05()
        day.input = TestInput("""
            [D]    
        [N] [C]    
        [Z] [M] [P]
         1   2   3 
        
        move 1 from 2 to 1
        move 3 from 1 to 3
        move 2 from 2 to 1
        move 1 from 1 to 2
        """, true)
        assertEquals("CMZ", day.task1())
        assertEquals("MCD", day.task2())
    }
}