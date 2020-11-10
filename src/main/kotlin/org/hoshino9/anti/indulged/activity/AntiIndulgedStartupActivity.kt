package org.hoshino9.anti.indulged.activity

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.startup.StartupActivity
import org.hoshino9.anti.indulged.globalAnti
import org.hoshino9.anti.indulged.broadcast
import org.hoshino9.anti.indulged.notice.AntiIndulgedNotification
import org.hoshino9.anti.indulged.notice.CurfewNotification
import org.hoshino9.anti.indulged.projectManager
import org.hoshino9.anti.indulged.settings.Settings

class AntiIndulgedStartupActivity : StartupActivity, DumbAware {
    private fun loadAntiIndulged() {
        broadcast.add(AntiIndulgedNotification)
    }

    private fun loadCurfew() {
        if (Settings.INSTANCE.curfew) {
            broadcast.add(CurfewNotification)
        }
    }

    private fun loadFeatures(project: Project) {
        loadCurfew()
        loadAntiIndulged()
    }

    override fun runActivity(project: Project) {
        println("Project ${project.name} is opened")

        val anti = globalAnti.get()

        if (! anti.isActive) {
            loadFeatures(project)
            anti.startTiming()
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