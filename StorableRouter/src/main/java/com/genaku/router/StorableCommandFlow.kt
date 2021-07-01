package com.genaku.router

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

open class StorableCommandFlow<C : RouterCommand>(
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : CommandQueue<C> {

    protected var canSend = AtomicBoolean(true)

    private val commands: Queue<C> = ConcurrentLinkedQueue()

    override val commandFlow: Flow<C> = flow {
        log("create flow")
        while (true) {
            tryGetCommand()?.run {
                log("emit command $this")
                emit(this)
                commands.remove()
            } ?: wait()
        }
    }.flowOn(dispatcher)

    override fun send(command: C) {
        log("send command $command")
        commands.add(command)
    }

    private fun tryGetCommand(): C? {
        log("try get command")
        if (canGetCommand()) {
            return commands.peek()
        }
        return null
    }

    private suspend fun wait() {
        log("start waitAsync")
        while (!canGetCommand()) {
            delay(DELAY)
        }
    }

    private fun canGetCommand(): Boolean = (canSend.get() && !commands.isEmpty())

    protected open fun pullAllCommandsAndPauseFlow(): List<C> {
        canSend.set(false)
        val result = commands.toList()
        commands.clear()
        return result
    }

    protected open fun addCommandsAndResumeFlow(storedCommands: List<C>) {
        if (storedCommands.isNotEmpty()) {
            val newCommands = pullAllCommandsAndPauseFlow()
            val allCommands = mutableListOf<C>()
            allCommands.addAll(storedCommands)
            allCommands.addAll(newCommands)
            commands.addAll(allCommands)
        }
        canSend.set(true)
    }

    private fun log(msg: String) {
        println("TAF [${Thread.currentThread()}] $msg")
    }

    companion object {
        private const val DELAY = 300L
    }
}