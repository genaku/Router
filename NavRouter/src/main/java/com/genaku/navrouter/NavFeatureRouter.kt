package com.genaku.navrouter

import com.genaku.router.AbstractFeatureRouter
import com.genaku.router.CommandQueue
import com.genaku.router.RouterScreens
import java.util.*
import kotlin.NoSuchElementException

/**
 * Feature router interface
 *
 * @author Gena Kuchergin
 */
open class NavFeatureRouter(
    commandQueue: CommandQueue<NavCommand>,
    routerScreens: RouterScreens<NavFeature>
) : AbstractFeatureRouter<NavFeature, NavCommand>(commandQueue, routerScreens) {

    override fun getStartCommand(screen: NavFeature, uuid: UUID): NavCommand =
        Open(screen.destinationResId, uuid)

    override fun getFinishCommand(uuid: UUID): NavCommand {
        val screen = routerScreens.getScreenOrNull(uuid)
            ?: throw NoSuchElementException("Screen with uid = $uuid not found")
        return BackAction(screen.finishActionResId)
    }
}