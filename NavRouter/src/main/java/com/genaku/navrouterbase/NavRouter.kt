package com.genaku.navrouterbase

import com.genaku.router.AbstractRouter
import com.genaku.router.CommandQueue

/**
 * Navigation router based on Navigation Component
 *
 * @author Gena Kuchergin
 */
class NavRouter(commandQueue: CommandQueue<NavCommand>) :
    AbstractRouter<NavScreen, NavCommand>(commandQueue) {

    override fun getStartCommand(screen: NavScreen, uid: Long): NavCommand =
        Open(screen.destinationResId, uid)

    override fun getFinishCommand(uid: Long): NavCommand = Back
}