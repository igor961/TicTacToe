package com.me.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow

class MainActivity : AppCompatActivity() {
    private lateinit var layout: TableLayout
    private var cells = ArrayList<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContentView(layout)
    }

    fun init() {
        layout = TableLayout(this)
        val diff = layout.height - layout.width
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
                tr.addView(cell)
                cells.add(cell)
            }
            layout.addView(tr)
        }
    }
}
