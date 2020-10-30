package com.darekbx.aliencolony.dspr

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.darekbx.aliencolony.model.Frame

class BTS(fileData: ByteArray) : GameFile(fileData) {

    private var palette = mutableListOf<IntArray>()
    private var frames = mutableMapOf<Int, Frame>()

    private var noImages: Int

    init {
        this.noImages = this.shortAt(4)

        extractColorPalette()
        extractFrames()
    }

    private fun extractColorPalette() {
        for (i in 0 until 256) {
            palette.add(
                    intArrayOf(
                            (byteAt(8 + i * 3 + 0L) * 4 + 3),
                            (byteAt(8 + i * 3 + 1L) * 4 + 3),
                            (byteAt(8 + i * 3 + 2L) * 4 + 3)
                    )
            )
        }
    }

    private fun extractFrames() {
        for (f in 0 until noImages) {
            var frame = Frame().apply {
                no = f
                start = 776L + 1028 * f
            }
            val index = this.longAt(frame.start).toInt()
            this.frames.put(index, frame)
        }
    }

    fun rgb(id: Int) = this.palette[id]

    fun getPalette() = this.palette

    fun getFrameCount() = this.noImages

    fun frameAsImage(f: Int, doFlip: Boolean): Pixmap {
        var frameStart = 776 + 1028 * f
        val canvas = PixelCanvas(32, 32, doFlip)

        var c = frameStart + 4L
        while (c < (frameStart + 1028L)) {
            var x = this.byteAt(c)
            var rgb = this.rgb(x)
            if (rgb[0] != 255 && rgb[1] != 3 && rgb[2] != 255) {
                canvas.addPixel(Color.toIntBits(255, rgb[2], rgb[1], rgb[0]))
            } else {
                canvas.next()
            }
            c++
        }

        return canvas.getImage()
    }

    fun frameAsImageAtFID(fid: Int, doFlip: Boolean): Pixmap? {
        if (!this.frames.containsKey(fid)) {
            return null
        }
        var frame = this.frames[fid]
        return this.frameAsImage(frame?.no ?: 0, doFlip)
    }

    fun frameAsCanvasAtFID(fid: Int, doFlip: Boolean) =
        frameAsImageAtFID(fid, doFlip)
}
