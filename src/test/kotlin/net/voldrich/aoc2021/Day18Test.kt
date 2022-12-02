package net.voldrich.aoc2021

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day18Test {
    @Test
    fun testExample() {
        val day = Day18()
        day.input = TestInput("""
            [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
            [[[5,[2,8]],4],[5,[[9,9],0]]]
            [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
            [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
            [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
            [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
            [[[[5,4],[7,7]],8],[[8,3],8]]
            [[9,3],[[9,9],[6,[4,9]]]]
            [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
            [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]            
        """.trimIndent(), true)
        assertEquals(4140, day.task1())
        assertEquals(3993, day.task2())
    }

    @Test
    fun testVarious() {
        val day = Day18()
        val snailNumber = day.parseSnailNumber("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]")
        val snailNumber2 = day.parseSnailNumber("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]")
        val snailNumber3 = snailNumber.add(snailNumber2)
        println(snailNumber3.toString())
    }

    @Test
    fun testVarious2() {
        val day = Day18()
        day.input = TestInput("""
            [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
            [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
            [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
            [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
            [7,[5,[[3,8],[1,4]]]]
            [[2,[2,2]],[8,[8,1]]]
            [2,9]
            [1,[[[9,3],9],[[9,0],[0,7]]]]
            [[[5,[7,4]],7],1]
            [[[[4,2],2],6],[8,7]]
        """.trimIndent(), true)
        assertEquals(3488, day.task1())
    }

    @Test
    fun testReal() {
        val day = Day18()
        assertEquals(3675, day.task1())
        assertEquals(4650, day.task2())
    }
}