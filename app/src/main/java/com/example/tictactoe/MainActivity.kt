package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.semantics.text

class MainActivity : AppCompatActivity() {

    private var currentPlayer = 1
    private var board = Array(3) { IntArray(3) { 0 } }
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
        if (board[row][col] == 0) {
            board[row][col] = currentPlayer
            buttons[row][col].text = if (currentPlayer == 1) "X" else "O"
            buttons[row][col].isEnabled = false

            if (checkForWinner()) {
                winnerTextView.text = if (currentPlayer == 1) "X wins!" else "O wins!"
                disableButtons()
            } else if (isBoardFull()) {
                winnerTextView.text = "It's a draw!"
                disableButtons()
            } else {
                currentPlayer = 3 - currentPlayer // Switch players (1 to 2 or 2 to 1)
            }
        }
    }

    private fun checkForWinner(): Boolean {
        // Check rows, columns, and diagonals for a winner
        for (i in 0..2) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return true
            }
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return true
            }
        }
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return true
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return true
        }
        return false
    }

    private fun isBoardFull(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == 0) {
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
        currentPlayer = 1
        board = Array(3) { IntArray(3) { 0 } }
        winnerTextView.text = ""

        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
    }
}