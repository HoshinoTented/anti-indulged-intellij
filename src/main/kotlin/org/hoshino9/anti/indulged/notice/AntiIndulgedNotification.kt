package org.hoshino9.anti.indulged.notice

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import org.hoshino9.anti.indulged.core.LimitationReminder
import org.hoshino9.anti.indulged.projectManager

object AntiIndulgedNotification : LimitationReminder {
    val GROUP: NotificationGroup = NotificationGroup("org.hoshino9.anti.indulged", NotificationDisplayType.BALLOON)

    override fun remind(level: LimitationReminder.Level) {
        val content = when (level) {
            is LimitationReminder.Level.Info -> "亲爱的社畜码农，您今日的可用编程时间剩余 ${level.rest} 分钟，请注意休息。"
            LimitationReminder.Level.Error -> "亲爱的社畜码农，您今日的可用编程时间已用完，请注意休息。"        // this level should close the IDE but not just notify
        }

        val notification = GROUP.createNotification(content, NotificationType.WARNING)

        projectManager.openProjects.forEach {
            it.takeIf { ! it.isDisposed }?.let {
                Notifications.Bus.notify(notification, it)
            }
        }
    }
}