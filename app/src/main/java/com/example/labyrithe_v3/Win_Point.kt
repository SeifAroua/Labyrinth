package com.example.labyrithe_v3

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Win_Point(val x: Int, val y: Int, val radius: Int) {

    fun draw(canvas: Canvas, paint: Paint) {
        paint.color = Color.BLUE
        canvas.drawCircle(x.toFloat(), y.toFloat(), radius.toFloat(), paint)
    }
}