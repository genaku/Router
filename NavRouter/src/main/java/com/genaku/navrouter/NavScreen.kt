package com.genaku.navrouter

import com.genaku.router.RouterScreen
import com.genaku.router.ScreenResult
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.Serializable

/**
 * Screen to use in Navigation Component router
 *
 * @author Gena Kuchergin
 */
interface NavScreen : RouterScreen, Serializable {

    /**
     * ResId of screen start destination in nav graph
     */
    val destinationResId: Int
}