package com.me.tictactoe.controller

import com.me.tictactoe.controller.mapper.GameFactory
import com.me.tictactoe.model.Table
import com.me.tictactoe.view.TableMVCView


class Game(
    private val playWithComputer: Boolean,
    playX: Boolean = true,
    private val view: TableMVCView
) {
    private val t = Table()
    private val gameFactory = GameFactory(t)

    init {
        if (!playX) {
            view.blockInput()
            this.move({ gameFactory.doRandFirstMove() }, -1)
        }
    }

    fun loop(i: Int) {
        if (playWithComputer) {
            if (!move(gameFactory::doMove, i)) move({ gameFactory.doAutoMove() }, -1)
        } else move(gameFactory::doMove, i)
    }

    fun move(moveFun: (Int) -> Int, pos: Int): Boolean {
        val res = moveFun(pos)
        if (res >= 0) view.updateView(res, t.winner.n)
        val cont = t.validate()
        if (!cont) {
            view.showWinner(t.winner.n)
            return true
        }
        return false
    }
}