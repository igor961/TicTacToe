package com.me.tictactoe.controller

import com.me.tictactoe.controller.mapper.GameFactory
import com.me.tictactoe.model.Piece
import com.me.tictactoe.model.Table


class Game(
    private val playWithComputer: Boolean,
    private val playX: Boolean = true,
    val updateView: (Int, String) -> Unit,
    val showDialog: (String) -> Unit
) {
    private val t = Table()
    private val gameFactory = GameFactory(t)

    init {
        if (!playX)
            this.move({ gameFactory.doAutoMove() }, -1)
    }

    fun loop(i: Int) {
        if (playWithComputer) {
            if (!move(gameFactory::doMove, i)) move({ gameFactory.doAutoMove() }, -1)
        } else move(gameFactory::doMove, i)
    }

    fun move(moveFun: (Int) -> Int, pos: Int): Boolean {
        val res = moveFun(pos)
        if (res >= 0) updateView(res, t.winner.n)
        val cont = t.validate()
        if (!cont) {
            showDialog(t.winner.n)
            return true
        }
        return false
    }
}