package com.genaku.router

abstract class AbstractFeatureRouter<F : Feature, C : RouterCommand>(
    commandQueue: CommandQueue<C>
) : AbstractRouter<F, C>(commandQueue) {

    override fun start(screen: F) {
        if (screen.isAvailable) {
            super.start(screen)
        }
    }
}