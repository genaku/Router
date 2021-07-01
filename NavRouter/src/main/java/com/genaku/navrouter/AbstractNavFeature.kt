package com.genaku.navrouter

/**
 * Abstract nav feature
 *
 * @property finishActionResId - ResId of action to finish feature nav graph
 *
 * @author Gena Kuchergin
 */
abstract class AbstractNavFeature(destinationResId: Int, override val finishActionResId: Int) :
    AbstractNavScreen(destinationResId), NavFeature {

    override val isAvailable: Boolean = true
}