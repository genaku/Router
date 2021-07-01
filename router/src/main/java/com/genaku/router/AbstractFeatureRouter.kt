package com.genaku.router

abstract class AbstractFeatureRouter<F : Feature, C : RouterCommand>(
    commandQueue: CommandQueue<C>,
    routerScreens: RouterScreens<F>
) : AbstractRouter<F, C>(commandQueue, routerScreens) {

    override fun start(screen: F) {
        if (screen.isAvailable) {
            super.start(screen)
        }
    }
}