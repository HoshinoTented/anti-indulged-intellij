package org.hoshino9.anti.indulged.activity

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.StartupActivity
import org.hoshino9.anti.indulged.GlobalAntiManager
import org.hoshino9.anti.indulged.projectManager
import org.hoshino9.anti.indulged.GlobalAntiManager.globalAnti
import org.hoshino9.anti.indulged.GlobalAntiManager.logger
import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.core.DefaultAntiIndulged
import org.hoshino9.anti.indulged.core.ReminderFactory
import org.hoshino9.anti.indulged.notice.AntiIndulgedNotification

class AntiIndulgedStartupActivity : StartupActivity, DumbAware {
    override fun runActivity(project: Project) {
        logger.info("Project ${project.name} was opened")

        GlobalAntiManager.launch()

        GlobalAntiManager.globalAnti.get()?.run {
            val reminder = newReminder()

            if (reminder === ReminderFactory.Reminder.EMPTY) {
                AntiIndulgedNotification.normal(coroutineContext[Clock.Key] ?: return@run)
                    .remind()
            }
        }

        projectManager.addProjectManagerListener(project, object : ProjectManagerListener {
            override fun projectClosed(project: Project) {
                if (projectManager.openProjects.isEmpty()) {
                    globalAnti.get().stopTiming()
                }
            }
        })
    }
}