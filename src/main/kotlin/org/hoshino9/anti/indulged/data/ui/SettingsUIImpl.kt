package org.hoshino9.anti.indulged.data.ui

import com.intellij.openapi.options.Configurable
import org.hoshino9.anti.indulged.GlobalAntiManager
import org.hoshino9.anti.indulged.data.Settings
import javax.swing.JComponent

class SettingsUIImpl : SettingsUI(), Configurable {
    private fun reload(settings: Settings) {
        textUsed.text = settings.currentTime.toString()
        textMaximum.text = settings.maximum.toString()
        cbCurfew.isSelected = settings.curfew
    }

    override fun createComponent(): JComponent? {
        reload(Settings.INSTANCE)

        return this.mainPanel
    }

    override fun isModified(): Boolean {
        val settings = Settings.INSTANCE

        return settings.curfew != cbCurfew.isSelected
    }

    override fun apply() {
        val settings = Settings.INSTANCE

        settings.curfew = cbCurfew.isSelected

        GlobalAntiManager.run {
            exit()
            clearBroadcast()
            launch()
        }
    }

    override fun getDisplayName(): String {
        return "Anti Indulged"
    }

}