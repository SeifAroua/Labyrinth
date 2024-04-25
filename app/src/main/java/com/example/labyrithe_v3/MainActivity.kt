package com.example.labyrithe_v3


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val startButton = findViewById<Button>(R.id.Play_button)
        startButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Game_Activity::class.java)
            startActivity(intent)
        }
    }
}