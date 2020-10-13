package org.hoshino9.anti.indulged.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.hoshino9.anti.indulged.core.Clock

@State(
    name = "org.hoshino9.anti.indulged.settings.LimitDataSettings",
    storages = [Storage("antiIndulged.xml")]
)
class LimitDataSettings : PersistentStateComponent<LimitDataSettings>, Clock {
    var lastUsed: Long = 0L
    var accMinutes: Long = 0L
    var limitMinutes: Long = 1L

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
    }

    companion object {
        val INSTANCE: LimitDataSettings
            get() {
                return ServiceManager.getService(LimitDataSettings::class.java)
            }
    }

    override fun increase() {
        accMinutes += 1
    }

    override fun toString(): String {
        return "($lastUsed, $accMinutes, $limitMinutes)"
    }
}