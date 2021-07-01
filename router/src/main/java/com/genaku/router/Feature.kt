package com.genaku.router

/**
 * Feature
 *
 * Represents Feature (independent feature module) for navigation (start and finish feature)
 *
 * @author Gena Kuchergin
 */
interface Feature: RouterScreen {

    /**
     * Is feature available (can be started?)
     */
    val isAvailable: Boolean
}