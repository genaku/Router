package com.genaku.navrouterbase

import com.genaku.router.AbstractFeatureRouter
import com.genaku.router.CommandQueue

/**
 * Feature router interface
 *
 * @author Gena Kuchergin
 */
class NavFeatureRouter(
    commandQueue: CommandQueue<NavCommand>
) : AbstractFeatureRouter<NavFeature, NavCommand>(commandQueue) {

    override fun getStartCommand(screen: NavFeature, uid: Long): NavCommand =
        Open(screen.destinationResId, uid)

    override fun getFinishCommand(uid: Long): NavCommand {
        val screen =
            screens[uid] ?: throw NoSuchElementException("Screen with uid = $uid not found")
        return BackAction(screen.finishActionResId)
    }
}