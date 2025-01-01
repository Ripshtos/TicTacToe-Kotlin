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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        winnerTextView = findViewById(R.id.winnerTextView)
        playAgainButton = findViewById(R.id.playAgainButton)

        board = arrayOf(
            arrayOf(
                Cell(findViewById<Button>(R.id.button0)),
                Cell(findViewById(R.id.button1)),
                Cell(findViewById(R.id.button2))
            ),
            arrayOf(
                Cell(findViewById(R.id.button3)),
                Cell(findViewById(R.id.button4)),
                Cell(findViewById(R.id.button5))
            ),
            arrayOf(
                Cell(findViewById(R.id.button6)),
                Cell(findViewById(R.id.button7)),
                Cell(findViewById(R.id.button8))
            )
        )

        board.forEachIndexed { i, row -> row.forEachIndexed { j, cell -> cell.button.setOnClickListener {
            onCellClicked(i, j)
        } } }

        playAgainButton.setOnClickListener {
            resetGame()
        }
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
                board[row][0].cellState == board[row][1].cellState &&
                board[row][0].cellState == board[row][2].cellState
    }

    private fun checkColumnForWinner(col: Int): Boolean {
        return !board[0][col].isEmpty() &&
                board[0][col].cellState == board[1][col].cellState &&
                board[0][col].cellState == board[2][col].cellState
    }

    private fun checkDiagonalsForWinner(row: Int, col: Int): Boolean {
        return !board[row][col].isEmpty() &&
                (row == col && board[0][0].cellState == board[1][1].cellState &&
                        board[0][0].cellState == board[2][2].cellState ||
                row + col == 2 && board[0][2].cellState == board[1][1].cellState &&
                        board[0][2].cellState == board[2][0].cellState)
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