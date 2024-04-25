package com.example.labyrithe_v3

import android.graphics.Canvas
import android.graphics.Paint
import com.example.labyrinth_v3.Ball
import com.example.labyrinth_v3.Holes
import com.example.labyrinth_v3.Wall
import java.util.Random

class Labyrinth(private val width: Int, private val height: Int) {
    val walls: ArrayList<Wall>
    val holes: ArrayList<Holes>
    var win_point: Win_Point? = null
        private set
    private val random = Random()

    init {
        walls = ArrayList()
        holes = ArrayList()
        initializeLabyrinth()
    }

    fun draw(canvas: Canvas, wallThickness: Int) {
        for (wall in walls) {
            wall.drawWall(canvas, wallThickness) // Pass the wall thickness here
        }
        for (hole in holes) {
            hole.drawHole(canvas)
        }
        win_point!!.draw(canvas, Paint())
    }

    private fun initializeLabyrinth() {
        createWalls(10)
        createHoles(10)
        createWin_Point()
    }

    private fun createWin_Point() {
        var x: Int
        var y: Int
        do {
            x = random.nextInt(width - 30) + 15
            y = random.nextInt(height - 30) + 15
        } while (checkOverlap(x, y, 15))
        win_point = Win_Point(x, y, 40)
    }

    private fun createWalls(count: Int) {
        for (i in 0 until count) {
            val wallWidth = 100 // Largeur fixe des murs
            val wallHeight = 50 // Hauteur fixe des murs
            val x = random.nextInt(width - wallWidth)
            val y = random.nextInt(height - wallHeight)
            if (!checkOverlap(x, y, Math.max(wallWidth, wallHeight) / 2)) {
                walls.add(Wall(x, y, wallWidth, wallHeight))
            }
        }
    }


    private fun createHoles(count: Int) {
        for (i in 0 until count) {
            val radius = 30
            var x: Int
            var y: Int
            var collision: Boolean
            do {
                collision = false
                x = random.nextInt(width - radius * 2) + radius
                y = random.nextInt(height - radius * 2) + radius
                if (checkOverlap(x, y, radius)) {
                    collision = true
                }
            } while (collision)
            holes.add(Holes(x, y, radius))
        }
    }

    fun checkOverlap(x: Int, y: Int, radius: Int): Boolean {
        for (wall in walls) {
            if (x + radius > wall.topLeftX && x - radius < wall.topLeftX + wall.wallWidth && y + radius > wall.topLeftY && y - radius < wall.topLeftY + wall.wallHeight) {
                return true
            }
        }
        for (hole in holes) {
            val distance = Math.sqrt(
                Math.pow((x - hole.centerX).toDouble(), 2.0) + Math.pow(
                    (y - hole.centerY).toDouble(),
                    2.0
                )
            )
            if (distance < radius + hole.holeRadius) {
                return true
            }
        }
        return false
    }

    fun createBall(ballRadius: Int): Ball {
        var x: Int
        var y: Int
        var valid: Boolean
        do {
            valid = true
            x = random.nextInt(width - ballRadius * 2) + ballRadius
            y = random.nextInt(height - ballRadius * 2) + ballRadius
            if (checkOverlap(x, y, ballRadius) || isCollision(
                    x.toFloat(),
                    y.toFloat(),
                    ballRadius
                )
            ) {
                valid = false
            }
        } while (!valid)
        return Ball(x.toFloat(), y.toFloat(), ballRadius)
    }

    fun isCollision(newX: Float, newY: Float, radius: Int): Boolean {
        for (wall in walls) {
            if (newX + radius > wall.topLeftX && newX - radius < wall.topLeftX + wall.wallWidth && newY + radius > wall.topLeftY && newY - radius < wall.topLeftY + wall.wallHeight) {
                return true
            }
        }
        return false
    }
}