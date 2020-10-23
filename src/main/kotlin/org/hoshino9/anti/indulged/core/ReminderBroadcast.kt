package org.hoshino9.anti.indulged.core

class ReminderBroadcast : ReminderFactory {
    private val reminders: MutableList<ReminderFactory> = mutableListOf()

    override fun newInstance(clock: Clock): ReminderFactory.Reminder {
        return reminders.fold(ReminderFactory.Reminder.EMPTY as ReminderFactory.Reminder) { acc, factory ->
            if (acc === ReminderFactory.Reminder.EMPTY) {
                factory.newInstance(clock)
            } else acc
        }
    }

    fun add(factory: ReminderFactory): Boolean {
        return reminders.add(factory)
    }
}