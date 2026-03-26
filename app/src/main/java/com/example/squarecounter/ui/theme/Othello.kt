package com.example.squarecounter.ui.theme

class Othello() {
    val board = Array(8) { IntArray(8) { 0 } }
    var player : Int = 1

    init { // initiate with starting positions, 4 tiles in the center of the screen
        board[3][3] = 1
        board[3][4] = 2
        board[4][3] = 2
        board[4][4] = 1

        player = 1
    }

    // helper list for determining legal moves (walkTiles())
    val directions = listOf(
        -1 to -1, -1 to 0, -1 to 1,
        0 to -1,          0 to 1,
        1 to -1,  1 to 0, 1 to 1
    )

    // get current state of the cell; passed to BoardView for drawing
    fun getCellState(row: Int, col: Int) : Int {
        return board[row][col];
    }

    // walk tiles to find opponent and adjacent player tiles
    fun walkTiles(r: Int, c: Int, dirr: Int, dirc: Int): Boolean {
        val opponent = if (player == 1) 2 else 1

        var row = r + dirr
        var col = c + dirc

        // check for out-of-bounds indicies
        if (row !in 0..7 || col !in 0..7) return false

        // First piece must be opponent
        if (board[row][col] != opponent)
            return false

        row += dirr
        col += dirc

        // Walk until a tile is hit
        while (row in 0..7 && col in 0..7) {
            val tile = board[row][col]
            if (tile == opponent) {
                // keep walking
                row += dirr
                col += dirc
            }
            else if (tile == player) {
                // found player tile; valid move
                return true
            }
            else {
                // empty tile at the end of walk
                return false
            }
        }
        return false
    }

    // flip tiles after a move is validated
    fun flipTiles(r: Int, c: Int, dr: Int, dc: Int) {
        val opponent = if (player == 1) 2 else 1

        var row = r + dr
        var col = c + dc

        // check for out-of-bounds indicies & opponent tile
        if (row !in 0..7 || col !in 0..7 || board[row][col] != opponent)
            return

        val tilesToFlip = mutableListOf<Pair<Int, Int>>()

        // Collect opponent tiles
        while (row in 0..7 && col in 0..7 && board[row][col] == opponent) {
            tilesToFlip.add(row to col)
            row += dr
            col += dc
        }

        // Once the player's piece is found, flip collected tiles
        if (row in 0..7 && col in 0..7 && board[row][col] == player) {
            for ((fr, fc) in tilesToFlip) {
                board[fr][fc] = player
            }
        }
    }

    // validate a move. triggers flipTiles() if called with flip as true
    fun valid(touchrow: Int, touchcol: Int, flip : Boolean) : Boolean {
        //must be an empty square
        if (board[touchrow][touchcol] != 0) return false

        // walk tiles in each direction to make sure the move is legal
        for ((dr, dc) in directions) {
            if (walkTiles(touchrow, touchcol, dr, dc)) {
                // flip tiles passed
                if(flip){
                    flipTiles(touchrow, touchcol, dr, dc)
                }
                return true
            }
        }

        return false
    }

    // check if valid moves exist
    fun hasLegalMoves(): Boolean {
        for (row in 0..7) {
            for (col in 0..7) {
                if (valid(row, col, false)) {
                    return true
                }
            }
        }
        return false
    }

    // called on every touch event triggered in BoardView ; progresses game
    fun touch(row: Int, col: Int) : Int {
        if (valid(row, col, true)){
            // update grid
            board[row][col] = player
            // switch players
            if(player == 1) player = 2
            else if(player == 2) player = 1
            if(!hasLegalMoves()){
                // flip turns if new player has no moves
                if(player == 1) player = 2
                else if(player == 2) player = 1
                if(!hasLegalMoves()){
                    // game over!
                    player = 0
                }
            }
        }
        return player
    }

    // return points of selected player
    fun points(playerPoints : Int) : Int {
        var p1p: Int = 0
        var p2p: Int = 0
        for (row in 0..7) {
            for (col in 0..7) {
                if (board[row][col] == 1) p1p++
                else if (board[row][col] == 2) p2p++
            }
        }
        if (playerPoints == 1) return p1p
        if (playerPoints == 2) return p2p
        return 0
    }

    fun clear(){ // resets board
        for (row in 0..7) {
            for (col in 0..7) {
                board[row][col] = 0
            }
        }
        // initiate with starting positions, 4 tiles in the center of the screen
        board[3][3] = 1
        board[3][4] = 2
        board[4][3] = 2
        board[4][4] = 1
        player = 1
    }
}