package org.hoshino9.anti.indulged.notice

import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.core.ReminderFactory
import java.util.*

object CurfewNotification : ReminderFactory {
    object Reminder : BaseForceExitReminder {
        override val title: String
            get() = "Anti-Indulged"

        override val content: String
            get() = "夜深了，野生的社畜也回巢休息了。<br/>它的伴侣和孩子们都已经熟睡，而唯独你还在为了生活而奋斗。<br/>休息吧~休息吧~"
    }

    val isNightTime: Boolean
        get() {
            val calendar = Calendar.getInstance()
            return when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 22 until 24, in 0 until 8 -> {
                    true
                }

                else -> false
            }
        }

    override fun newInstance(clock: Clock): ReminderFactory.Reminder {
        if (isNightTime) {
            return Reminder
        } else {
            return ReminderFactory.Reminder.EMPTY
        }
    }
}