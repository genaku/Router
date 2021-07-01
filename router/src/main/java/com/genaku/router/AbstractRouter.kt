package com.genaku.router

import java.util.*

abstract class AbstractRouter<S : RouterScreen, C : RouterCommand>(
    protected val commandQueue: CommandQueue<C>,
    protected val routerScreens: RouterScreens<S>
) : Router<S>, CommandFlow<C> by commandQueue, ScreenParameters by routerScreens {

    abstract fun getStartCommand(screen: S, uuid: UUID): C

    abstract fun getFinishCommand(uuid: UUID): C

    override fun start(screen: S) {
        val uid = routerScreens.addScreen(screen)
        commandQueue.send(getStartCommand(screen, uid))
    }

    override fun finish(uuid: UUID) {
        commandQueue.send(getFinishCommand(uuid))
        routerScreens.deleteScreen(uuid)
    }

    override fun finishWithResult(uuid: UUID, result: ScreenResult) {
        val screen = routerScreens.getScreenOrNull(uuid)
            ?: throw NoSuchElementException("Screen with uid = $uuid not found")
        screen.resultStateFlow.tryEmit(result)
        finish(uuid)
    }
}