package com.genaku.router

import kotlinx.coroutines.flow.Flow
import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class AbstractRouter<S : Screen, C : RouterCommand>(
    protected val commandQueue: CommandQueue<C>
) : Router<S>, CommandFlow<C> {

    protected val screens: ConcurrentHashMap<Long, S> = ConcurrentHashMap()

    abstract fun getStartCommand(screen: S, uid: Long): C

    abstract fun getFinishCommand(uid: Long): C

    override val commandFlow: Flow<C>
        get() = commandQueue.commandFlow

    override fun start(screen: S) {
        val uid = Date().time
        screens[uid] = screen
        commandQueue.send(getStartCommand(screen, uid))
    }

    override fun finish(uid: Long) {
        commandQueue.send(getFinishCommand(uid))
        screens.remove(uid)
    }

    override fun finishWithResult(uid: Long, result: ScreenResult) {
        val screen = screens[uid]
            ?: throw NoSuchElementException("Screen with uid = $uid not found")
        screen.resultStateFlow.tryEmit(result)
        finish(uid)
    }

    override fun getParametersOrNull(uid: Long): ScreenParams? =
        screens[uid]?.params
}