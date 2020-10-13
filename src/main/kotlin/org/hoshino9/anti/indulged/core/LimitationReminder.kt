package org.hoshino9.anti.indulged.core

interface LimitationReminder {

    /**
     * this function will be called every cycle of the clock
     * it shouldn't take a long time
     * returns true if should close the game
     */
    fun remind(rest: Long): Boolean
}