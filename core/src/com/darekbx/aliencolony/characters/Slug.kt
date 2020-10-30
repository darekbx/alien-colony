package com.darekbx.aliencolony.characters

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.darekbx.aliencolony.dspr.SPR

class Slug {

    companion object {
        val ASSET_NAME = "SLUG.SPR"
    }

    private var texture: Texture
    private var sprite: Sprite

    init {
        val spr = SPR(loadAssetBytes())
        val sprimg = spr.frameAsImage(2, false, false)
        texture = Texture(sprimg)
        sprite = Sprite(texture)
    }

    fun draw(batch: SpriteBatch) {
        sprite.draw(batch)
    }

    fun setPosition(x: Float, y: Float) {
        sprite.setPosition(
                x - sprite.getWidth() / 2,
                y - sprite.getHeight() / 2
        )
    }

    fun dispose() {
        texture.dispose()
    }

    private fun loadAssetBytes() = Gdx.files.internal(ASSET_NAME).readBytes()

}
