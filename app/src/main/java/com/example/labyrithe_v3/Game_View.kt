package com.example.labyrithe_v3

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.labyrinth_v3.Ball
import java.util.Random

class Game_View(context: Context) : View(context), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private var ax = 0f
    private var ay = 0f
    private var screenWidth = 0
    private var screenHeight = 0
    private val random = Random()
    private val background: Drawable?
    private val win_point: Win_Point? = null
    private var labyrinth: Labyrinth? = null
    private var ball: Ball? = null

    init {
        setupScreenDimensions()
        initGameComponents()
        setupAccelerometer(context)
        background = ContextCompat.getDrawable(context, R.drawable.bg_labyrinth)
        setBackground(background)
    }

    private fun setupScreenDimensions() {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
    }

    private fun initGameComponents() {
        labyrinth = Labyrinth(screenWidth, screenHeight)
        ball = labyrinth!!.createBall(20)
    }

    private fun setupAccelerometer(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val wallThickness = 1
        labyrinth!!.draw(canvas, wallThickness)
        ball!!.drawBall(canvas)
    }


    override fun onSensorChanged(event: SensorEvent) {
        ax = event.values[0]
        ay = event.values[1]
        updateBallPosition()
        invalidate()
    }

    private fun updateBallPosition() {
        val speedFactorX = 3.0f
        val speedFactorY = 3.0f
        val dx = ax * -0.5f * speedFactorX
        val dy = ay * 0.5f * speedFactorY
        var newX = ball!!.positionX + dx
        var newY = ball!!.positionY + dy
        newX = Math.max(
            ball!!.radiusSize.toFloat(),
            Math.min(newX, (screenWidth - ball!!.radiusSize).toFloat())
        )
        newY = Math.max(
            ball!!.radiusSize.toFloat(),
            Math.min(newY, (screenHeight - ball!!.radiusSize).toFloat())
        )
        if (!labyrinth!!.isCollision(newX, newY, ball!!.radiusSize)) {
            ball!!.positionX = newX
            ball!!.positionY = newY
            checkForHole()
            checkForVictory()
        } else {
            handleCollision()
        }
    }

    private fun handleCollision() {
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event)
    }

    fun resume() {
        sensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    fun pause() {
        sensorManager!!.unregisterListener(this)
    }

    private fun checkForHole() {

        for (hole in labyrinth!!.holes) {
            val distance = Math.sqrt(
                Math.pow(
                    (ball!!.positionX - hole.centerX).toDouble(),
                    2.0
                ) + Math.pow((ball!!.positionY - hole.centerY).toDouble(), 2.0)
            )
            if (distance < ball!!.radiusSize + hole.holeRadius) {
                gameOver()
                return
            }
        }
    }

    private fun gameOver() {

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show()
            (context as Activity).finish()
        }
    }

    private fun checkForVictory() {

        val goal = labyrinth!!.win_point
        val distanceToGoal = Math.hypot((ball!!.positionX - goal!!.x).toDouble(), (ball!!.positionY - goal.y).toDouble())
        if (distanceToGoal < ball!!.radiusSize + goal.radius) {
            winGame()
        }
    }

    private fun winGame() {

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "Game Won", Toast.LENGTH_SHORT).show()
            (context as Activity).finish()
        }
    }
}