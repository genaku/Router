package com.genaku.router

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.genaku.navrouterbase.NavCommand
import com.genaku.navrouterbase.NavRouter
import com.genaku.navrouterconnect.connectTo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.connectTo(lifecycleScope, findNavController(R.id.rootNavHostFragment))
    }
}

val router = NavRouter(BaseCommandQueue())