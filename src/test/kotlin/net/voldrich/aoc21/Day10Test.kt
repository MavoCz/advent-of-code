package net.voldrich.aoc21

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day10Test {
    @Test
    fun testExample() {
        val day = Day10()
        day.input = TestInput("""
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
        """, true)
        assertEquals(26397, day.task1())
        assertEquals(288957, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day10()
        assertEquals(266301, day.task1())
        assertEquals(3404870164, day.task2())
    }
}