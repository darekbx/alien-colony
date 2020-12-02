package com.darekbx.aliencolony.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.utils.viewport.FitViewport
import com.darekbx.aliencolony.stages.GameStage

class GameScreen : Screen {

    var gameStage = GameStage(FitViewport(1024F, 762F))

    override fun show() {

        loadAssets()
    }

    override fun render(delta: Float) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        gameStage.draw()
    }

    override fun resize(width: Int, height: Int) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {
        gameStage.dispose()
    }

    private fun loadAssets() {
    }
}
