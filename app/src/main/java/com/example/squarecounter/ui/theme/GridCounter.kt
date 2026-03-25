package com.example.squarecounter.ui.theme

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Rect
import android.view.View
import kotlin.math.min

class GridCounter(context: Context, attributeSet: AttributeSet?) : View(context, attributeSet){

    // holds the grid
    val grid = Array(8) { IntArray(8) { 0 } }

    init { // initiate with starting positions, 4 tiles in the center of the screen
        grid[3][3] = 1
        grid[3][4] = 2
        grid[4][3] = 2
        grid[4][4] = 1
    }
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

    private fun cellColor(value: Int): Int {
        // handpicked on https://rgbcolorpicker.com
        return when (value) {
            0 -> Color.WHITE                                   // white
            1 -> Color.rgb(128, 255, 132) // green
            2 -> Color.rgb(158, 255, 128)
            3 -> Color.rgb(189, 255, 128)
            4 -> Color.rgb(217, 255, 128)
            5 -> Color.rgb(246, 255, 128) // yellow
            6 -> Color.rgb(255, 226, 128)
            7 -> Color.rgb(255, 192, 128)
            8 -> Color.rgb(255, 167, 128)
            9 -> Color.rgb(255, 128, 128) // red
            else -> Color.WHITE
        }
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
                if (grid[row][col] == 0){} // empty cell
                else if (grid[row][col] == 1) { // black tile
                    fillPaint.color = Color.BLACK
                    canvas.drawCircle(left+(cellSize/2), top+(cellSize/2), (cellSize/2-3), fillPaint)
                }
                else if (grid[row][col] == 2){ // white tile
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
                grid[row][col] = (grid[row][col] + 1) % 3
                invalidate()
            }
        }
        return true
    }
}