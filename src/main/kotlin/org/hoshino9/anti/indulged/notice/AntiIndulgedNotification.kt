package org.hoshino9.anti.indulged.notice

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.core.ReminderFactory

object AntiIndulgedNotification : AbstractAntiIndulgedReminderFactory() {
    object ForceExitReminder : ForceExitReminderBase {
        override val content: String
            get() = "关掉，关掉，一定要关掉！<br/>再不关掉那些编程软件，社畜哪有美好的未来，哪有美好的前程？<br/>祖国哪有栋梁之材？"

        override val title: String
            get() = "Anti-Indulged"
    }

    class SimpleReminder(private val rest: Long) : ReminderFactory.Reminder {
        override val shouldClose: Boolean = false
        override fun remind() {
            val content = "亲爱的社畜码农<br/>您今日的可用编程时间剩余 $rest 分钟"
            notice(content)
        }
    }

    class TakeRestReminder(private val rest: Long) : ReminderFactory.Reminder {
        override val shouldClose: Boolean = false
        override fun remind() {
            val content = "亲爱的社畜码农<br/>您今日的可用编程时间剩余 $rest 分钟，请注意休息。"
            notice(content)
        }
    }

    val GROUP: NotificationGroup = NotificationGroup("org.hoshino9.anti.indulged", NotificationDisplayType.BALLOON)

    fun notice(content: String) {
        val notification = GROUP.createNotification(content, NotificationType.WARNING)

        Notifications.Bus.notify(notification)
    }

    override fun takeRest(clock: Clock): ReminderFactory.Reminder {
        return TakeRestReminder(clock.rest)
    }

    override fun normal(clock: Clock): ReminderFactory.Reminder {
        return SimpleReminder(clock.rest)
    }

    override fun forceExit(clock: Clock): ReminderFactory.Reminder {
        return ForceExitReminder
    }
}