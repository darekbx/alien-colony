package com.darekbx.aliencolony.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.Array
import com.darekbx.aliencolony.dspr.FIN
import com.darekbx.aliencolony.dspr.SPR
import kotlin.math.abs

class Slug : Actor() {

    companion object {
        val SPRITE_NAME = "SPRITES/SLUG.SPR"
        val ANIMATE_NAME = "ANIMATE/SLUG.FIN"
    }

    private var texture: Texture
    private var sprite: Sprite

    private var slugMoveAnimation: Animation<TextureRegion>? = null
    var stateTime = 0F

    val fin = FIN(loadAssetBytes(ANIMATE_NAME))
    val spr = SPR(loadAssetBytes(SPRITE_NAME))

    enum class SlugMovement(val animationName: String) {
        DOWN("SLUGMOVE0"),
        DOWN_LEFT("SLUGMOVE2"),
        LEFT("SLUGMOVE4"),
        UP_LEFT("SLUGMOVE6"),
        UP("SLUGMOVE8"),
        UP_RIGHT("SLUGMOVE10"),
        RIGHT("SLUGMOVE12"),
        DOWN_RIGHT("SLUGMOVE14")
    }

    var actualX = 10F
    var actualY = 10F

    init {
        val sprimg = spr.frameAsImage(2, false, false)
        texture = Texture(sprimg)
        sprite = Sprite(texture)

        animate(SlugMovement.LEFT)

        setPosition(10F, 10F)

        actualX = 10F
        actualY = 10F
    }

    override fun setPosition(x: Float, y: Float, alignment: Int) {
        val deltaX = abs(x - actualX)
        val deltaY = abs(y - actualY)
        when {
            deltaX < deltaY -> {
                // Move UP / DOWN
                when {
                    y - actualY > 0 -> {
                        // Move UP
                        animate(SlugMovement.UP)
                    }
                    else -> {
                        // Move DOWN
                        animate(SlugMovement.DOWN)
                    }
                }
            }
            else -> {
                // Move LEFT / RIGHT
                when {
                    x - actualX > 0 -> {
                        // Move RIGHT
                        animate(SlugMovement.RIGHT)
                    }
                    else -> {
                        // Move LEFT
                        animate(SlugMovement.LEFT)
                    }
                }
            }
        }

        super.setPosition(x, y, alignment)
        actualX = x
        actualY = y
    }

    fun animate(slugMovement: SlugMovement) {
        val slugFrames = Array<TextureRegion>()
        fin.animations.get(slugMovement.animationName)?.frames?.forEach { frame->
            val frameImage = spr.frameAsImage(frame.sprFrameNo, frame.isFlipped == 1, false)
            slugFrames.add(TextureRegion(Texture(frameImage)))
        }
        slugMoveAnimation = Animation(0.083F, slugFrames)
    }

    override fun getWidth() = sprite.width

    override fun getHeight() = sprite.height

    override fun draw(batch: Batch?, parentAlpha: Float) {
        stateTime += Gdx.graphics.getDeltaTime()
        batch?.let {
            slugMoveAnimation?.getKeyFrame(stateTime, true)?.let {
                batch.draw(it, x, y)
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
