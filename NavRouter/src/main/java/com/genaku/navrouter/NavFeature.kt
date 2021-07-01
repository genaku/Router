package com.genaku.navrouter

import com.genaku.router.Feature

/**
 * Feature navigation info to use in Navigation Component router
 *
 * @author Gena Kuchergin
 */
interface NavFeature: NavScreen, Feature {

    /**
     * ResId of action to finish feature nav graph
     */
    val finishActionResId: Int
}