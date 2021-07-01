package com.genaku.router

import java.util.*

interface RouterScreens<S : RouterScreen> : ScreenParameters {
    fun addScreen(screen: S): UUID
    fun deleteScreen(uuid: UUID)
    fun getScreenOrNull(uuid: UUID): S?
    fun getAllScreens(): Map<UUID, S>
    fun addScreens(newScreens: Map<UUID, S>)
}