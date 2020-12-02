package com.darekbx.aliencolony

import com.badlogic.gdx.*
import com.darekbx.aliencolony.screens.GameScreen

class AlienColony : ApplicationAdapter() {

    lateinit var gameScreen: GameScreen

    override fun create() {
        super.create()

        gameScreen = GameScreen()
    }

    override fun render() {
        super.render()

        gameScreen.render(Gdx.graphics.getDeltaTime())
    }
}
