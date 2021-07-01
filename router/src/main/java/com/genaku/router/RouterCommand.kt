package com.genaku.router

import java.io.Serializable

/**
 * Router navigation command
 *
 * Command will be sent to command queue. Lifecycle owner observes queue and receive
 * these commands from queue and execute it in UI thread by UI navigation engine
 */
interface RouterCommand: Serializable