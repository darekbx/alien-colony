package com.darekbx.aliencolony.characters

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.darekbx.aliencolony.dspr.FIN
import com.darekbx.aliencolony.dspr.SPR
import com.darekbx.aliencolony.model.AnimationFrame
import java.util.*
import kotlin.concurrent.timerTask

class Slug {

    companion object {
        val SPRITE_NAME = "SPRITES/SLUG.SPR"
        val ANIMATE_NAME = "ANIMATE/SLUG.FIN"
    }

    private var texture: Texture
    private var sprite: Sprite

    private var animationFrames = mutableListOf<AnimationFrame>()

    var animationTimer: Timer? = null

    init {
        val fin = FIN(loadAssetBytes(ANIMATE_NAME))
        fin.animations.get("SLUGMOVE12")?.frames?.let { frames ->
            animationFrames = frames
        }

        val spr = SPR(loadAssetBytes(SPRITE_NAME))
        val sprimg = spr.frameAsImage(2, false, false)
        texture = Texture(sprimg)
        sprite = Sprite(texture)

        var index = 0

        animationTimer = Timer().apply {
            scheduleAtFixedRate(timerTask {

                val frame = animationFrames.get(index)

                val sprimg = spr.frameAsImage(frame.sprFrameNo, frame.isFlipped == 1, false)

                Gdx.app.postRunnable {
                    texture = Texture(sprimg)
                }

                if (animationFrames.size <= 1) {
                    animationTimer?.cancel()
                    return@timerTask
                }

                if (++index > animationFrames.size - 1) {
                    index = 0
                }

            }, 0, 120)
        }
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
        animationTimer?.cancel()

        texture.dispose()
    }

    private fun loadAssetBytes(assetName: String) =
            Gdx.files.internal(assetName).readBytes()

}
