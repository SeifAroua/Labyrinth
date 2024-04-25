package com.example.labyrithe_v3

import android.app.Activity
import android.os.Bundle

// Activité principale du jeu
class Game_Activity : Activity() {

    private var game_View: Game_View? = null // Vue de jeu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Création de la vue de jeu et l'assigne au contenu de l'activité
        game_View = Game_View(this)
        setContentView(game_View)
    }

    override fun onResume() {
        super.onResume()

        // Reprise de la vue de jeu
        game_View?.resume()
    }

    override fun onPause() {
        super.onPause()

        // Pause de la vue de jeu
        game_View?.pause()
    }
}
