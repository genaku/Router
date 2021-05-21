package com.genaku.router

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Observe screen result
 *
 * @param coroutineScope - coroutine scope to receive data from result state flow when result is class of [T]
 * @param block - lambda function to process received result
 *
 * @author Gena Kuchergin
 */
inline fun <reified T : ScreenResult> Screen.observe(coroutineScope: CoroutineScope, crossinline block: (T) -> Unit) {
    coroutineScope.launch {
        resultStateFlow.collect {
            if (it is T) {
                block(it)
            }
        }
    }
}