package com.genaku.navrouterbase

import com.genaku.router.RouterCommand

/**
 * Nav command
 *
 * @author Gena Kuchergin
 */
sealed class NavCommand: RouterCommand

/**
 * Command for Navigation Component to return back from current screen
 *
 * @author Gena Kuchergin
 */
object Back : NavCommand()

/**
 * Command for Navigation Component to navigate to new screen with [destinationResId]
 *
 * @property destinationResId - ResId of screen start destination in nav graph
 * @property uid - screen unique ID, will be sent as argument by Navigation Component to navigated screen
 *
 * @author Gena Kuchergin
 */
data class Open(val destinationResId: Int, val uid: Long) : NavCommand()

/**
 * Command for Navigation Component to return back from current feature
 *
 * @property actionResId - ResId of action to finish feature nav graph
 *
 * @author Gena Kuchergin
 */
data class BackAction(val actionResId: Int): NavCommand()