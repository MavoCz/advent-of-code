package net.voldrich.aoc21

// https://adventofcode.com/2021/day/8
fun main() {
    Day8().run()
}

/*

1 = 2
4 = 4
7 = 3
8 = 7

5 = 5
2 = 5
3 = 5

6 = 6
9 = 6
0 = 6

**/
class Day8 : BaseDay() {
    override fun task1() : Int {
        val signalEntries = parseEntries()

        return signalEntries.sumOf { it.countUniqueNumbers() }
    }

    override fun task2() : Int {
        val signalEntries = parseEntries()

        return signalEntries.sumOf {it. decodeOutputNumber() }
    }

    private fun sortString(str: String) : String {
        return str.toCharArray().sorted().joinToString("");
    }

    private fun parseEntries() : List<SignalEntry> {
        return input.lines().map { line ->
            val lineSplit = line.split("|")

            SignalEntry(
                lineSplit[0].trim().split(" ").map { sortString(it) },
                lineSplit[1].trim().split(" ").map { sortString(it) }
            )
        }
    }

    private class SignalEntry(val patterns: List<String>, val output: List<String>) {
        fun countUniqueNumbers() : Int {
            return output.count { it.length == 2 || it.length == 4 || it.length == 3 || it.length == 7 }
        }

        fun decodeOutputNumber() : Int {
            // maps number to its code
            val p1 = getSinglePatternOfSize(2)
            val p4 = getSinglePatternOfSize(4)
            val p7 = getSinglePatternOfSize(3)
            val p8 = getSinglePatternOfSize(7)

            val p235 = getPatternOfSize(5, 3)
            val p069 = getPatternOfSize(6, 3)

            // 3 contains a single shared line with 1
            val p3 = p235.single { intersect(it, p1) == p1 }
            val p25 = p235.filter { intersect(it, p1) != p1 }

            // 6 minus 1 == 1. others == 2
            val p6 = p069.single { minus(p1, it).length == 1 }
            val p09 = p069.filter { minus(p1, it).length != 1 }

            val se = minus (minus(p6, p4), p3)

            // zero contains E
            val p0 = p09.single { it.contains(se) }
            val p9 = p09.single { !it.contains(se) }

            // 2 contains E
            val p2 = p25.single { it.contains(se) }
            val p5 = p25.single { !it.contains(se) }

            val codeToNumberMap = mapOf(
                p1 to '1',
                p2 to '2',
                p3 to '3',
                p4 to '4',
                p5 to '5',
                p6 to '6',
                p7 to '7',
                p8 to '8',
                p9 to '9',
                p0 to '0',
            )

            return output.map { codeToNumberMap[it] }.joinToString ("").toInt()
        }

        private fun minus(first: String, second: String): String {
            return first.toCharArray().toSet().minus(second.toCharArray().toSet()).joinToString("")
        }

        private fun intersect(first: String, second: String): String {
            val intersect = first.toCharArray().intersect(second.toCharArray().toSet())
            return intersect.toCharArray().joinToString("")
        }

        private fun getPatternOfSize(size: Int, expectedSize: Int) : List<String> {
            val list = patterns.filter { it.length == size }.toList()
            if (list.size != expectedSize) throw Exception("More then $expectedSize results $list with size $size")
            return list
        }

        private fun getSinglePatternOfSize(size: Int) : String {
            return getPatternOfSize(size, 1).first()
        }

    }
}
