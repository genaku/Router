package com.genaku.router

/**
 * Router
 *
 * Base navigation router interface
 *
 * @author Gena Kuchergin
 */
interface Router<T : Screen> {

    /**
     * Start screen will open screen
     *
     * @param screen
     */
    fun start(screen: T)

    /**
     * Finish screen with [uid]
     *
     * @param uid - screen unique id
     */
    fun finish(uid: Long)

    /**
     * Finish screen with [uid] sending result through router
     *
     * @param uid - screen unique id
     * @param result - screen result
     */
    fun finishWithResult(uid: Long, result: ScreenResult)

    /**
     * Get parameters of screen with [uid]
     *
     * @param uid - screen unique id
     * @return screen parameters or null if not found
     */
    fun getParametersOrNull(uid: Long): ScreenParams?
}