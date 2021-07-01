package com.genaku.navrouter

import com.genaku.router.AbstractRouter
import com.genaku.router.CommandQueue
import com.genaku.router.RouterScreens
import java.util.*

/**
 * Navigation router based on Navigation Component
 *
 * @author Gena Kuchergin
 */
open class NavRouter(
    commandQueue: CommandQueue<NavCommand>,
    routerScreens: RouterScreens<NavScreen>
) : AbstractRouter<NavScreen, NavCommand>(commandQueue, routerScreens) {

    override fun getStartCommand(screen: NavScreen, uuid: UUID): NavCommand =
        Open(screen.destinationResId, uuid)

    override fun getFinishCommand(uuid: UUID): NavCommand = Back

}