package com.genaku.navrouter

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.genaku.router.*
import kotlinx.coroutines.flow.collect
import java.util.*

fun <T : RouterCommand> CommandFlow<T>.connectTo(
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavController
) {
    lifecycleScope.launchWhenResumed {
        Log.d("TAF", "start collecting")
        commandFlow.collect {
            Log.d("TAF", "collect $it")
            when (it) {
                Back -> navController.navigateUp()
                is Open -> navController.navigate(it.destinationResId, uidToBundle(it.uuid))
                is BackAction -> navController.navigate(it.actionResId)
            }
        }
    }
}

internal const val NAV_UID = "navUid"

fun getUid(arguments: Bundle?): UUID? =
    arguments?.getSerializable(NAV_UID) as UUID?

fun uidToBundle(uuid: UUID) = Bundle().apply {
    putSerializable(NAV_UID, uuid)
}