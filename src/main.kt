//const val TIE : Int = 3
var GAMELOOP = true

abstract class AGame {
    // TODO: Everything. What do you mean 'everything'? EVEERYYYYTHIIIIINGGG!!!
}

interface MakeGame {
    // Create game board of given size (for games like Tic Tac Toe, Connect Four)
}

interface ITakeTurn {
    fun takeTurn()
}

class TicTacToeGame: AGame() {
    //Class for rules
    //Inherits from the superclass Game
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
}


class GameBoard {
    //Class to represent the game board
    // [ [0,0    0,1     0,2],

    //   [1,0    1,1     1,2],

    //   [2,0    2,1     2,2]]

    private var _board = MutableList(3) {MutableList(3) {" "}}

    var board = List(3) {List<String>(3){" "}}
        get() = _board.toList()

    fun makeMove(x: Int, y: Int, symbol: String) {
        _board[x][y] = symbol
    }

    fun print(){
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
                println("Invalid move, try again:")
                return varInput()}
        }
    }
    println("Invalid move, try again:")
    return varInput()
}

fun playGame(gameID: Int) { // !!!!!!
    var moves : Int = 0
    var over : Boolean = false
    val pOne = Player("X")
    val pTwo = Player("O")
    var gameBoard = GameBoard()
    val ticTacToe = TicTacToeGame()

    while (!over) {
        if (gameID == 1) {
            var currentPlayer : Player
            when (moves % 2) {
                0 -> currentPlayer = pOne
                else -> currentPlayer = pTwo
            }

            if (moves == 0) gameBoard.printInitialBoard()
            else gameBoard.print()

            println("Player ${currentPlayer.symbol} make your move:")
            var (x, y) = varInput()
            if (gameBoard.board[x][y] != " ") {
                println("Invalid choice.")
            } else {
                gameBoard.makeMove(x, y, currentPlayer.symbol)
                moves += 1
                if (ticTacToe.checkColumns(gameBoard)[0] != -1) {
                    gameBoard.print()
                    println("Player ${ticTacToe.checkColumns(gameBoard)[1]} wins in column ${ticTacToe.checkColumns(gameBoard)[0]}")
                    over = true
                    continue
                }
                if (ticTacToe.checkRows(gameBoard)[0] != -1) {
                    gameBoard.print()
                    println("Player ${ticTacToe.checkRows(gameBoard)[1]} wins in row ${ticTacToe.checkRows(gameBoard)[0]}")
                    over = true
                    continue
                }
                if (ticTacToe.checkDiagonals(gameBoard)[0] != -1) {
                    gameBoard.print()
                    println("Player ${ticTacToe.checkDiagonals(gameBoard)[1]} wins in diagonal ${ticTacToe.checkDiagonals(gameBoard)[0]}")
                    over = true
                    continue
                }
                if (moves == 9) {
                    gameBoard.print()
                    println("Game has tied.")
                    over = true
                    continue
                }
            }
        }
    }
}

fun main() {
    while (GAMELOOP) {
        println("Select game by entering the number...\n 1. TicTacToe\n 0. Exit")
        when (readln().toInt()) {
            0 -> GAMELOOP = false
            1 -> playGame(1)
        }
    }
}