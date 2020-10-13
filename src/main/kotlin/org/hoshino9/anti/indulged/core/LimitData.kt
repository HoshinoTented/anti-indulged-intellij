package org.hoshino9.anti.indulged.core

data class LimitData @JvmOverloads constructor(var accMinutes: Long = 0, val limitMinutes: Long = 3): Clock {
    override val runOut: Boolean
        get() = accMinutes >= limitMinutes

    override fun increase() {
        accMinutes += 1
    }
}