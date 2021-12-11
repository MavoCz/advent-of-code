package net.voldrich.aoc21

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.test.assertEquals

internal class Day6Test {

    @Test
    fun testExample() {
        val day = Day6()
        day.input = TestInput("3,4,3,1,2")
        Assertions.assertEquals(BigInteger.valueOf(26), day.procreateFish(18))
    }

    @Test
    fun testReal() {
        val day = Day6()
        Assertions.assertEquals(BigInteger.valueOf(366057), day.task1())
        Assertions.assertEquals(BigInteger("1653559299811"), day.task2())
    }
}