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

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].setOnClickListener {
                    onCellClicked(i, j)
                }
            }
        }

        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (board[row][col] == CellState.EMPTY) {
            board[row][col] = currentPlayer.cellState
            buttons[row][col].text = currentPlayer.cellState.toString()
            buttons[row][col].isEnabled = false

            if (checkForWinner()) {
                winnerTextView.text = "$currentPlayer wins!"
                disableButtons()
            } else if (isBoardFull()) {
                winnerTextView.text = "It's a draw!"
                disableButtons()
            } else {
                currentPlayer = if (currentPlayer == Player.X) Player.O else Player.X
            }
        }
    }

    private fun checkForWinner(): Boolean {
        // Check rows, columns, and diagonals for a winner
        for (i in 0..2) {
            if (board[i][0] != CellState.EMPTY && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return true
            }
            if (board[0][i] != CellState.EMPTY && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return true
            }
        }
        if (board[0][0] != CellState.EMPTY && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return true
        }
        if (board[0][2] != CellState.EMPTY && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return true
        }
        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == CellState.EMPTY) {
                    return false
                }
            }
        }
        return true
    }

    private fun disableButtons() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }

    private fun resetGame() {
        currentPlayer = Player.X
        board = Array(3) { Array(3) { CellState.EMPTY } }
        winnerTextView.text = ""

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
    }
}