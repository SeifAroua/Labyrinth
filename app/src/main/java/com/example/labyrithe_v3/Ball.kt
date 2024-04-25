package com.example.labyrinth_v3

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Ball(@JvmField var positionX: Float, @JvmField var positionY: Float, @JvmField val radiusSize: Int) {
    private val ballPaint: Paint // Peinture utilisée pour dessiner la balle

    init {
        ballPaint = Paint()
        ballPaint.color = Color.RED // Couleur de la balle (rouge par défaut)
    }

    // Méthode pour dessiner la balle sur le canvas
    fun drawBall(canvas: Canvas) {
        canvas.drawCircle(positionX, positionY, radiusSize.toFloat(), ballPaint)
    }

    // Méthode pour mettre à jour la position de la balle en fonction des déplacements
    fun updateBallPosition(deltaX: Float, deltaY: Float) {
        positionX += deltaX
        positionY += deltaY
    }
}
