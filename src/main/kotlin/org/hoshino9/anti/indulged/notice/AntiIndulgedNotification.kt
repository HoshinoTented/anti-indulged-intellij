package org.hoshino9.anti.indulged.notice

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.DialogBuilder
import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.core.ReminderFactory
import org.hoshino9.anti.indulged.projectManager

object AntiIndulgedNotification : AbstractAntiIndulgedReminderFactory() {
    object ForceExitReminder : ReminderFactory.Reminder {
        override val shouldClose: Boolean = true
        override fun remind() {
            val app = ApplicationManager.getApplication()

            app.invokeLater {
                DialogBuilder().apply {
                    setTitle("Anti-Indulged")
                    setErrorText("亲爱的社畜码农，您今日的可用编程时间已用完，请注意休息。")
                }.showAndGet()

                projectManager.openProjects.forEach {
                    if (! it.isDisposed) {
                        projectManager.closeAndDispose(it)
                    }
                }

                ApplicationManager.getApplication().exit(true, false, false)
            }
        }
    }

    class SimpleReminder(private val rest: Long) : ReminderFactory.Reminder {
        override val shouldClose: Boolean = false
        override fun remind() {
            val content = "亲爱的社畜码农，您今日的可用编程时间剩余 $rest 分钟"
            notice(content)
        }
    }

    class TakeRestReminder(private val rest: Long) : ReminderFactory.Reminder {
        override val shouldClose: Boolean = false
        override fun remind() {
            val content = "亲爱的社畜码农，您今日的可用编程时间剩余 $rest 分钟，请注意休息。"
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