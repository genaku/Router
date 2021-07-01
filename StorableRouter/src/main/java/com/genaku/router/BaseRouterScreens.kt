package com.genaku.router

import java.util.*
import java.util.concurrent.ConcurrentHashMap

open class BaseRouterScreens<S : RouterScreen> : RouterScreens<S> {

    private val screens: MutableMap<UUID, S> = ConcurrentHashMap<UUID, S>()

    override fun addScreen(screen: S): UUID {
        val uuid = UUID.randomUUID()
        screens[uuid] = screen
        return uuid
    }

    override fun deleteScreen(uuid: UUID) {
        log("before delete: $screens")
        screens.remove(uuid)
        log("after delete: $screens")
    }

    override fun getScreenOrNull(uuid: UUID): S? = screens[uuid]

    override fun getAllScreens(): Map<UUID, S> {
        log("getAllScreens: $screens")
        return Collections.unmodifiableMap(screens)
    }

    override fun addScreens(newScreens: Map<UUID, S>) {
        if (newScreens.isNotEmpty()) {
            screens.putAll(newScreens)
        }
    }

    override fun getParametersOrNull(uuid: UUID): ScreenParams? = screens[uuid]?.params

    private fun log(msg: String) {
        println("TAF [${Thread.currentThread()}] $msg")
    }
}