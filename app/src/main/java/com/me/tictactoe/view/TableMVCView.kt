package com.me.tictactoe.view

interface TableMVCView {
    fun blockInput()
    fun updateView(res: Int, n: String)
    fun showWinner(n: String)
}