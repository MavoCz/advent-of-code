package net.voldrich.aoc21

import kotlin.math.abs
import kotlin.math.max

// https://adventofcode.com/2021/day/17
fun main() {
    Day17().run()
}

class Day17 : BaseDay() {
    override fun task1() : Int {
        val canon = ProbeCanon(input.lines()[0])
        return canon.findHighestShot()
    }

    override fun task2() : Int {
        val canon = ProbeCanon(input.lines()[0])
        val shotList = canon.findAllPossibleShots()
        return shotList.size
    }

    class ProbeCanon(targetAreaStr : String) {
        val targetMinX : Int
        val targetMaxX : Int
        val targetMinY : Int
        val targetMaxY : Int

        init {
            val regex = Regex("x=([0-9\\-]+)..([0-9\\-]+), y=([0-9\\-]+)..([0-9\\-]+)")
            val matches = regex.find(targetAreaStr) ?: throw Exception("Failed to parse target area")

            targetMinX = matches.groups[1]!!.value.toInt()
            targetMaxX = matches.groups[2]!!.value.toInt()
            targetMinY = matches.groups[3]!!.value.toInt()
            targetMaxY = matches.groups[4]!!.value.toInt()
        }

        fun findHighestShot() : Int {
            val velocityMinX = findMinHorizontalVelocity(targetMinX)
            val velocityMaxX = targetMaxX
            val velocityMinY = 0
            val velocityMaxY = abs(targetMinY)

            println("Velocity candidate area x: $velocityMinX $velocityMaxX, y: $velocityMinY $velocityMaxY ")

            var maxy = Integer.MIN_VALUE
            for (vy in (velocityMinY..velocityMaxY)) {
                for (vx in (velocityMinX..velocityMaxX)) {
                    val probeShot = ProbeShot(vy, vx)
                    if (testShot(probeShot)) {
                        maxy = max(probeShot.maxy, maxy)
                    }
                }
            }

            return maxy
        }

        fun findAllPossibleShots(): ArrayList<ProbeShot> {
            val velocityMinX = findMinHorizontalVelocity(targetMinX)
            val velocityMaxX = targetMaxX
            val velocityMinY = targetMinY
            val velocityMaxY = abs(targetMinY)

            println("Velocity candidate area x: $velocityMinX $velocityMaxX, y: $velocityMinY $velocityMaxY ")

            val shotList = ArrayList<ProbeShot>()
            for (vy in (velocityMinY..velocityMaxY)) {
                for (vx in (velocityMinX..velocityMaxX)) {
                    val probeShot = ProbeShot(vy, vx)
                    if (testShot(probeShot)) {
                        shotList.add(probeShot)
                    }
                }
            }

            return shotList
        }

        private fun testShot(probeShot: ProbeShot): Boolean {
            while (probeShot.posx < targetMaxX && probeShot.posy > targetMinY) {
                probeShot.moveToNextProbeLocation()
                if (probeShot.posx in targetMinX..targetMaxX
                    && probeShot.posy in targetMinY..targetMaxY) {
                    return true;
                }
            }
            return false
        }

        private fun findMinHorizontalVelocity(targetMinX: Int): Int {
            for (i in (1.. targetMinX)) {
                val sum = (i*(i +1)) / 2
                if (sum > targetMinX) {
                    return i
                }
            }
            throw Exception("Could not find minX")
        }
    }

    data class ProbeShot(val vy: Int, val vx: Int) {
        var step = 0
        var posx = 0
        var posy = 0

        var maxy = Integer.MIN_VALUE

        var currentVy = vy
        var currentVx = vx

        fun moveToNextProbeLocation() {
            posx += currentVx
            posy += currentVy
            if (currentVx > 0) {
                currentVx -= 1
            } else if (currentVx < 0) {
                currentVx += 1
            }
            currentVy -= 1
            step++
            maxy = max(posy, maxy)
        }
    }
}
