package org.hoshino9.anti.indulged

import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.ProjectManager
import org.hoshino9.anti.indulged.core.AntiIndulged
import org.hoshino9.anti.indulged.core.DefaultAntiIndulged
import org.hoshino9.anti.indulged.core.ReminderBroadcast
import org.hoshino9.anti.indulged.settings.Settings
import java.util.*
import java.util.concurrent.atomic.AtomicReference

val broadcast: ReminderBroadcast = ReminderBroadcast()

val globalAnti: AtomicReference<AntiIndulged> by lazy {
    val anti = DefaultAntiIndulged(Settings.INSTANCE, broadcast)
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