package org.hoshino9.anti.indulged.core

interface LimitationReminder {
    /**
     * Reminder Level
     */
    sealed class Level {
        /**
         * remind player should take a rest
         */
        data class Info(val rest: Long) : Level()

        object Error : Level()
    }

    fun remind(level: Level)
}