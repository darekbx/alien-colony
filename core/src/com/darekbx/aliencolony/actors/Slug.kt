package com.darekbx.aliencolony.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Array
import com.darekbx.aliencolony.dspr.FIN
import com.darekbx.aliencolony.dspr.SPR

class Slug : Actor() {

    companion object {
        val SPRITE_NAME = "SPRITES/SLUG.SPR"
        val ANIMATE_NAME = "ANIMATE/SLUG.FIN"
    }

    private var texture: Texture
    private var sprite: Sprite

    private var slugMove1Animation: Animation<TextureRegion>? = null
    private var slugMove2Animation: Animation<TextureRegion>? = null
    var stateTime = 0F

    var movementMode = true

    init {
        val fin = FIN(loadAssetBytes(ANIMATE_NAME))
        val spr = SPR(loadAssetBytes(SPRITE_NAME))
        val sprimg = spr.frameAsImage(2, false, false)
        texture = Texture(sprimg)
        sprite = Sprite(texture)

        val slugFrames = Array<TextureRegion>()
        fin.animations.get("SLUGMOVE12")?.frames?.forEach{ frame->
            val frameImage = spr.frameAsImage(frame.sprFrameNo, frame.isFlipped == 1, false)
            slugFrames.add(TextureRegion(Texture(frameImage)))
        }
        slugMove1Animation = Animation(0.083F, slugFrames)

        val slugFrames2 = Array<TextureRegion>()
        fin.animations.get("SLUGMOVE0")?.frames?.forEach{ frame->
            val frameImage = spr.frameAsImage(frame.sprFrameNo, frame.isFlipped == 1, false)
            slugFrames2.add(TextureRegion(Texture(frameImage)))
        }
        slugMove2Animation = Animation(0.083F, slugFrames2)

        setPosition(10F, 10F)
    }

    override fun getWidth() = sprite.width

    override fun getHeight() = sprite.height

    override fun draw(batch: Batch?, parentAlpha: Float) {
        stateTime += Gdx.graphics.getDeltaTime()
        batch?.let {
            if (movementMode) {
                slugMove1Animation?.getKeyFrame(stateTime, true)?.let {
                    batch.draw(it, x, y)
                }
            } else {
                slugMove2Animation?.getKeyFrame(stateTime, true)?.let {
                    batch.draw(it, x, y)
                }
            }
        }
    }

    fun changeSprite() {
        stateTime = 0F
        //movementMode = !movementMode
    }

    fun dispose() {
        texture.dispose()
    }

    private fun loadAssetBytes(assetName: String) =
            Gdx.files.internal(assetName).readBytes()
}
