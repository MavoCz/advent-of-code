package net.voldrich.aoc21

import java.math.BigInteger

// link to task
fun main() {
    Day21().run()
}

class Day21 : BaseDay() {
    override fun task1() : Int {
        return playSimpleGame(7, 2)
    }

    // 111 = 3 , multi 1
    // 121,211,112 = 4, multi 3
    // 122,221,212,311,131,113 = 5, multi 6
    // 321,312,132,231,123,213,222 = 6, multi 7
    // 331,313,133,322,223,232 = 7, multi 6
    // 332,323,233 = 8, multi 3
    // 333 = 9, multi 1

    override fun task2() : BigInteger {
        return playUniverseGame(7, 2)
    }

    fun playUniverseGame(s1: Int, s2: Int) : BigInteger {
        playTurn(PlayerU(s1, true), PlayerU(s2, false), BigInteger.ONE)

        println("p1 $p1UniverseWinCount")
        println("p2 $p2UniverseWinCount")

        return p1UniverseWinCount
    }

    val diceRolls = mapOf( 3 to 1L,
        4 to 3L,
        5 to 6L,
        6 to 7L,
        7 to 6L,
        8 to 3L,
        9 to 1L )

    var p1UniverseWinCount = BigInteger.ZERO
    var p2UniverseWinCount = BigInteger.ZERO

    // p1 is on the move
    fun playTurn(p1: PlayerU, p2: PlayerU, universeCount : BigInteger) {
        diceRolls.forEach { entry ->
            val pMoved = PlayerU(p1, entry.key)
            val universeCountMoved = universeCount.multiply(BigInteger.valueOf(entry.value))
            if (pMoved.points >= 21) {
                if (pMoved.isPlayerOne) {
                    p1UniverseWinCount = p1UniverseWinCount.add(universeCountMoved)
                } else {
                    p2UniverseWinCount = p2UniverseWinCount.add(universeCountMoved)
                }
                return@forEach
            }
            playTurn(p2, pMoved, universeCountMoved)
        }
    }

    class PlayerU {

        var currentPos = 0
        var points = 0
        val isPlayerOne : Boolean

        constructor(startPos: Int, isPlayerOne : Boolean) {
            this.currentPos = startPos - 1
            this.isPlayerOne = isPlayerOne
        }

        constructor(player: PlayerU, roll: Int) {
            currentPos = (player.currentPos + roll) % 10
            points = player.points + currentPos + 1
            this.isPlayerOne = player.isPlayerOne
        }
    }

    // --- simple game

    fun playSimpleGame(s1: Int, s2: Int) : Int {
        val p1 = Player(s1)
        val p2 = Player(s2)
        return DiracGame(p1, p2).playMatch()
    }

    class DiracGame(val player1: Player, val player2: Player) {
        var dice = Dice()

        fun playMatch() : Int {
            (0..1000).forEach {
                if (player1.playTurn(dice)) {
                    return player2.points * dice.rollCount
                }
                if (player2.playTurn(dice)) {
                    return player1.points * dice.rollCount
                }
            }
            return 0
        }
    }

    class Dice {
        var diceRoll = 1

        var rollCount = 0

        fun roll() : Int {
            rollCount += 1
            return diceRoll++
        }
    }

    class Player(val startPos: Int, val winCondition: Int = 1000) {
        var currentPos = startPos - 1
        var points = 0

        fun playTurn(roll: Int) : Boolean {
            currentPos = (currentPos + roll) % 10
            points += currentPos + 1
            return wonGame()
        }

        fun playTurn(dice: Dice) : Boolean {
            return playTurn(dice.roll() + dice.roll() + dice.roll())
        }

        fun wonGame() = points >= winCondition
    }
}
