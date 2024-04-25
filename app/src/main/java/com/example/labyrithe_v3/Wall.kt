package com.example.labyrinth_v3

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class Wall(val topLeftX: Int, val topLeftY: Int, val wallWidth: Int, val wallHeight: Int) {
    private val wallPaint: Paint

    init {
        wallPaint = Paint()
        wallPaint.color = Color.YELLOW
    }

    fun drawWall(canvas: Canvas, thickness: Int) {

        val adjustedTopLeftX = topLeftX + thickness / 2
        val adjustedTopLeftY = topLeftY + thickness / 2
        val adjustedWallWidth = wallWidth - thickness
        val adjustedWallHeight = wallHeight - thickness


        val wallRect = Rect(topLeftX, topLeftY, topLeftX + wallWidth, topLeftY + wallHeight)
        canvas.drawRect(wallRect, wallPaint)
    }


    fun containsPoint(px: Int, py: Int): Boolean {
        return px >= topLeftX && px <= topLeftX + wallWidth && py >= topLeftY && py <= topLeftY + wallHeight
    }
}
