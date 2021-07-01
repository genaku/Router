package com.genaku.alligatorrouter

import com.genaku.router.EmptyScreenResult
import com.genaku.router.ScreenParams
import com.genaku.router.ScreenResult
import kotlinx.coroutines.flow.MutableStateFlow

abstract class AbstractAlgScreen : AlgScreen {

    abstract override val params: ScreenParams

    override val resultStateFlow: MutableStateFlow<ScreenResult> = MutableStateFlow(
        EmptyScreenResult
    )
}