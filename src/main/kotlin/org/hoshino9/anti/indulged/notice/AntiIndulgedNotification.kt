package org.hoshino9.anti.indulged.notice

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.DialogBuilder
import org.hoshino9.anti.indulged.core.ReminderFactory
import org.hoshino9.anti.indulged.projectManager

object AntiIndulgedNotification : ReminderFactory {
    object ForceExitReminder : ReminderFactory.Reminder {
        override val shouldClose: Boolean = true
        override fun remind() {
            forceExit("亲爱的社畜码农，您今日的可用编程时间已用完，请注意休息。")
        }
    }

    class SimpleReminder(private val rest: Long) : ReminderFactory.Reminder {
        override val shouldClose: Boolean = false
        override fun remind() {
            val content = "亲爱的社畜码农，您今日的可用编程时间剩余 $rest 分钟，请注意休息。"
            val notification = GROUP.createNotification(content, NotificationType.WARNING)

            Notifications.Bus.notify(notification)
        }
    }

    val GROUP: NotificationGroup = NotificationGroup("org.hoshino9.anti.indulged", NotificationDisplayType.BALLOON)

    fun forceExit(content: String) {
        val app = ApplicationManager.getApplication()

        app.invokeLater {
            DialogBuilder().apply {
                setTitle("Anti-Indulged")
                setErrorText(content)
            }.showAndGet()

            projectManager.openProjects.forEach {
                if (! it.isDisposed) {
                    projectManager.closeAndDispose(it)
                }
            }

            ApplicationManager.getApplication().exit(true, false, false)
        }
    }

    override fun newInstance(rest: Long): ReminderFactory.Reminder {
        return when (rest) {
            0L -> ForceExitReminder
            else -> SimpleReminder(rest)
        }
    }
}