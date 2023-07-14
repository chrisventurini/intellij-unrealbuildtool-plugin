package com.visionsof.intellijunrealbuildtoolplugin.ui

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareToggleAction
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import com.visionsof.intellijunrealbuildtoolplugin.model.BuildConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.ParallelExecutorConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.services.BasicPropUiFactoryService
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.buildProcessCountMultiplierSlider
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.buildSpinnerPropUi
import com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders.checkConfig
import kotlin.reflect.KMutableProperty1

fun buildUbtQuickMenu(masterConfig: UbtConfiguration, onApply : () -> Unit, onCancel: () -> Unit): DialogPanel {
    lateinit var panel : DialogPanel

    var config = checkConfig(masterConfig, UbtConfiguration::buildConfiguration, ::BuildConfiguration)

    val propService = BasicPropUiFactoryService.getInstance()
    val maxProcessors = Runtime.getRuntime().availableProcessors()

    fun executorActionFactory(name: String, prop: KMutableProperty1<BuildConfiguration, Boolean?>, selectedDefault: Boolean) : DumbAwareToggleAction {
        return object : DumbAwareToggleAction(name){
            override fun isSelected(e: AnActionEvent): Boolean {
                if(masterConfig.buildConfiguration == null || prop.get(masterConfig.buildConfiguration!!) == null) {
                    return selectedDefault
                }
                return prop.get(masterConfig.buildConfiguration!!)!!
            }

            override fun setSelected(e: AnActionEvent, state: Boolean) {
                if(masterConfig.buildConfiguration == null) {
                    masterConfig.buildConfiguration = BuildConfiguration()
                }

                prop.set(masterConfig.buildConfiguration!!, state)
            }
        }
    }

    panel = panel() {
        group ("Build Configuration") {
            row() {
                label("Enabled Executors:")
                actionsButton(
                    executorActionFactory("1. Hybrid", BuildConfiguration::allowHybrid, false),
                    executorActionFactory("2. Incredibuild", BuildConfiguration::allowXge, true),
                    executorActionFactory("3. FASTBuild", BuildConfiguration::allowFastBuild, true),
                )
            }

            propService.build(this, config!!::useUnityBuild, false)
            propService.build(this, config::useAdaptiveUnityBuild, false)
            propService.build(this, config::warningsAsErrors, false)
            buildSpinnerPropUi(this, config::maxParallelActions, 0..(maxProcessors * 2), 1, false)
        }

        group ("Parallel Executor") {
            row(){
                label("Process Count Multiplier")
            }
            row() {
                buildProcessCountMultiplierSlider(this, checkConfig(masterConfig, UbtConfiguration::parallelExecutor, ::ParallelExecutorConfiguration)!!)
            }
        }

        row() {
            link("Edit Configuration") {
                onCancel()
                UbtGlobalConfigurationWindow(masterConfig, onApply).show()
            }
        }

        row () {
            button("Apply") {
                panel.apply()
                onApply()
            }
            button("Cancel") {
                panel.reset()
                onCancel()
            }
        }
    }

    return panel
}
