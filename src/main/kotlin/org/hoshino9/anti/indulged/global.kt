package org.hoshino9.anti.indulged

import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.openapi.project.ProjectManager
import org.hoshino9.anti.indulged.core.AntiIndulged
import org.hoshino9.anti.indulged.core.DefaultAntiIndulged
import org.hoshino9.anti.indulged.notice.AntiIndulgedNotification
import org.hoshino9.anti.indulged.settings.LimitDataSettings
import java.util.concurrent.atomic.AtomicReference

val globalAnti: AtomicReference<AntiIndulged> by lazy {
    val anti = DefaultAntiIndulged(LimitDataSettings.INSTANCE, AntiIndulgedNotification)
    AtomicReference<AntiIndulged>(anti)
}

val projectManager: ProjectManager
    get() = ProjectManager.getInstance()