package com.genaku.router

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.genaku.navrouter.NavCommand
import com.genaku.navrouter.NavRouter
import com.genaku.navrouter.NavScreen
import com.genaku.navrouter.connectTo

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.connectTo(lifecycleScope, findNavController(R.id.rootNavHostFragment))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        routerScreens.onRestoreInstanceState(savedInstanceState)
        commandQueue.onRestoreInstanceState(savedInstanceState)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        commandQueue.onSaveInstanceState(outState)
        routerScreens.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}

val commandQueue = StorableCommandQueue<NavCommand>()
val routerScreens = StorableRouterScreens<NavScreen>()
val router = NavRouter(commandQueue, routerScreens)