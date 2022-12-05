package net.voldrich

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day00Test {

    @Test
    fun testExample() {
        val day = Day00()
        day.input = TestInput("""
        123
        """, true)
        assertEquals(0, day.task1())
        assertEquals(0, day.task2())
    }
}