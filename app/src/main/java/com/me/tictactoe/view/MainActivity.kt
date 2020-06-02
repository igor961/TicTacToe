package com.me.tictactoe.view

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.me.tictactoe.controller.Game
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), TableMVCView {
    private lateinit var layout: TableLayout
    private var cells = ArrayList<Button>()
    private val enabled = Array(9) { true }
    override lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(layout)
        buildStartDialog()
    }

    private fun buildStartDialog() {
        val vars = arrayOf("X", "O")
        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setTitle("Choose your weapon")
        dialog.setSingleChoiceItems(vars, -1) { d, i ->
            thread {
                startGame(i == 0)
            }
            d.dismiss()
        }
        dialog.create().show()
    }

    fun setPiece(pos: Int, cont: String) {
        runOnUiThread {
            cells[pos].text = cont
            this.enabled[pos] = false
            this.cells.forEachIndexed { i, c ->
                c.isEnabled = this.enabled[i]
            }
        }
    }

    fun init() {
        layout = TableLayout(this)
        layout.setPadding(10, 10, 10, 10)
        val param1 = TableLayout.LayoutParams()
        val param2 = TableRow.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        param1.weight = 1f
        param2.weight = 1f
        for (i in 0..2) {
            val tr = TableRow(this)
            tr.layoutParams = param1
            for (j in 0..2) {
                val cell = Button(this)
                cell.layoutParams = param2
                cell.tag = i * 3 + j
                cell.setOnClickListener(::cellOnClickListener)
                tr.addView(cell)
                cells.add(cell)
            }
            layout.addView(tr)
        }
    }

    private fun disableCells() {
        runOnUiThread {
            this.cells.forEachIndexed { i, c ->
                this.enabled[i] = c.isEnabled
                c.isEnabled = false
            }
        }
    }

    fun cellOnClickListener(v: View) {
        val pos = Integer.parseInt(v.tag.toString())
        disableCells()
        thread {
            game.loop(pos)
        }
    }

    override fun blockInput() = disableCells()

    override fun updateView(res: Int, n: String) = setPiece(res, n)

    override fun showWinner(n: String) {
        runOnUiThread {
            val dBuilder = AlertDialog.Builder(this)
            val msg = if (n == "N") "This is a draw" else "$n is winner!"
            dBuilder.setCancelable(true).setMessage(msg)
            dBuilder.setNegativeButton("Cancel") { d, _ ->
                d.cancel()
            }
            dBuilder.setPositiveButton("New game") { _, _ ->
                recreate()
            }
            dBuilder.create().show()
        }
    }
}
