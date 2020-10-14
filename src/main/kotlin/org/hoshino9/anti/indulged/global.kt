package org.hoshino9.anti.indulged

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.ProjectManager
import org.hoshino9.anti.indulged.core.AntiIndulged
import org.hoshino9.anti.indulged.core.DefaultAntiIndulged
import org.hoshino9.anti.indulged.notice.AntiIndulgedNotification
import org.hoshino9.anti.indulged.settings.LimitDataSettings
import java.util.*
import java.util.concurrent.atomic.AtomicReference

val globalAnti: AtomicReference<AntiIndulged> by lazy {
    val anti = DefaultAntiIndulged(LimitDataSettings.INSTANCE, AntiIndulgedNotification)
    AtomicReference<AntiIndulged>(anti)
}

val projectManager: ProjectManager
    get() = ProjectManager.getInstance()

val application: Application
    get() = ApplicationManager.getApplication()

internal fun todayOf(calendar: Calendar): Long {
    var today = 0L

    today += calendar[Calendar.YEAR]
    today *= 100
    today += calendar[Calendar.MONTH] + 1
    today *= 100
    today += calendar[Calendar.DAY_OF_MONTH]

    return today
}

val today: Long
    get() {
        return todayOf(Calendar.getInstance())
    }