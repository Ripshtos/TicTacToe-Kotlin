package com.example.tictactoe

import android.widget.Button

class Cell(var button: Button) {
    var cellState: CellState = CellState.EMPTY

    fun isEmpty(): Boolean {
        return cellState == CellState.EMPTY
    }

    fun fill(cellState: CellState) {
        this.cellState = cellState
        button.text = cellState.toString()
        button.isEnabled = false
    }

    fun reset() {
        cellState = CellState.EMPTY
        button.text = ""
        button.isEnabled = true
    }

    fun isSameState(cell: Cell): Boolean {
        return cellState == cell.cellState
    }
}