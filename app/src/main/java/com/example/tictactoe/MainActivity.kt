package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = Player.X
    private var board = Array(3) { Array(3) { CellState.EMPTY } }
    private lateinit var buttons: Array<Array<Button>>
    private lateinit var winnerTextView: TextView
    private lateinit var playAgainButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        winnerTextView = findViewById(R.id.winnerTextView)
        playAgainButton = findViewById(R.id.playAgainButton)

        buttons = arrayOf(
            arrayOf(
                findViewById<Button>(R.id.button0),
                findViewById(R.id.button1),
                findViewById(R.id.button2)
            ),
            arrayOf(
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5)
            ),
            arrayOf(
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8)
            )
        )

        buttons.forEachIndexed { i, row -> row.forEachIndexed { j, button -> button.setOnClickListener {
            onCellClicked(i, j)
        } } }

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        board[row][col] = currentPlayer.cellState
        buttons[row][col].text = currentPlayer.cellState.toString()
        buttons[row][col].isEnabled = false

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
        return board[row][0] != CellState.EMPTY && board[row][0] == board[row][1] && board[row][0] == board[row][2]
    }

    private fun checkColumnForWinner(col: Int): Boolean {
        return board[0][col] != CellState.EMPTY && board[0][col] == board[1][col] && board[0][col] == board[2][col]
    }

    private fun checkDiagonalsForWinner(row: Int, col: Int): Boolean {
        return board[row][col] != CellState.EMPTY && (row == col && board[0][0] == board[1][1] && board[0][0] == board[2][2] ||
                row + col == 2 && board[0][2] == board[1][1] && board[0][2] == board[2][0])
    }

    private fun isBoardFull(): Boolean {
        return !board.any { row -> row.any { cell -> cell == CellState.EMPTY } }
    }

    private fun disableButtons() {
        buttons.forEach { row -> row.forEach { button -> button.isEnabled = false } }
    }

    private fun resetGame() {
        currentPlayer = Player.X
        board = Array(3) { Array(3) { CellState.EMPTY } }
        winnerTextView.text = ""

        buttons.forEach { row -> row.forEach { button ->
            button.text = ""
            button.isEnabled = true
        } }
    }
}