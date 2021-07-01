package com.genaku.router

import java.util.*

interface ScreenParameters {
    fun getParametersOrNull(uuid: UUID): ScreenParams?
}