package com.genaku.router

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Screen
 *
 * Represents any screen for navigation
 *
 * @author Gena Kuchergin
 */
interface Screen {

    /**
     * Parameters for screen
     */
    val params: ScreenParams

    /**
     * State flow with result returned from the screen on finish
     */
    val resultStateFlow: MutableStateFlow<ScreenResult>
}