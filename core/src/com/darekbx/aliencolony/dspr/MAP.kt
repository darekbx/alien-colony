package com.darekbx.aliencolony.dspr

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap

class MAP(fileData: ByteArray, val bts: BTS) : GameFile(fileData) {

    private var tileCache = mutableMapOf<String, Pixmap>()

    var width = 0L
    var height = 0L

    init {
        this.width = this.longAt(0)
        this.height = this.longAt(4)
    }

    fun getCollisionArea(): Pixmap {
        val pixmap = createBitmap()
        pixmap.setColor(Color.BLACK)

        var noTiles = this.width * this.height
        var curX = 0
        var curY = 0

        for (t in 0 until noTiles) {
            var fpos = 8 + noTiles * 4 + t * 2
            var flagByte = this.byteAt(fpos)
            var x1 = (flagByte shr 1) and 1
            var x2 = (flagByte shr 2) and 1
            var blockViewAhead = (flagByte shr 7) and 1
            var x8 = (flagByte shr 8) and 1

            if (x1 == 1 || x2 == 1 || x8 == 1 || blockViewAhead != 1) {
                val x = curX * 32
                val y = curY * 32
                pixmap.fillRectangle(x, y, 32, 32)
            }

            curX++
            if (curX >= this.width) {
                curX = 0
                curY++
            }
        }

        return pixmap
    }

    fun asBitmap(): Pixmap {
        val pixmap = createBitmap()
        var noTiles = this.width * this.height
        var curX = 0
        var curY = 0

        tileCache.clear()

        for (t in 0 until noTiles) {
            var mpos = 8 + t * 4
            var mainTile = this.shortAt(mpos)
            var topTile = this.shortAt(mpos + 2)
            var fpos = 8 + noTiles * 4 + t * 2
            var flagByte = this.byteAt(fpos)
            var doFlipMain = (flagByte shr 5) and 1
            var doFlipTop = (flagByte shr 6) and 1

            drawImage(mainTile, doFlipMain, pixmap, curX, curY)

            if (topTile > 0) {false
                drawImage(topTile, doFlipTop, pixmap, curX, curY)
            }

            curX++
            if (curX >= this.width) {
                curX = 0
                curY++
            }
        }

        return pixmap
    }

    private fun drawImage(fid: Int, doFlip: Int, oContext: Pixmap, curX: Int, curY: Int) {
        val cacheMainTileKey = "$fid${doFlip == 1}"
        tileCache.get(cacheMainTileKey)?.let {
            oContext.drawPixmap(it, curX * 32, curY * 32)
        } ?: run {
            val oMainTile = this.bts.frameAsImageAtFID(fid, doFlip == 1)
            if (oMainTile != null) {
                oContext.drawPixmap(oMainTile, curX * 32, curY * 32)
                tileCache.put(cacheMainTileKey, oMainTile)
            }
        }
    }

    private fun createBitmap(): Pixmap {
        val canvasWidth = width.toInt() * 32
        val canvasHeight = height.toInt() * 32
        return Pixmap(canvasWidth, canvasHeight, Pixmap.Format.RGBA4444)
    }
}
