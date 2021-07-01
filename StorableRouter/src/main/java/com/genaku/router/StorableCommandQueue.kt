package com.genaku.router

import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.io.Serializable

open class StorableCommandQueue<C : RouterCommand>(
    private val key: String = DEFAULT_KEY,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) : StorableCommandFlow<C>(dispatcher), StorableInstanceState {

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val data = savedInstanceState.getSerializable(key) as StorableCommandQueue<C>.QueueData?
        if (data == null) {
            addCommandsAndResumeFlow(emptyList())
        } else {
            log("restore ${data.commands}")
            addCommandsAndResumeFlow(data.commands)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val data = QueueData(pullAllCommandsAndPauseFlow())
        log("save ${data.commands}")
        if (data.commands.isNotEmpty()) {
            outState.putSerializable(key, data)
        }
    }

    private fun log(msg: String) {
        Log.d("TAF", "[${Thread.currentThread()}] $msg")
    }

    inner class QueueData(val commands: List<C>) : Serializable

    companion object {
        private const val DEFAULT_KEY = "RouterCommands"
    }
}

