package com.visionsof.intellijunrealbuildtoolplugin.ui

import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTabbedPane
import com.intellij.util.ui.JBUI
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders.*
import javax.swing.JComponent
import java.awt.Dimension
import javax.swing.ScrollPaneConstants
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

private val CONFIG_UIS = arrayOf(
    ::buildGlobalConfigurationUi,
    ::buildWindowsPlatformConfigurationUi,
    ::buildParallelConfigurationUi,
    ::buildUbtFastbuildExecutorTabUi,
    ::buildIncredibuildConfigurationUi,
)

class UbtGlobalConfigurationWindow(private val config: UbtConfiguration, private val onApply : ()-> Unit): DialogWrapper(null, null, true, IdeModalityType.IDE, true) {
    companion object
    {
        const val WIDTH = 600
        const val HEIGHT = 400
    }

    private val configPanels = MutableList<DialogPanel?>(CONFIG_UIS.size) { null }

    init {
        title = "Unreal Build Tool Configuration"
        init()
    }

    override fun getPreferredSize(): Dimension {
       return Dimension(WIDTH, HEIGHT)
    }

    override fun createCenterPanel(): JComponent? {
        val dims = Dimension(WIDTH, HEIGHT)
        val tabs = JBTabbedPane().apply { preferredSize = dims; maximumSize = dims }

        for ((index, configUi: KFunction<DialogPanel>) in CONFIG_UIS.withIndex()) {
            val configUIAnnotation = configUi.findAnnotation<UbtConfigTabUi>()
                ?: throw Exception("UbtConfigUi annotation is missed for ${configUi.name}")

            val configPanel: DialogPanel = configUi.call(config)

            tabs.add(configUIAnnotation.title, JBScrollPane(configPanel).apply {
                horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                border = JBUI.Borders.empty()
            })
            configPanels[index] = configPanel
        }

        return tabs
    }

    override fun doOKAction() {
        super.doOKAction()

        configPanels.forEach { panel -> panel?.apply() }

        onApply()
    }
}