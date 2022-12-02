package net.voldrich.aoc2021

import net.voldrich.BaseDay

// link to task
fun main() {
    Day13().run()
}

class Day13 : BaseDay() {
    override fun task1() : Int {
        val paper = PaperInstructions()

        return foldPaper(paper.foldInstructions.first(), paper.points.toSet()).size
    }

    override fun task2() : Int {
        val paper = PaperInstructions()
        val finalPoints = paper.foldInstructions.fold(paper.points.toSet()) { points, f -> foldPaper(f, points) }

        renderPointsAsAscii(finalPoints)

        // human reader output: "EPLGRULR"
        return finalPoints.size
    }

    private fun renderPointsAsAscii(points: Set<Point>) {
        val sx = points.maxOf { it.x }
        val sy = points.maxOf { it.y }

        // render points to screen, the dump but fast way
        for (y in (0..sy)) {
            for (x in (0..sx)) {
                if (points.contains(Point(y,x))) {
                    print("x")
                } else {
                    print(" ")
                }
            }
            println()
        }
    }

    private fun foldPaper(fold: FoldInstruction, points: Set<Point>) : Set<Point> {
        return if (fold.axis == "y") {
            points.filter { it.y < fold.value }.toSet().union(
                points.filter { it.y > fold.value }.map { Point(2*fold.value - it.y, it.x) }.toSet())
        } else {
            points.filter { it.x < fold.value }.toSet().union(
                points.filter { it.x > fold.value }.map { Point(it.y, 2*fold.value - it.x) }.toSet())
        }
    }

    inner class PaperInstructions {

        val points: List<Point>

        val foldInstructions: List<FoldInstruction>

        init {
            val groupBy = input.lines().groupBy { it.contains("fold along") }
            points = groupBy[false]!!.mapNotNull { line ->
                if (line.isNotBlank()) {
                    val (x, y) = line.split(",")
                    Point(y.toInt(), x.toInt())
                } else {
                    null
                }
            }
            val regex = Regex("fold along ([a-z])=([0-9]+)")
            foldInstructions = groupBy[true]!!.map { line ->
                val matches = regex.find(line)!!
                FoldInstruction(matches.groups[1]!!.value, matches.groups[2]!!.value.toInt())
            }
        }

        override fun toString(): String {
            return "DotGrid(points=$points, foldInstructions=$foldInstructions)"
        }
    }

    data class Point(val y : Int, val x: Int)

    data class FoldInstruction(val axis : String, val value: Int)
}
