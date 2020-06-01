package com.me.tictactoe.model

enum class Piece(val v: Int, val n: String) {
    X(1, "X"), N(0, "N"), O(-1, "O");

    companion object {
        fun opposite(p: Piece) = if (p == X) O else X
    }
}