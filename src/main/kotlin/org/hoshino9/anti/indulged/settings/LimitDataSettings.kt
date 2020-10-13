package org.hoshino9.anti.indulged.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import org.hoshino9.anti.indulged.core.Clock
import org.hoshino9.anti.indulged.core.LimitData

@State(
    name = "org.hoshino9.anti.indulged.settings.LimitDataSettings",
    storages = [Storage("AntiIndulged.xml")]
)
class LimitDataSettings : PersistentStateComponent<LimitDataSettings>, Clock {
    private var lastUsed: Long = 0L
    private var accMinutes: Long = 0L
    private var limitMinutes: Long = 1L

    override fun getState(): LimitDataSettings? {
        return this
    }

    override fun loadState(state: LimitDataSettings) {
        this.lastUsed = state.lastUsed
        this.accMinutes = state.lastUsed
        this.limitMinutes = state.limitMinutes
    }

    companion object {
        val INSTANCE: LimitDataSettings
            get() {
                return ServiceManager.getService(LimitDataSettings::class.java)
            }
    }

    override val runOut: Boolean
        get() = accMinutes >= limitMinutes

    override fun increase() {
        accMinutes += 1
    }

    override fun toString(): String {
        return "($lastUsed, $accMinutes, $limitMinutes)"
    }
}