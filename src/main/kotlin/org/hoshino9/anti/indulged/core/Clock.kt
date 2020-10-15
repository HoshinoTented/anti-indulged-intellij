package org.hoshino9.anti.indulged.core

import kotlin.coroutines.CoroutineContext

interface Clock : CoroutineContext.Element {
    val runOut: Boolean
        get() {
            return currentTime >= maximum
        }

    /**
     * 一时刻所需的时间，单位毫秒
     */
    val cycle: Long

    /**
     * 当前时刻
     * 没有要求 [currentTime] 必须不大于 [maximum]，但最好不要
     */
    val currentTime: Long

    /**
     * 剩余时刻
     */
    val rest: Long

    /**
     * Clock 所能走的最多的时刻
     */
    val maximum: Long

    /**
     * 前进一时刻
     */
    fun increase()

    override val key: CoroutineContext.Key<*>
        get() = Key

    object Key : CoroutineContext.Key<Clock>
}