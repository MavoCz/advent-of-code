package net.voldrich.aoc2021

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day14Test {
    @Test
    fun testExample() {
        val day = Day14()
        day.input = TestInput("""
            NNCB

            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
        """, true)
        assertEquals(1588L, day.task1())
        assertEquals(2188189693529L, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day14()
        assertEquals(3118L, day.task1())
        assertEquals(4332887448171L, day.task2())
    }
}