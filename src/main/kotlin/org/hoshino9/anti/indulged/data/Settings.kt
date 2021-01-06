package org.hoshino9.anti.indulged.data

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.options.Configurable
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.OptionTag
import org.hoshino9.anti.indulged.GlobalAntiManager
import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.currentDate
import java.util.*
import javax.swing.JComponent

// TODO: 考虑不直接将 Settings 实现 Clock
@State(
    name = "org.hoshino9.anti.indulged.data.Settings",
    storages = [Storage("antiIndulged.xml")]
)
class Settings : PersistentStateComponent<Settings>, Clock {
    @OptionTag(converter = CalendarConverter::class)
    var lastUpdate: Calendar = CalendarConverter.DEFAULT
    var accMinutes: Long = 0L
    var limitMinutes: Long = 90L            // 1.5h
    var curfew: Boolean = true

    override val maximum: Long
        get() = (if (lastUpdate.isHoliday()) 2 else 1) * limitMinutes

    override val cycle: Long
        get() = 60 * 1000

    override val currentTime: Long
        get() = accMinutes

    override fun getState(): Settings? {
        return this
    }

    override fun loadState(state: Settings) {
        XmlSerializerUtil.copyBean(state, this)

        checkNextDay()
    }

    override fun noStateLoaded() {
        checkNextDay()
    }

    override fun increase() {
        if (! checkNextDay()) {
            accMinutes += 1
        }
    }

    private fun checkNextDay(): Boolean {
        val today = currentDate

        return if (today > lastUpdate) {
            lastUpdate = currentDate
            accMinutes = 0

            true
        } else false
    }

    override fun toString(): String {
        return "(${lastUpdate.timeInMillis}, $accMinutes ($currentTime), $limitMinutes ($maximum))"
    }

    companion object {
        val INSTANCE: Settings
            get() {
                return ServiceManager.getService(Settings::class.java)
            }
    }
}
