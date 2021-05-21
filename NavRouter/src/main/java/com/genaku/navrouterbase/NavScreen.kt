package com.genaku.navrouterbase

import com.genaku.router.Screen

/**
 * Screen to use in Navigation Component router
 *
 * @author Gena Kuchergin
 */
interface NavScreen : Screen {

    /**
     * ResId of screen start destination in nav graph
     */
    val destinationResId: Int
}