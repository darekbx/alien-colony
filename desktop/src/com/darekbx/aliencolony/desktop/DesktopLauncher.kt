package com.darekbx.aliencolony.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.darekbx.aliencolony.AlienColony

object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        LwjglApplication(AlienColony(), config)
    }
}
