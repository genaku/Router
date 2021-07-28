package com.genaku.navrouterconnect

import android.os.Bundle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.genaku.navrouterbase.Back
import com.genaku.navrouterbase.BackAction
import com.genaku.navrouterbase.Open
import com.genaku.router.AbstractRouter
import com.genaku.router.RouterCommand
import com.genaku.router.Screen
import kotlinx.coroutines.flow.collect

/**
 * Connect router to lifecycle and NavController
 *
 * @param lifecycleScope
 * @param navController
 *
 * @return job - this job should be canceled in onPause() method of activity
 */
fun <S : Screen, C : RouterCommand> AbstractRouter<S, C>.connectTo(
    lifecycleScope: LifecycleCoroutineScope,
    navController: NavController
): Job = lifecycleScope.launchWhenResumed {
    commandFlow.collect {
        when (it) {
            Back -> navController.navigateUp()
            is Open -> navController.navigate(it.destinationResId, uidToBundle(it.uid))
            is BackAction -> navController.navigate(it.actionResId)
        }
    }
}

internal const val NAV_UID = "navUid"

fun getUid(arguments: Bundle?): Long? =
    arguments?.getLong(NAV_UID)

fun uidToBundle(uid: Long) = Bundle().apply {
    putLong(NAV_UID, uid)
}