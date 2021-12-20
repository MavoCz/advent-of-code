package net.voldrich.aoc21

import kotlin.math.abs
import kotlin.math.max

// link to task
fun main() {
    Day19().run()
}

typealias ScFn = (Day19.ScannerPoint) -> Day19.ScannerPoint

class Day19 : BaseDay() {
    override fun task1() : Int {
        return ScannerAnalyzer().findTotalPoints()
    }

    override fun task2() : Int {
        return ScannerAnalyzer().findMaxManhattanDistance()
    }

    inner class ScannerAnalyzer() {

        val scanners = parseScannerPoints()

        val transformationList : List<ScannerTransformation>

        init {
            val trList = ArrayList<ScannerTransformation>()
            for (i in scanners) {
                for (j in scanners) {
                    if (i !== j) {
                        val transformation = findTransformation(i, j)
                        if (transformation != null) {
                            trList.add(transformation)
                        }
                    }
                }
            }

            trList.forEach {
                println("${it.from.index} ${it.to.index}")
            }

            transformationList = trList
        }

        fun findMaxManhattanDistance() : Int {
            val scannerCenterPoints = ArrayList<ScannerPoint>()
            val targetScanner = scanners[0]
            scannerCenterPoints.add(ScannerPoint(0,0,0))
            for (i in (1 until scanners.size)) {
                val from = scanners[i]
                val transformations = findTransitiveTransformationChain(from, targetScanner)

                val center = transformations[0].delta
                val centerTransformedTo0 = transformations.drop(1).fold(center) { point, tr ->
                    tr.scfnWithShift.invoke(point)
                }
                scannerCenterPoints.add(centerTransformedTo0)
            }

            var max = Int.MIN_VALUE
            for (i in scannerCenterPoints.indices) {
                for (j in scannerCenterPoints.indices) {
                    if (i > j) {
                        max = max(max, scannerCenterPoints[i].distanceTo(scannerCenterPoints[j]))
                    }
                }
            }

            return max
        }

        fun findTotalPoints() : Int {
            val resultPointSet = HashSet<ScannerPoint>()
            val targetScanner = scanners[0]
            resultPointSet.addAll(scanners[0].points)
            for (i in (1 until scanners.size)) {
                val from = scanners[i]
                val transformations = findTransitiveTransformationChain(from, targetScanner)

                println("scanner $i")
                val transformedTo0 = transformations.fold(from) { scanner, tr ->
                    println("${tr.from.index} ${tr.to.index}")
                    scanner.applyTransformation(tr.scfnWithShift)
                }

                resultPointSet.addAll(transformedTo0.points)
            }

            return resultPointSet.size
        }

        fun findTransitiveTransformationChain(from : Scanner, to : Scanner) : List<ScannerTransformation> {
            return findTransitiveTransformationChain(from, to, transformationList, emptySet())
                ?: throw Exception("Transformation chain not found to ${to.index}")
        }

        fun findTransitiveTransformationChain(from : Scanner, to : Scanner, trList : List<ScannerTransformation>, visited : Set<Scanner>) : List<ScannerTransformation>? {
            trList.filter { it.from === from && !visited.contains(it.to) }.forEach { tr ->
                if (tr.to === to) {
                    return listOf(tr)
                } else {
                    val transitive = findTransitiveTransformationChain(tr.to, to, trList, visited + setOf(from))
                    if (transitive != null) {
                        return listOf(tr) + transitive
                    }
                }
            }
            return null
        }

        fun findTransformation(from : Scanner, to: Scanner) : ScannerTransformation? {
            trOptions.forEach { scfn ->
                val s2Tr = from.applyTransformation(scfn)
                val deltaList = to.points.flatMap { s1point ->
                    s2Tr.points.map { s2point ->
                        s1point.delta(s2point, scfn)
                    }
                }.toList()

                // group the points by their delta
                val deltaMap = HashMap<ScannerPoint, MutableList<Delta>>()
                deltaList.forEach {
                    val list = deltaMap.computeIfAbsent(it.delta) { ArrayList() }
                    list.add(it)
                }

                // if transformation is correct, 12 points should match
                val match = deltaMap.values.filter { it.size >= 12 }.toList()
                if (match.size == 1) {
                    return ScannerTransformation(from, to, match[0])
                }
            }
            return null
        }

        fun parseScannerPoints() : ArrayList<Scanner> {
            val iterator = input.lines().iterator()

            val scannerList = ArrayList<Scanner>()

            while (iterator.hasNext()) {
                val scannerName = iterator.next()
                val pointList = ArrayList<ScannerPoint>()
                while (iterator.hasNext()) {
                    val line = iterator.next()
                    if (line.isBlank()) {
                        break
                    }
                    val points = line.split(",").map { it.toInt() }
                    pointList.add(ScannerPoint(points[0], points[1], points[2]))
                }
                scannerList.add(Scanner(scannerName, scannerList.size, pointList.toList()))
                pointList.clear()
            }

            return scannerList
        }
    }

    val trAxis = listOf<ScFn>(
        { point -> ScannerPoint(point.x, point.y, point.z) },
        { point -> ScannerPoint(point.x, point.z, point.y) },
        { point -> ScannerPoint(point.y, point.x, point.z) },
        { point -> ScannerPoint(point.y, point.z, point.x) },
        { point -> ScannerPoint(point.z, point.x, point.y) },
        { point -> ScannerPoint(point.z, point.y, point.x) },
    )

    val trOrientation = listOf<ScFn>(
        { point -> ScannerPoint(point.x, point.y, point.z) },
        { point -> ScannerPoint(-point.x, point.y, point.z) },
        { point -> ScannerPoint(point.x, -point.y, point.z) },
        { point -> ScannerPoint(point.x, point.y, -point.z) },
        { point -> ScannerPoint(-point.x, -point.y, point.z) },
        { point -> ScannerPoint(-point.x, point.y, -point.z) },
        { point -> ScannerPoint(point.x, -point.y, -point.z) },
        { point -> ScannerPoint(-point.x, -point.y, -point.z) },
    )

    val trOptions : List<ScFn>

    init {
        trOptions = trAxis.flatMap { axisFn -> trOrientation.map { orientFn -> {point : ScannerPoint -> axisFn.invoke(orientFn.invoke(point)) } } }.toList()
    }

    class Scanner(val name: String, val index : Int, val points: List<ScannerPoint>) {
        override fun toString(): String {
            return "$name \n${points.joinToString("\n")})\n"
        }

        fun applyTransformation(scfn: ScFn): Scanner {
            return Scanner(name, index, points.map(scfn).toList())
        }
    }

    data class ScannerPoint(val x : Int, val y : Int, val z: Int) {

        override fun toString(): String {
            return "$x,$y,$z"
        }

        fun delta(other: ScannerPoint, scfn: ScFn) : Delta {
            return Delta(ScannerPoint(this.x - other.x, this.y - other.y, this.z - other.z), this, other, scfn)
        }

        fun distanceTo(other: ScannerPoint): Int {
            return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
        }
    }

    data class Delta(val delta : ScannerPoint, val p1 : ScannerPoint, val p2 : ScannerPoint, val scfn: ScFn)

    data class ScannerTransformation(val from: Scanner, val to: Scanner, val points : List<Delta>) {
        val delta = points[0].delta
        val scfn = points[0].scfn

        val scfnWithShift = { point : ScannerPoint ->
            val fn = scfn.invoke(point)
            ScannerPoint(fn.x + delta.x, fn.y + delta.y, fn.z + delta.z)
        }
    }
}
