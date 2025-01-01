package com.example.tictactoe

enum class Player(val cellState: CellState) {
    X(CellState.X),
    O(CellState.O)
}