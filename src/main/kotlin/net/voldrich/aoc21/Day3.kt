package net.voldrich.aoc21

// https://adventofcode.com/2021/day/2
/* correct results:
gamma 1114, epsilon 2981, multiplied 3320834
oxygen: 101011101111, decimal: 2799
co2: 011001000001, decimal: 1601
multiply: 4481199
*/
fun main() {
    Day3().run()
}

class Day3 : BaseDay() {

    override fun task1() : Int {
        val bitCount = input.lines()[0].length;
        val oneCounts = IntArray(bitCount)
        input.lines().forEach {
            for (i in 0 until bitCount) {
                if (it[i] == '1') {
                    oneCounts[i] += 1;
                }
            }
        }

        val gamma = StringBuilder()
        val epsilon = StringBuilder()
        for (i in 0 until bitCount) {
            if (oneCounts[i] > input.lines().size / 2) {
                gamma.append("0")
                epsilon.append("1")
            } else {
                gamma.append("1")
                epsilon.append("0")
            }
        }

        val gammaDecimal = gamma.toString().toInt(2)
        val epsilonDecimal = epsilon.toString().toInt(2)
        return gammaDecimal * epsilonDecimal
    }

    override fun task2() : Int {
        val oxygen = calculate(input.lines(), fun(oneCount, size): Char {
            if (size == 2) return '1'
            return if (oneCount >= size - oneCount) '1' else '0'
        })
        val co2 = calculate(input.lines(), fun(oneCount, size): Char {
            if (size == 2) return '0'
            return if (oneCount >= size - oneCount) '0' else '1'
        })
        println ("oxygen: $oxygen, decimal: ${oxygen.toInt(2)}")
        println ("co2: $co2, decimal: ${co2.toInt(2)}")
        return oxygen.toInt(2) * co2.toInt(2)
    }

    private fun calculate(lines: List<String>, logic: (Int, Int) -> Char): String {
        val bitCount = lines[0].length;

        var filteredLines = lines
        for (i in 0 until bitCount) {
            val oneCount = calculateOneCount(i, filteredLines)
            val filteredNum = logic(oneCount, filteredLines.size)
            filteredLines = filteredLines.filter { it[i] == filteredNum }.toList()
            if (filteredLines.size == 1) {
                return filteredLines[0]
            }
        }

        throw Exception("More then one lines remained " + filteredLines.size)
    }

    private fun calculateOneCount(index: Int, lines: List<String>) : Int {
        return lines.map { it[index] }.count { it == '1' }
    }
}