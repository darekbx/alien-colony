package com.darekbx.aliencolony

import com.badlogic.gdx.*
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import com.darekbx.aliencolony.characters.Slug
import com.darekbx.aliencolony.dspr.BTS
import com.darekbx.aliencolony.dspr.MAP

class AlienColony : ApplicationAdapter(), InputProcessor {

    var batch: SpriteBatch? = null
    var map: Texture? = null

    lateinit var stage: Stage
    lateinit var slug: Slug

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        batch = SpriteBatch()
        stage = Stage(FitViewport(1024F, 762F), batch)

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
        stage.addActor(Image(map))
        stage.addActor(slug)

        Gdx.input.setInputProcessor(this)
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(Gdx.graphics.getDeltaTime())
        stage.draw()
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

            Gdx.app.log("------", "${slug.getWidth()}")
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
