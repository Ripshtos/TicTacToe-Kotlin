package com.example.tictactoe

import android.widget.Button

class Cell(var button: Button) {
    var cellState: CellState = CellState.EMPTY
}