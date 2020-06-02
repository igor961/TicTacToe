package com.me.tictactoe.view

import com.me.tictactoe.controller.Game

interface TableMVCView {
    var game: Game
    fun blockInput()
    fun updateView(res: Int, n: String)
    fun showWinner(n: String)

    fun startGame(x: Boolean) {
        game = Game(true, x, this)
    }
}