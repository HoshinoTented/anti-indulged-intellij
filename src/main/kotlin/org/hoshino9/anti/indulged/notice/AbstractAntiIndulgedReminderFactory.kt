package org.hoshino9.anti.indulged.notice

import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.core.ReminderFactory

abstract class AbstractAntiIndulgedReminderFactory : ReminderFactory {
    abstract fun forceExit(clock: Clock): ReminderFactory.Reminder
    abstract fun normal(clock: Clock): ReminderFactory.Reminder
    abstract fun takeRest(clock: Clock): ReminderFactory.Reminder

    override fun newInstance(clock: Clock): ReminderFactory.Reminder {
        val rest = clock.rest

        return if (rest.rem(5) == 0L) {
            when (rest.div(5)) {
                in 1L..5L -> takeRest(clock)
                0L -> forceExit(clock)
                else -> normal(clock)
            }
        } else {
            ReminderFactory.Reminder.EMPTY
        }
    }
}