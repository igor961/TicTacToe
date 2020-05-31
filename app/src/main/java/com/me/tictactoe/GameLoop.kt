package com.me.tictactoe

import com.me.tictactoe.domain.Piece
import com.me.tictactoe.domain.Table


class Game(private val playWithComputer: Boolean, playX: Boolean = true) {
    private val t = Table()
    private val piece = if (playX) Piece.X else Piece.O
    private val gameFactory = GameFactory(piece, t)

    private inline fun playWithComputer(
        humanMove: Boolean,
        computerMoveFun: () -> Int,
        humanMoveFun: () -> Int
    ) {
        if (humanMove) humanMoveFun() else computerMoveFun()
    }

    fun loop(i: Int, updateView: (Int, String) -> Unit): Piece? {
        fun humanMove() {
            gameFactory.doMove(i)
            updateView(i, piece.n)
        }

        var res = if (move(::humanMove)) piece else null
        if (playWithComputer) res = if (move {
            val res = gameFactory.doAutoMove()
            if (res >= 0) updateView(res, Piece.opposite(piece).n)
        }) piece else null

        return res
    }

    private fun move(block: () -> Unit): Boolean {
        if (t.validate()) {
            block()
            return false
        }
        return true
    }
}