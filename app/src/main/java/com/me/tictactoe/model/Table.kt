package com.me.tictactoe.model

class Table {
    private var freeCells = 9
    private val table: Array<Piece>
    var winner: Piece = Piece.N
        private set

    constructor() {
        this.table = Array(9) { Piece.N }
    }

    private constructor(table: Array<Piece>) {
        this.table = table
    }

    fun getTable() = table.clone()

    fun clone(): Table {
        return Table(getTable())
    }

    fun getI(i: Int): Piece {
        return this.table[i]
    }

    fun getIJ(i: Int, j: Int): Piece {
        return this.table[i * 3 + j]
    }

    fun setPiece(i: Int, p: Piece): Boolean {
        if (i >= 0 && this.table[i] == Piece.N) {
            this.table[i] = p
            this.winner = p
            this.freeCells--
            return true
        }
        return false
    }

    fun cellIsEmpty(i: Int): Boolean {
        return this.table[i] == Piece.N
    }

    fun validate(): Boolean {
        if (this.freeCells == 0) {
            this.winner = Piece.N
            return false
        }
        val sums = Array(8) { 0 }
        for (i in 0..2) {
            for (j in 0..2) {
                val value = this.getIJ(i, j).v
                //main diagonal
                if (i == j) sums[6] += value
                //second diagonal
                if (i == 2 - j) sums[7] += value
                //rows(0..2)
                sums[i] += value
                //columns(i=3..5)
                sums[3 + j] += value
            }
        }
        val max = sums.max()!!
        val min = sums.min()!!
        if (max >= 3 || min <= -3) return false
        return true
    }

    override fun toString(): String {
        var str = ""
        for (i in 0..2) {
            for (j in 0..2) {
                str += getIJ(i, j).n + "\t"
            }
            str += "\n"
        }
        return str
    }
}