package com.genaku.alligatorrouter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import me.aartikov.alligator.animations.providers.TransitionAnimationProvider
import me.aartikov.alligator.listeners.ScreenResultListener
import java.util.*

/**
 * Интерфейс роутера на базе me.aartikov.alligator.Navigator
 *
 * @author Gena Kuchergin
 */
interface AlligatorRouter {

    /**
     * Связывает активити с навигатором
     *
     * @param activity
     * @param screenResultListener
     * @param fragmentContainerId
     * @param transitionAnimationProvider
     */
    fun bind(
        activity: AppCompatActivity,
        screenResultListener: ScreenResultListener,
        fragmentContainerId: Int,
        transitionAnimationProvider: TransitionAnimationProvider?
    )

    /**
     * Отсоединяет активити от навигатора
     *
     * @param activity
     */
    fun unbind(activity: AppCompatActivity)

    /**
     * Передать в навигатор Intent
     *
     * @param intent
     */
    fun onNewIntent(intent: Intent?)

    /**
     * Передать в навигатор результаты возврата в активити
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun getUUID(activity: AppCompatActivity): UUID

    fun getUUID(fragment: Fragment): UUID
}