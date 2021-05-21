package com.genaku.router

interface CommandQueue<C : RouterCommand> : CommandFlow<C> {
    /**
     * Send router command into queue
     *
     * @param command - navigation command
     */
    fun send(command: C)
}