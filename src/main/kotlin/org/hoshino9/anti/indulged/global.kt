package org.hoshino9.anti.indulged

import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.ProjectManager
import org.hoshino9.anti.indulged.core.AntiIndulged
import org.hoshino9.anti.indulged.core.ConsoleLogger
import org.hoshino9.anti.indulged.core.DefaultAntiIndulged
import org.hoshino9.anti.indulged.core.ReminderBroadcast
import org.hoshino9.anti.indulged.data.CalendarConverter.truncate
import org.hoshino9.anti.indulged.data.Settings
import org.hoshino9.anti.indulged.notice.AntiIndulgedNotification
import org.hoshino9.anti.indulged.notice.CurfewNotification
import java.util.*
import java.util.concurrent.atomic.AtomicReference


object GlobalAntiManager {
    val logger = ConsoleLogger

    val broadcast: ReminderBroadcast = ReminderBroadcast()

    val globalAnti: AtomicReference<AntiIndulged> by lazy {
        val anti = DefaultAntiIndulged(Settings.INSTANCE, broadcast, logger)
        AtomicReference<AntiIndulged>(anti)
    }

    private fun loadAntiIndulged() {
        broadcast.add(AntiIndulgedNotification)
    }

    private fun loadCurfew() {
        if (Settings.INSTANCE.curfew) {
            broadcast.add(CurfewNotification)
        }
    }

    private fun loadFeatures() {
        loadCurfew()
        loadAntiIndulged()
    }

    @Synchronized
    fun launch() {
        val anti = globalAnti.get()

        if (!anti.isActive) {
            loadFeatures()
            anti.startTiming()
        }
    }

    fun exit() {
        globalAnti.get().stopTiming()
    }

    fun clearBroadcast() {
        broadcast.clear()
    }
}

val projectManager: ProjectManager
    get() = ProjectManager.getInstance()

val application: Application
    get() = ApplicationManager.getApplication()

val today: Calendar
    get() {
        return Calendar.getInstance().truncate()
    }

val currentDate: Calendar get() = Calendar.getInstance().truncate()