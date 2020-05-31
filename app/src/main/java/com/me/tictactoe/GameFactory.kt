package com.me.tictactoe

import com.me.tictactoe.domain.Piece
import com.me.tictactoe.domain.Table

class GameFactory(private val usersChoice: Piece, private val table: Table) {
    var lastPiece = Piece.N
        private set

    fun doMove(i: Int): Int {
        val newPiece = Piece.opposite(lastPiece)
        if (table.setPiece(i, newPiece)) {
            this.lastPiece = newPiece
            return i
        }
        return -1
    }

    fun doAutoMove() = doMove(minimax(table, Piece.opposite(lastPiece)).first)

    private fun minimax(table: Table, piece: Piece): Pair<Int, Int> {
        if (!table.validate()) return Pair(-1, table.winner.v * piece.v)
        var move = -1
        var score = -2

        for (i in 0..8) {
            if (table.getI(i) == Piece.N) {
                var boardWithNewMove = table.clone()
                boardWithNewMove.setPiece(i, piece)
                var scoreForTheMove = -minimax(boardWithNewMove, Piece.opposite(piece)).second
                if (scoreForTheMove > score) {
                    score = scoreForTheMove
                    move = i
                }
            }
        }
        if (move == -1) {
            return Pair(-1, 0)
        }

        return Pair(move, score)
    }
}