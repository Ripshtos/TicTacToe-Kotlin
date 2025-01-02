package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = Player.X
    private lateinit var board: Array<Array<Cell>>
    private lateinit var winnerTextView: TextView
    private lateinit var playAgainButton: Button
    private var cellButtonIds: Array<Int> = arrayOf(
        R.id.button0, R.id.button1, R.id.button2,
        R.id.button3, R.id.button4, R.id.button5,
        R.id.button6, R.id.button7, R.id.button8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        winnerTextView = findViewById(R.id.winnerTextView)
        playAgainButton = findViewById(R.id.playAgainButton)

        board = Array(3) { row -> Array(3) { col -> createCell(row, col) } }

        board.forEachIndexed { i, row -> row.forEachIndexed { j, cell -> cell.button.setOnClickListener {
            onCellClicked(i, j)
        } } }

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun createCell(row: Int, col: Int): Cell {
        return Cell(findViewById<Button>(cellButtonIds[row * 3 + col]))
    }

    private fun onCellClicked(row: Int, col: Int) {
        board[row][col].fill(currentPlayer.cellState)

        if (checkForWinner(row, col)) {
            winnerTextView.text = "$currentPlayer wins!"
            disableButtons()
        } else if (isBoardFull()) {
            winnerTextView.text = "It's a draw!"
            disableButtons()
        } else {
            currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
        }
    }

    private fun checkForWinner(row: Int, col: Int): Boolean {
        return checkRowForWinner(row) || checkColumnForWinner(col) || checkDiagonalsForWinner(row, col)
    }

    private fun checkRowForWinner(row: Int): Boolean {
        return !board[row][0].isEmpty() &&
                board[row][0].isSameState(board[row][1]) &&
                board[row][0].isSameState(board[row][2])
    }

    private fun checkColumnForWinner(col: Int): Boolean {
        return !board[0][col].isEmpty() &&
                board[0][col].isSameState(board[1][col]) &&
                board[0][col].isSameState(board[2][col])
    }

    private fun checkDiagonalsForWinner(row: Int, col: Int): Boolean {
        return !board[row][col].isEmpty() &&
                (row == col && board[0][0].isSameState(board[1][1]) &&
                        board[0][0].isSameState(board[2][2]) ||
                row + col == 2 && board[0][2].isSameState(board[1][1]) &&
                        board[0][2].isSameState(board[2][0]))
    }

    private fun isBoardFull(): Boolean {
        return !board.any { row -> row.any { cell -> cell.isEmpty() } }
    }

    private fun disableButtons() {
        board.forEach { row -> row.forEach { cell -> cell.button.isEnabled = false } }
    }

    private fun resetGame() {
        currentPlayer = Player.X
        winnerTextView.text = ""

        board.forEach { row -> row.forEach { cell -> cell.reset() } }
    }
}