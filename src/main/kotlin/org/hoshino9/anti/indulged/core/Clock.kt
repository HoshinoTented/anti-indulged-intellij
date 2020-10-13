package org.hoshino9.anti.indulged.core

import kotlin.coroutines.CoroutineContext

interface Clock : CoroutineContext.Element {
    val runOut: Boolean
        get() {
            return rest <= 0
        }

    val cycle: Long

    val time: Long
    val rest: Long

    fun increase()

    override val key: CoroutineContext.Key<*>
        get() = Key

    object Key : CoroutineContext.Key<Clock>
}