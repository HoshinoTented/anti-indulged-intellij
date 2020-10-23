package org.hoshino9.anti.indulged.core

interface AntiIndulged {
    /**
     * start timing
     * it should start timing after boots or [stopTiming]
     */
    fun startTiming()

    /**
     * stop timing
     * it should stop timing after [startTiming]
     */
    fun stopTiming()

    /**
     * return true if current anti-indulged is timming
     */
    val isActive: Boolean
}