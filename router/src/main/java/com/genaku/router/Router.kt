package com.genaku.router

import java.util.*

/**
 * Router
 *
 * Base navigation router interface
 *
 * @author Gena Kuchergin
 */
interface Router<T : RouterScreen> {

    /**
     * Start screen will open screen
     *
     * @param screen
     */
    fun start(screen: T)

    /**
     * Finish screen with [uuid]
     *
     * @param uuid - screen unique id
     */
    fun finish(uuid: UUID)

    /**
     * Finish screen with [uuid] sending result through router
     *
     * @param uuid - screen unique id
     * @param result - screen result
     */
    fun finishWithResult(uuid: UUID, result: ScreenResult)

    /**
     * Get parameters of screen with [uuid]
     *
     * @param uuid - screen unique id
     * @return screen parameters or null if not found
     */
    fun getParametersOrNull(uuid: UUID): ScreenParams?
}