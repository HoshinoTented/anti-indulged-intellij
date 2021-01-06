package org.hoshino9.anti.indulged.notice

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.ui.DialogBuilder
import org.hoshino9.anti.indulged.core.ReminderFactory
import org.hoshino9.anti.indulged.projectManager

interface ForceExitReminderBase : ReminderFactory.Reminder {
    val title: String
    val content: String

    override val shouldClose: Boolean get() = true

    override fun remind() {
        val app = ApplicationManager.getApplication()

        app.invokeLater {
            DialogBuilder().apply {
                setTitle(title)
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
}