package com.darekbx.aliencolony

import com.badlogic.gdx.Application
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.darekbx.aliencolony.dspr.BTS
import com.darekbx.aliencolony.dspr.MAP
import com.darekbx.aliencolony.dspr.SPR

class AlienColony : ApplicationAdapter() {

    var batch: SpriteBatch? = null
    var map: Texture? = null
    var slug: Texture? = null

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        batch = SpriteBatch()

        map = try {
            val bts = BTS(Gdx.files.internal("DESERT.BTS").readBytes())
            val map = MAP(Gdx.files.internal("HUMAN01.MAP").readBytes(), bts)
            val image = map.asBitmap()

            Texture(image)
        } catch (e: Exception) {
            Gdx.app.error("Core", "Unable to load map", e)
            Texture("badlogic.jpg")
        }

        slug = try {
            val spr = SPR(Gdx.files.internal("SLUG.SPR").readBytes())
            val sprimg = spr.frameAsImage(2, false, false)
            Texture(sprimg)
        } catch (e: Exception) {
            Gdx.app.error("Core", "Unable to load map", e)
            Texture("badlogic.jpg")
        }
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch?.run {
            begin()
            draw(map, 0f, 0f)
            draw(slug, 50f, 50f)
            end()
        }
    }

    override fun dispose() {
        batch?.dispose()
        map?.dispose()
        slug?.dispose()
    }
}
