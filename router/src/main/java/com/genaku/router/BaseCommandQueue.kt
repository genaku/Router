package com.genaku.router

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

open class BaseCommandQueue<C : RouterCommand> : CommandQueue<C> {

    protected val channel = Channel<C>(Channel.UNLIMITED)

    override val commandFlow: Flow<C> = channel.receiveAsFlow()

    override fun send(command: C) {
        channel.offer(command)
    }
}