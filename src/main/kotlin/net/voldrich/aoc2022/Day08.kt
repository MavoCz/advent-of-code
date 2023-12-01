package net.voldrich.aoc2022

import net.voldrich.BaseDay

// https://adventofcode.com/2022/day/7
fun main() {
    Day08().run()
}

class Day08 : BaseDay() {

    override fun task1() : Int {
        return countVisibleTrees(input.lines().filter { it.isNotBlank() })
    }

    override fun task2() : Int {
        return countViewIndex(input.lines().filter { it.isNotBlank() })
    }

    fun countViewIndex(grid: List<String>): Int {
        val size = grid.size
        val indexes = Array(size) { IntArray(size) { 0 } }
        val maxIndex = size - 1

        for (i in 1 until maxIndex) {
            for (j in 1 until maxIndex) {
                indexes[i][j] = calculateScenicIndex(grid, i, j)
            }
        }

        return indexes.maxOf { it.max() }
    }

    fun calculateScenicIndex(grid: List<String>, row: Int, col: Int) : Int {
        val height = grid[row][col]

        var scoreLeft = 0
        for (j in col-1 downTo 0) {
            scoreLeft +=1
            if (grid[row][j] >= height) {
                break
            }
        }

        var scoreRight = 0
        for (j in col+1 until grid.size) {
            scoreRight +=1
            if (grid[row][j] >= height) {
                break
            }
        }

        var scoreUp = 0
        for (i in row-1 downTo 0) {
            scoreUp +=1
            if (grid[i][col] >= height) {
                break
            }
        }

        var scoreDown = 0
        for (i in row + 1 until grid.size) {
            scoreDown +=1
            if (grid[i][col] >= height) {
                break
            }
        }

        return scoreLeft * scoreRight * scoreUp * scoreDown
    }

    fun countVisibleTrees(grid: List<String>): Int {
        val size = grid.size
        val maxIndex = size - 1

        val visible = Array(size) { IntArray(size) { 0 } }

        // Count all edge trees
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (i == 0 || i == maxIndex || j == 0 || j == maxIndex) {
                    visible[i][j] = 1
                }
            }
        }

        // Check each row
        for (i in 1 until maxIndex) {
            var maxLeft = grid[i][0]
            var maxRight = grid[i][maxIndex]
            for (j in 1 until maxIndex) {
                // From left to right
                if (grid[i][j] > maxLeft) {
                    maxLeft = grid[i][j]
                    visible[i][j] = 1
                }
                // From right to left
                if (grid[i][maxIndex - j] > maxRight) {
                    maxRight = grid[i][maxIndex - j]
                    visible[i][maxIndex - j] = 1
                }
            }
        }

        // Check each column
        for (j in 1 until maxIndex) {
            var maxTop = grid[0][j]
            var maxBottom = grid[maxIndex][j]
            for (i in 1 until maxIndex) {
                // From top to bottom
                if (grid[i][j] > maxTop) {
                    maxTop = grid[i][j]
                    visible[i][j] = 1
                }
                // From bottom to top
                if (grid[maxIndex - i][j] > maxBottom) {
                    maxBottom = grid[maxIndex - i][j]
                    visible[maxIndex - i][j] = 1
                }
            }
        }

        return visible.sumOf { it.sumOf { it } }
    }
}
