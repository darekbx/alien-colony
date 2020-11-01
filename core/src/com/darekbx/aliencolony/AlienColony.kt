package com.darekbx.aliencolony

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.darekbx.aliencolony.characters.Slug
import com.darekbx.aliencolony.dspr.BTS
import com.darekbx.aliencolony.dspr.MAP
import com.darekbx.aliencolony.dspr.SPR

class AlienColony : ApplicationAdapter(), InputProcessor {

    var batch: SpriteBatch? = null
    var map: Texture? = null
    lateinit var slug: Slug

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        batch = SpriteBatch()

        map = try {
            val bts = BTS(Gdx.files.internal("SCENARIO/DESERT.BTS").readBytes())
            val map = MAP(Gdx.files.internal("SCENARIO/HUMAN/HUMAN01.MAP").readBytes(), bts)
            val image = map.asBitmap()

            Texture(image)
        } catch (e: Exception) {
            Gdx.app.error("Core", "Unable to load map", e)
            Texture("badlogic.jpg")
        }

        slug = Slug()

        Gdx.input.setInputProcessor(this)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch?.run {
            begin()

            draw(map, 0f, 0f)

            slug.draw(this)

            end()
        }
    }

    override fun dispose() {
        batch?.dispose()
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
            slug.setPosition(x.toFloat(), y.toFloat())
        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int) = false

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int) = false

    override fun mouseMoved(screenX: Int, screenY: Int) = false

    override fun scrolled(amount: Int) = false
}
