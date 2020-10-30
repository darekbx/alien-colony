package com.darekbx.aliencolony.dspr

import com.badlogic.gdx.graphics.Pixmap

class PixelCanvas(
    var width: Int,
    var height: Int,
    val doFlip: Boolean = false,
    var disX: Int = 0,
    var disY: Int = 0
) {

    var curX = 0
    var curY = 0

    var canvas: Pixmap

    init {
        if (disX > 0) {
            curX = disX
            width = width + disX
        }
        if (disY > 0) {
            curY = disY
            height = height + disY
        }

        canvas = Pixmap(width, height, Pixmap.Format.RGBA4444)
    }

    fun addPixel(color: Int) {
        when {
            doFlip -> canvas.drawPixel(width - 1 - curX + if (disX > 0) disX else 0, curY, color)
            else -> canvas.drawPixel(curX, curY, color)
        }
        next()
    }

    fun getImage() = canvas

    fun next() {
        curX++
        if (curX >= width) {
            curX = if (disX > 0) disX else 0
            curY++
        }
    }
}
