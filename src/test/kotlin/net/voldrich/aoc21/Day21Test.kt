package net.voldrich.aoc21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigInteger

internal class Day21Test {
    @Test
    fun testExample() {
        val day = Day21()

        assertEquals(739785, day.playSimpleGame(4, 8))
        assertEquals(BigInteger("444356092776315"), day.playUniverseGame(4, 8))

    }

    @Test
    fun testReal() {
        val day = Day21()
        assertEquals(678468, day.task1())
        assertEquals(BigInteger("131180774190079"), day.task2())
    }
}