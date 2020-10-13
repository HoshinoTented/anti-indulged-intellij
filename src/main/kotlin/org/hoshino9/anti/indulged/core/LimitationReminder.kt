package org.hoshino9.anti.indulged.core

interface LimitationReminder {

    /**
     * this function will be called every cycle of the clock
     * returns true if should close the game
     */
    fun remind(rest: Long): Boolean
}