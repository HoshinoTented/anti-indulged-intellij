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
class LimitDataSettings : PersistentStateComponent<LimitDataSettings>, Clock {
    var lastUpdate: Long = 20201012L
    var accMinutes: Long = 0L
    val limitMinutes: Long = 90L            // 1.5h

    override val cycle: Long
        get() = 60 * 1000

    override val time: Long
        get() = accMinutes

    override val rest: Long
        get() = limitMinutes - accMinutes

    override fun getState(): LimitDataSettings? {
        return this
    }

    override fun loadState(state: LimitDataSettings) {
        XmlSerializerUtil.copyBean(state, this)

        val today = today
        if (today > lastUpdate) {
            lastUpdate = today
            accMinutes = 0
        }
    }

    companion object {
        val INSTANCE: LimitDataSettings
            get() {
                return ServiceManager.getService(LimitDataSettings::class.java)
            }
    }

    // TODO: 更新最后使用的时候可能导致凌晨写代码没有重置 accMinutes
    override fun increase() {
        accMinutes += 1
        lastUpdate = today
    }

    override fun toString(): String {
        return "($lastUpdate, $accMinutes, $limitMinutes)"
    }
}