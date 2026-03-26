package com.example.squarecounter.ui.theme

import android.view.MotionEvent
import kotlin.math.min

class Othello {
    val board = Array(8) { IntArray(8) { 0 } }

    init { // initiate with starting positions, 4 tiles in the center of the screen
        board[3][3] = 1
        board[3][4] = 2
        board[4][3] = 2
        board[4][4] = 1
    }

    fun getCellState(row: Int, col: Int) : Int {
        return board[row][col];
    }
}