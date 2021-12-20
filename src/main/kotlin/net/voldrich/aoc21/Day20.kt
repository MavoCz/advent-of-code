package net.voldrich.aoc21

// link to task
fun main() {
    Day20().run()
}

class Day20 : BaseDay() {
    override fun task1() : Int {
        val image = ImageEnhancement()
        image.print()
        val enhanced = image.enhance()
        enhanced.print()
        val twiceEnhanced = enhanced.enhance()
        twiceEnhanced.print()
        return twiceEnhanced.totalBrightPoints()
    }

    override fun task2() : Int {
        val image = ImageEnhancement()
        val enhanced = (1..50).fold(image) { img, _ -> img.enhance() }
        return enhanced.totalBrightPoints()
    }

    inner class ImageEnhancement {
        val enhancementLine: String

        val image: List<String>

        val expandChar: Char

        constructor() {
            expandChar = '0'
            enhancementLine = input.lines()[0]
            image = expandImage(input.lines().subList(2, input.lines().size)
                .map { it
                    .replace('#', '1')
                    .replace('.', '0') })
        }

        constructor(enhancementLine: String, image: List<String>, prevExpandChar : Char) {
            this.enhancementLine = enhancementLine
            // if the transformation of 000 000 000 translates to # this means that all infinity off screen . will became #
            // if the transformation of 111 111 111 translates to . this means that all infinity off screen # will became .
            this.expandChar = if (enhancementLine[0] == '.') '0' else if (prevExpandChar == '0') '1' else '0'
            this.image = expandImage(image)
        }

        fun expandImage(img: List<String>): List<String> {
            val map = img.map { expandChar + it + expandChar }.toMutableList()
            map.add(0, getEmptyLine(img[0].length + 2))
            map.add(getEmptyLine(img[0].length + 2))
            return map
        }

        fun enhance(): ImageEnhancement {
            return ImageEnhancement(enhancementLine, image.mapIndexed { y, line ->
                line.mapIndexed{ x, _ ->
                    enhanceItem(y, x)
                }.joinToString("")
            }, expandChar)
        }

        private fun enhanceItem(y: Int, x: Int) : Char {
            val num = getData(y-1, x) + getData(y, x) + getData(y+1, x)
            val index = num.toInt(2)
            return if (enhancementLine[index] == '#') '1' else '0'
        }

        fun getEmptyLine(size: Int) : String {
            return (0 until size).map { expandChar }.joinToString("")
        }

        private fun getData(y: Int, x: Int): String {
            val row = if (y in image.indices) image[y] else getEmptyLine(image[0].length)

            return when (x) {
                0 -> expandChar + row.substring(0, 2)
                row.length - 1 -> row.substring(x-1, row.length) + expandChar
                else -> row.substring(x-1, x+2)
            }
        }

        fun totalBrightPoints(): Int {
            return image.sumOf { it.count { char -> char == '1' } }
        }

        fun print() {
            image.forEach { println(it) }
            println()
        }
    }
}
