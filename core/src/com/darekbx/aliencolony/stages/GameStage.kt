package com.darekbx.aliencolony.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.Viewport
import com.darekbx.aliencolony.actors.Slug
import com.darekbx.aliencolony.dspr.BTS
import com.darekbx.aliencolony.dspr.MAP

class GameStage constructor(viewport: Viewport) : Stage(), InputProcessor {

    var map: Texture? = null
    lateinit var slug: Slug

    init {

        loadAssets()

        addActor(slug)

        Gdx.input.setInputProcessor(this)
    }

    fun loadAssets() {
        slug = Slug()

        map = try {
            val bts = BTS(Gdx.files.internal("SCENARIO/DESERT.BTS").readBytes())
            val map = MAP(Gdx.files.internal("SCENARIO/HUMAN/HUMAN01.MAP").readBytes(), bts)
            val image = map.asBitmap()

            Texture(image)
        } catch (e: Exception) {
            Gdx.app.error("Core", "Unable to load map", e)
            Texture("badlogic.jpg")
        }
    }

    override fun draw() {

        with(batch) {
            begin()
            draw(map, 0F, 0F)
            end()
        }

        act(Gdx.graphics.getDeltaTime())
        super.draw()
    }

    override fun dispose() {
        super.dispose()

        map?.dispose()
        slug?.dispose()
    }

    override fun keyDown(keycode: Int) = false

    override fun keyUp(keycode: Int) = false

    override fun keyTyped(character: Char) = false

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (button == Input.Buttons.LEFT) {
            val x = screenX
            val y = Gdx.graphics.getHeight() - screenY

            slug.changeSprite()
            slug.addAction(
                    Actions.moveTo(
                            x.toFloat() + slug.getWidth() / 2,
                            y.toFloat() + slug.getHeight() / 2)
            )

        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int) = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int) = false

    override fun mouseMoved(screenX: Int, screenY: Int) = false

    override fun scrolled(amount: Int) = false
}
