package com.genaku.alligatorrouter

import com.genaku.router.RouterScreen
import java.io.Serializable
import java.util.*

/**
 * Screen navigation info to use in Alligator navigator
 *
 * @author Gena Kuchergin
 */
interface AlgScreen : RouterScreen, AlligatorScreen, Serializable {
    var uuid: UUID?
}