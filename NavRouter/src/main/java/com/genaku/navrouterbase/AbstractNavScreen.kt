package com.genaku.navrouterbase

import com.genaku.router.EmptyScreenResult
import com.genaku.router.ScreenParams
import com.genaku.router.ScreenResult
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Abstract nav screen
 *
 * @property destinationResId - ResId of screen start destination in nav graph
 *
 * @author Gena Kuchergin
 */
abstract class AbstractNavScreen(override val destinationResId: Int): NavScreen {

    abstract override val params: ScreenParams

    override val resultStateFlow: MutableStateFlow<ScreenResult> = MutableStateFlow(
        EmptyScreenResult
    )
}