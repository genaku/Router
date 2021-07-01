package com.genaku.alligatorrouter

import com.genaku.router.Feature
import com.genaku.router.RouterScreen
import java.io.Serializable

/**
 * Screen navigation info to use in Alligator navigator
 *
 * @author Gena Kuchergin
 */
interface AlgScreen : RouterScreen, AlligatorScreen, Serializable

/**
 * Feature navigation info to use in Alligator navigator
 *
 * @author Gena Kuchergin
 */
interface NavFeature: AlgScreen, Feature