package com.genaku.router

import kotlinx.coroutines.flow.Flow

interface CommandFlow<T: RouterCommand> {
    /**
     * Router commands flow used to get commands from queue
     */
    val commandFlow: Flow<T>
}