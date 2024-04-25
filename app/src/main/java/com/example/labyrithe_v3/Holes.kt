package com.example.labyrinth_v3

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Holes(val centerX: Int, val centerY: Int, val holeRadius: Int) {
    // Propriétés de peinture pour dessiner le trou
    private val holePaint: Paint

    // Initialisation du pinceau pour dessiner le trou
    init {
        holePaint = Paint()
        holePaint.color = Color.WHITE
    }

    fun drawHole(canvas: Canvas) {
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), holeRadius.toFloat(), holePaint)
    }

    // Méthode pour vérifier si un point est à l'intérieur du trou
    fun containsPoint(px: Float, py: Float): Boolean {
        return Math.sqrt(
            Math.pow((px - centerX).toDouble(), 2.0) + Math.pow(
                (py - centerY).toDouble(),
                2.0
            )
        ) <= holeRadius
    }
}
