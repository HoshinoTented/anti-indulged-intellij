package org.hoshino9.anti.indulged.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.today

@State(
    name = "org.hoshino9.anti.indulged.settings.LimitDataSettings",
    storages = [Storage("antiIndulged.xml")]
)
class Settings : PersistentStateComponent<Settings>, Clock {
    var lastUpdate: Long = 20201012L
    var accMinutes: Long = 0L
    var limitMinutes: Long = 90L            // 1.5h
    var stayUpProtect: Boolean = true

    override var maximum: Long = limitMinutes

    override val cycle: Long
        get() = 60 * 1000

    override val currentTime: Long
        get() = accMinutes

    override val rest: Long
        get() = limitMinutes - accMinutes

    override fun getState(): Settings? {
        return this
    }

    override fun loadState(state: Settings) {
        XmlSerializerUtil.copyBean(state, this)

        checkNextDay()
    }

    companion object {
        val INSTANCE: Settings
            get() {
                return ServiceManager.getService(Settings::class.java)
            }
    }

    override fun increase() {
        if (! checkNextDay()) {
            accMinutes += 1
        }
    }

    private fun checkNextDay(): Boolean {
        val today = today

        if (today > lastUpdate) {
            lastUpdate = today
            accMinutes = 0

            return true
        } else return false
    }

    override fun toString(): String {
        return "($lastUpdate, $accMinutes, $limitMinutes)"
    }
}