package org.hoshino9.anti.indulged.core

interface AntiIndulged {
    /**
     * start timing
     * it should start timing after boots or [stopTiming]
     */
    fun startTiming()

    /**
     * stop timing
     * it should stop timing after [startTiming], and write rewrite [LimitData.lastUsed] and [LimitData.accMinutes]
     */
    fun stopTiming()
}