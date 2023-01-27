//const val TIE : Int = 3
var GAMELOOP = true

abstract class Game {
    // TODO: Everything. What do you mean 'everything'? EVEERYYYYTHIIIIINGGG!!!
}

interface MakeGame {
    // Create game board of given size (for games like Tic Tac Toe, Connect Four)
}

interface ITakeTurn {
    fun takeTurn()
}

class TicTacToeGame: Game() {
    fun checkRows(board : GameBoard): Array<Any> {
        if (board.board[0][0] == board.board[0][1] && board.board[0][1] == board.board[0][2] && board.board[0][0] != " ") {
            return arrayOf(0, board.board[0][0])
        } else if (board.board[1][0] == board.board[1][1] && board.board[1][1] == board.board[1][2] && board.board[1][0] != " ") {
            return arrayOf(1, board.board[1][0])
        } else if (board.board[2][0] == board.board[2][1] && board.board[2][1] == board.board[2][2] && board.board[2][0] != " ") {
            return arrayOf(2, board.board[2][0])
        }
        return arrayOf(-1, false)
    }
    fun checkColumns(board: GameBoard) : Array<Any>{
        if (board.board[0][0] == board.board[1][0] && board.board[1][0] == board.board[2][0] && board.board[0][0] != " ") {
            return arrayOf(0, board.board[0][0])
        } else if (board.board[0][1] == board.board[1][1] && board.board[1][1] == board.board[2][1] && board.board[0][1] != " ") {
            return arrayOf(1, board.board[0][1])
        } else if (board.board[0][2] == board.board[1][2] && board.board[1][2] == board.board[2][2] && board.board[0][2] != " ") {
            return arrayOf(2, board.board[0][2])
        }
        return arrayOf(-1, false)
    }
    fun checkDiagonals(board : GameBoard) : Array<Any>{
        if(board.board[1][1] != " ") {
            if (board.board[0][0] == board.board[1][1] && board.board[1][1] == board.board[2][2]) {
                return arrayOf(1, board.board[1][1])
            } else if (board.board[0][2] == board.board[1][1] && board.board[1][1] == board.board[2][0]) {
                return arrayOf(2, board.board[1][1])
            }
        }
        return arrayOf(-1, false)
    }
    fun printGameBoard(board: List<List<String>>){
        val toPrint = mutableListOf<String>()

        for (row in board) {
            var rowString : String = "|"
            for (x in row.withIndex()) {
                if (x.index == 1) {
                    var toAdd : String = x.value.toString()
                    rowString += "| $toAdd |"
                } else {
                    var toAdd : String = x.value.toString()
                    rowString += " $toAdd "
                }
            }
            rowString  += "|"
            toPrint += rowString
        }
        println("-".repeat(13))
        println(toPrint[0])
        println("-".repeat(13))
        println(toPrint[1])
        println("-".repeat(13))
        println(toPrint[2])
        println("-".repeat(13))
    }
    fun isValidMove(row: Int, column: Int, board: List<List<String>>): Boolean {
        if (board[row][column] == " ") {
            return true
        }
        return false
    }
}


class GameBoard {
    private var _board = MutableList(3) {MutableList(3) {" "}}

    var board = List(3) {List<String>(3){" "}}
        get() = _board.toList()

    fun makeMove(x: Int, y: Int, symbol: String) {
        _board[x][y] = symbol
    }

    fun printInitialBoard() {
        val toPrint = mutableListOf<String>()
        val initial = arrayOf(
            arrayOf(1, 2, 3),
            arrayOf(4, 5, 6),
            arrayOf(7, 8, 9))

        for (row in initial) {
            var rowString : String = "|"
            for (x in row.withIndex()) {
                if (x.index == 1) {
                    var toAdd : String = x.value.toString()
                    rowString += "| $toAdd |"
                } else {
                    var toAdd : String = x.value.toString()
                    rowString += " $toAdd "
                }
            }
            rowString  += "|"
            toPrint += rowString
        }
        println("-".repeat(13))
        println(toPrint[0])
        println("-".repeat(13))
        println(toPrint[1])
        println("-".repeat(13))
        println(toPrint[2])
        println("-".repeat(13))
        println("To place your marker symbol, select any of the 9 grid squares...")
    }
}

class Player(val symbol: String): ITakeTurn {
    override fun takeTurn() {
    }
}

fun varInput() : List<Int> {
    var x = readln().toIntOrNull()
    if (x != null) {
        return when (x) {
            in 1..3 -> listOf(0, x - 1)
            in 4..6 -> listOf(1, x - 4)
            in 7..9 -> listOf(2, x - 7)
            else -> {
                println("That's not a valid number, try again:")
                return varInput()}
        }
    }
    println("That's not a valid number, try again")
    return varInput()
}

fun playGame(gameID: Int) {
    var moves : Int = 0
    var over : Boolean = false
    val pOne = Player("X")
    val pTwo = Player("O")
    var gameBoard = GameBoard()
    val ticTacToe = TicTacToeGame()
    val playAgain : () -> Unit = {println("Want to play again?\n0. No\n1. Yes")}

    while (!over) {
        if (gameID == 1) {
            var currentPlayer : Player
            when (moves % 2) {
                0 -> currentPlayer = pOne
                else -> currentPlayer = pTwo
            }

            if (moves == 0) {
                gameBoard.printInitialBoard()
            } else {
                ticTacToe.printGameBoard(gameBoard.board)
            }

            println("Player ${currentPlayer.symbol} make your move:")
            var (x, y) = varInput()
            if (ticTacToe.isValidMove(x, y, gameBoard.board)) {
                gameBoard.makeMove(x, y, currentPlayer.symbol)
                moves += 1
                if (ticTacToe.checkColumns(gameBoard)[0] != -1) {
                    ticTacToe.printGameBoard(gameBoard.board)
                    println("Player ${ticTacToe.checkColumns(gameBoard)[1]} wins in column ${ticTacToe.checkColumns(gameBoard)[0]}")
                    playAgain()
                    over = true
                    continue
                }
                if (ticTacToe.checkRows(gameBoard)[0] != -1) {
                    ticTacToe.printGameBoard(gameBoard.board)
                    println("Player ${ticTacToe.checkRows(gameBoard)[1]} wins in row ${ticTacToe.checkRows(gameBoard)[0]}")
                    playAgain()
                    over = true
                    continue
                }
                if (ticTacToe.checkDiagonals(gameBoard)[0] != -1) {
                    ticTacToe.printGameBoard(gameBoard.board)
                    println("Player ${ticTacToe.checkDiagonals(gameBoard)[1]} wins in diagonal ${ticTacToe.checkDiagonals(gameBoard)[0]}")
                    playAgain()
                    over = true
                    continue
                }
                if (moves == 9) {
                    ticTacToe.printGameBoard(gameBoard.board)
                    println("Game has tied.")
                    playAgain()
                    over = true
                    continue
                }
            } else {
                    println("That's not a valid move.")
                }
            }
        }
}


fun main() {
    println("Select game by entering the number...\n 1. TicTacToe\n 0. Exit")
    while (GAMELOOP) {
        when (readln().toInt()) {
            0 -> GAMELOOP = false
            1 -> playGame(1)
        }
    }
}