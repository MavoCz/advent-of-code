package net.voldrich.aoc21

// https://adventofcode.com/2021/day/4
// First Bingo!! sum 932, number 48, multiplied 44736
// Last board with bingo!! sum 261, number 7, multiplied 1827
fun main() {
    Day4().run()
}

private class Day4 : BaseDay() {

    override fun task1() {
        analyzeBoards { bingoBoard, number ->
            // bingo!!
            val sum = bingoBoard.getSumOfUnusedNumbers()
            println("First Bingo!! sum $sum, number $number, multiplied ${sum * number}")
            true
        }
    }

    override fun task2() {
        val winningBoardSet = HashSet<BingoBoard>()
        val winningBoardList = ArrayList<Pair<BingoBoard, Int>>()
        analyzeBoards { bingoBoard, number ->
            // bingo!!
            if (winningBoardSet.add(bingoBoard)) {
                winningBoardList.add(Pair(bingoBoard, number))
            }
            winningBoardSet.size == 100 // end if all boards finished
        }

        val last = winningBoardList.last()
        val sum = last.first.getSumOfUnusedNumbers()
        println("Last board with bingo!! sum $sum, number ${last.second}, multiplied ${sum * last.second}")
    }

    // provide function which is invoked when bingo is detected.
    // Return true if you want to quit, false to continue with next number
    fun analyzeBoards(bingoHandler : (BingoBoard, number: Int) -> Boolean) {
        val draftedNumbers = inputLines[0].split(",").map { it.toInt() }
        val boards = readBoards()
        println("Number of boards ${boards.size}, number of drafted numbers ${draftedNumbers.size}")

        // build index mapping drafted number to board and its position
        val numberIndex = buildNumberIndex(boards);

        draftedNumbers.forEach { number ->
            numberIndex.indexMap[number]?.forEach { position ->
                if (position.markDrafted()) {
                    // bingo
                    if (bingoHandler.invoke(position.board, number)) {
                        return
                    }
                }}
        }
    }

    private fun buildNumberIndex(boards: List<BingoBoard>): NumberIndex {
        val index = NumberIndex()
        boards.forEach{ bingoBoard ->
            for (row in 0 until bingoBoard.board.size) {
                for (col in 0 until bingoBoard.board[row].size) {
                    val number = bingoBoard.board[row][col]

                    index.addNumber(number, NumberPosition(bingoBoard, row, col))
                }
            }
        }
        return index
    }

    fun readBoards(): ArrayList<BingoBoard> {
        var index = 2;

        val boards = ArrayList<BingoBoard>()
        while (index < inputLines.size) {
            boards.add(BingoBoard(arrayOf(
                readRow(index++),
                readRow(index++),
                readRow(index++),
                readRow(index++),
                readRow(index++)
            )))
            index++
        }

        return boards
    }

    fun readRow(index: Int): IntArray {
        val line = inputLines[index];
        if (line.isBlank()) {
            throw Exception("Line $index is empty")
        }
        try {
            return line.split(Regex("\\s+")).map { it.toInt() }.toIntArray()
        } catch (ex: NumberFormatException) {
            throw Exception("Failed to parse line $line on index $index : $ex")
        }
    }

    private class BingoBoard(val board: Array<IntArray>) {
        fun markDrafted(row: Int, col: Int): Boolean {
            board[row][col] = 0
            return checkBingo(row, col)
        }

        // if sum of updated row or column equals zero, that means that all numbers were picked => bingo
        private fun checkBingo(row: Int, col: Int) : Boolean {
            var sumCol = 0
            var sumRow = 0
            for (i in 0 until 5) {
                sumCol += board[row][i]
                sumRow += board[i][col]
            }
            if (sumCol == 0 || sumRow == 0) {
                return true
            }
            return false
        }

        fun getSumOfUnusedNumbers(): Int {
            return board.sumOf { it.sum() }
        }
    }


    private data class NumberPosition(val board: BingoBoard, val row: Int, val col: Int) {
        fun markDrafted(): Boolean {
            return board.markDrafted(row, col)
        }
    }

    private class NumberIndex {
        val indexMap = HashMap<Int, ArrayList<NumberPosition>>()

        fun addNumber(number: Int, numberPosition: NumberPosition) {
            indexMap.getOrPut(number) { ArrayList() }.add(numberPosition)
        }
    }
}