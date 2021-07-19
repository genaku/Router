package com.genaku.router

import android.os.Bundle
import android.util.Log
import java.io.Serializable
import java.util.*

class StorableRouterScreens<S : RouterScreen>(
    private val key: String = DEFAULT_KEY
) : BaseRouterScreens<S>(), StorableInstanceState {

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val data = savedInstanceState.getSerializable(key) as StorableRouterScreens<S>.ScreensData?
        if (data == null) {
            addScreens(emptyMap())
        } else {
            log("restore ${data.screens}")
            addScreens(data.screens)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val data = ScreensData(
            getAllScreens()
        )
        log("save ${data.screens}")
        if (data.screens.isNotEmpty()) {
            outState.putSerializable(key, data)
        }
    }

    private fun log(msg: String) {
        Log.d("TAF", "[${Thread.currentThread()}] $msg")
    }

    inner class ScreensData(val screens: Map<UUID, S>) : Serializable

    companion object {
        private const val DEFAULT_KEY = "RouterScreens"
    }
}