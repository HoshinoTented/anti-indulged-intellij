package org.hoshino9.anti.indulged.core

interface ReminderFactory {
    interface Reminder {
        val shouldClose: Boolean
        fun remind()

        object EMPTY : Reminder {
            override val shouldClose: Boolean = false
            override fun remind() = Unit
        }
    }

    /**
     * this function will be called every cycle of the clock
     * returns true if should close the game
     */
    fun newInstance(rest: Long): Reminder
}