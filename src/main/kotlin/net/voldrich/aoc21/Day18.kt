package net.voldrich.aoc21

import kotlin.math.max

// https://adventofcode.com/2021/day/18
fun main() {
    Day18().run()
}

class Day18 : BaseDay() {
    override fun task1() : Int {
        val finalSnailNumber = input.lines().map(::SnailNumber).reduce { a, b -> a.add(b) }

        return finalSnailNumber.calculateValue()
    }

    override fun task2() : Int {
        val numberList = input.lines().map(::SnailNumber).toList()

        var maxValue = Int.MIN_VALUE
        for (i in numberList) {
            for (j in numberList) {
                if (i != j) {
                    maxValue = max(i.add(j).calculateValue(), maxValue)
                    maxValue = max(j.add(i).calculateValue(), maxValue)
                }
            }
        }
        return maxValue
    }

    fun parseSnailNumber(numStr : String) : SnailNumber {
        return SnailNumber(numStr)
    }

    class SnailNumber {
        val rootPair : NumPair

        constructor(numStr : String) {
            rootPair = readSnailNumber(numStr.iterator()) as NumPair
            println(rootPair.toString())
        }

        constructor(rootPair : NumPair) {
            this.rootPair = rootPair
        }

        private fun readSnailNumber(iterator: CharIterator): NumBase {
            val char = iterator.nextChar()
            if (char == '[') {
                val pair = NumPair()

                pair.left = readSnailNumber(iterator)
                assert(iterator.nextChar() == ',')
                pair.right = readSnailNumber(iterator)
                assert(iterator.nextChar() == ']')
                return pair
            } else if (char.isDigit()) {
                return NumValue(char.digitToInt())
            } else throw Exception("Unexpected character $char")
        }

        fun add(other: SnailNumber) : SnailNumber {
            val pair = NumPair()

            pair.left = this.rootPair.clone()
            pair.right = other.rootPair.clone()

            val snailNumber = SnailNumber(pair)
            snailNumber.reduce()

            println("$this + $other = $snailNumber")
            return snailNumber
        }

        fun reduce() {
            //println("reduce $this")
            do {
                analyze()
                if (pairsToExplode.isNotEmpty()) {
                    val explodeCandidate = pairsToExplode.first()
                    val exPair = explodeCandidate.pair
                    explodeCandidate.closestLeft?.add(exPair.left as NumValue)
                    explodeCandidate.closestRight?.add(exPair.right as NumValue)
                    exPair.parent.replace(exPair, NumValue(0))

                    //println("explode $explodeCandidate $this")
                    continue
                }

                if (valuesToSplit.isNotEmpty()) {
                    val splitValue = valuesToSplit.first()
                    val pair = NumPair()
                    val halfFloor = splitValue.value / 2
                    pair.left = NumValue(halfFloor)
                    pair.right = NumValue(splitValue.value - halfFloor)

                    splitValue.parent.replace(splitValue, pair)
                    //println("split $splitValue $this")
                    continue
                }
            } while (pairsToExplode.isNotEmpty() || valuesToSplit.isNotEmpty())
        }

        val pairsToExplode = ArrayList<ExplodeCandidate>()
        val valuesToSplit = ArrayList<NumValue>()

        fun analyze() {
            pairsToExplode.clear()
            valuesToSplit.clear()
            analyze(rootPair, 1, null, null)
        }

        fun analyze(pair: NumPair, depth: Int, closestLeft: NumValue?, closestRight: NumValue?) {
            val left = pair.left
            val right = pair.right

            if (left is NumValue && left.value > 9) {
                valuesToSplit.add(left)
            }

            if (left is NumPair) {
                analyze(left, depth + 1, closestLeft, findClosesRight(pair))
            }

            if (left is NumValue && right is NumValue && depth > 4) {
                pairsToExplode.add(ExplodeCandidate(pair, closestLeft, closestRight))
            }

            if (right is NumPair) {
                analyze(right, depth + 1, findClosesLeft(pair), closestRight)
            }

            if (right is NumValue && right.value > 9) {
                valuesToSplit.add(right)
            }
        }

        data class ExplodeCandidate(val pair: NumPair, val closestLeft: NumValue?, val closestRight: NumValue?)

        private fun findClosesRight(pair: NumPair): NumValue {
            if (pair.right is NumValue) {
                return pair.right as NumValue
            }

            var leftMost = pair.right
            while (leftMost is NumPair) {
                leftMost = leftMost.left
            }

            if (leftMost is NumValue) {
                return leftMost
            }
            throw Exception("Right value not found")
        }

        private fun findClosesLeft(pair: NumPair): NumValue {
            if (pair.left is NumValue) {
                return pair.left as NumValue
            }

            var rightMost = pair.left
            while (rightMost is NumPair) {
                rightMost = rightMost.right
            }

            if (rightMost is NumValue) {
                return rightMost
            }
            throw Exception("Left value not found")
        }

        fun calculateValue() : Int {
            return rootPair.calculateValue()
        }

        override fun toString(): String {
            return rootPair.toString()
        }
    }

    class NumPair : NumBase() {

        var left: NumBase = NumValue(0)
            set(value) {
                value.parent = this
                field = value
            }
        var right: NumBase = NumValue(0)
            set(value) {
                value.parent = this
                field = value
            }

        override fun toString(builder: StringBuilder) {
            builder.append('[')
            left.toString(builder)
            builder.append(',')
            right.toString(builder)
            builder.append(']')
        }

        fun replace(oldPair: NumBase, newPair: NumBase) {
            if (left == oldPair) left = newPair
            if (right == oldPair) right = newPair
            newPair.parent = this
        }

        override fun calculateValue(): Int {
            return 3 * left.calculateValue() + 2 * right.calculateValue()
        }

        override fun clone(): NumBase {
            val clone = NumPair()
            clone.left = left.clone()
            clone.right = right.clone()
            return clone
        }
    }

    class NumValue(var value: Int = 0) : NumBase() {

        override fun toString(builder: StringBuilder) {
            builder.append(value)
        }

        override fun calculateValue(): Int {
            return value
        }

        override fun clone(): NumBase {
            return NumValue(value)
        }

        fun add(other: NumValue) {
            this.value += other.value
        }
    }

    abstract class NumBase {

        lateinit var parent: NumPair

        abstract fun toString(builder: StringBuilder)

        override fun toString(): String {
            val sb = StringBuilder()
            toString(sb)
            return sb.toString()
        }

        abstract fun calculateValue(): Int

        abstract fun clone(): NumBase
    }


}
