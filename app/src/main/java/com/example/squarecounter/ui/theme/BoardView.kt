package com.example.squarecounter.ui.theme

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.view.View
import kotlin.math.min

class BoardView(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet){

    // reference for the game state
    var board: Othello? = null

    var size = 0f
    var cellSize = 0f
    var offsetX = 0f
    var offsetY = 0f

    // shared values to center the grid
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        size = min(w, h).toFloat()
        cellSize = size / 8f
        offsetX = (w - size) / 2f
        offsetY = (h - size) / 2f
    }

    // paint styles
    val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.LTGRAY
        strokeWidth = 4f
    }
    val textPaint = Paint().apply {
        textAlign = Paint.Align.CENTER
        color = Color.GRAY
    }

    private val fillPaint = Paint().apply { style = Paint.Style.FILL }


    // draw the grid
    override fun onDraw(canvas: Canvas){
        super.onDraw(canvas)

        // text
        for (row in 0..7){
            for (col in 0..7){
                // cell
                val left = offsetX + col * cellSize
                val top = offsetY + row * cellSize
                val right = left + cellSize
                val bottom = top + cellSize
                fillPaint.color = Color.rgb(37, 117, 23)
                canvas.drawRect(left, top, right, bottom, fillPaint)

                // draw tile if applicable
                val state = board?.getCellState(row, col)
                if (state == 0){} // empty cell
                else if (state == 1) { // black tile
                    fillPaint.color = Color.BLACK
                    canvas.drawCircle(left+(cellSize/2), top+(cellSize/2), (cellSize/2-3), fillPaint)
                }
                else if (state == 2){ // white tile
                    fillPaint.color = Color.WHITE
                    canvas.drawCircle(left+(cellSize/2), top+(cellSize/2), (cellSize/2-3), fillPaint)
                }
            }
        }

        // lines
        for (i in 0..7) {
            val posX = offsetX + i * cellSize
            val posY = offsetY + i * cellSize

            canvas.drawLine(offsetX, posY, offsetX + size, posY, linePaint)   // horizontal
            canvas.drawLine(posX, offsetY, posX, offsetY + size, linePaint)   // vertical
        }
    }

    // touch event
    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_DOWN) {
            val size = min(width, height)
            val cellSize = size / 8

            // relate touch position to grid position
            val col = ((event.x - offsetX) / cellSize).toInt()
            val row = ((event.y - offsetY) / cellSize).toInt()

            // re-trigger the draw
            if (col in 0..7 && row in 0..7) {
                // grid[row][col] = (grid[row][col] + 1) % 3 // handled by game class
                invalidate()
            }
        }
        return true
    }
}