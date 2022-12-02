package net.voldrich.aoc2021

import net.voldrich.TestInput
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day20Test {
    @Test
    fun testExample() {
        val day = Day20()
        day.input = TestInput("""
            ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

            #..#.
            #....
            ##..#
            ..#..
            ..###
        """.trimIndent(), true)
        assertEquals(35, day.task1())
        assertEquals(3351, day.task2())
    }

    @Test
    fun testReal() {
        val day = Day20()
        assertEquals(5475, day.task1())
        assertEquals(17548, day.task2())
    }
}