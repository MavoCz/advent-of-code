package net.voldrich.aoc21

import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.test.assertEquals

internal class Day6Test {

    @Test
    fun procreateFish() {
        val day = Day6()
        day.input = TestInput("3,4,3,1,2")

        val result = day.procreateFish(18)

        assertEquals(BigInteger.valueOf(26), result)

        val result80 = day.procreateFish(80)
        assertEquals(BigInteger.valueOf(5934), result80)


        val result256 = day.procreateFish(256)
        assertEquals(BigInteger("26984457539"), result256)

    }
}