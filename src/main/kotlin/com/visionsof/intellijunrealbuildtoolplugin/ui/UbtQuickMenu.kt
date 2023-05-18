package com.visionsof.intellijunrealbuildtoolplugin.ui

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareToggleAction
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import com.visionsof.intellijunrealbuildtoolplugin.model.BuildConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.ParallelExecutorConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import javax.swing.JLabel
import kotlin.reflect.KMutableProperty1


fun buildUbtQuickMenu(config: UbtConfiguration, onApply : () -> Unit, onCancel: () -> Unit): DialogPanel {

    lateinit var panel : DialogPanel

    val maxProcessors = Runtime.getRuntime().availableProcessors()

    val buildConfigProp = UbtConfiguration::buildConfiguration
    val parallelExecutorProp = UbtConfiguration::parallelExecutor

    fun <T, U> configNullCheck (parentProp: KMutableProperty1<UbtConfiguration, T?>, prop: KMutableProperty1<T, U?>, defaultValue: U ) : U {
        val parentVal = parentProp.get(config)
        if(parentVal == null || prop.get(parentVal) == null) {
            return defaultValue
        }
        return (prop.get(parentVal))!!
    }

    fun <T, U> configAssign (parentProp: KMutableProperty1<UbtConfiguration, T?>, factory: () -> T,  prop: KMutableProperty1<T, U?>, value: U? ) {
        var parentVal = parentProp.get(config)
        if(parentVal == null) {
            parentVal = factory()
            parentProp.set(config, parentVal)
        }
        prop.set(parentVal!!, value)
    }

    lateinit var priorityExecutorComment: JLabel

    val priorityExecutorCommentBuilder = {
        var priority = "Incredibuild"
        if(config.buildConfiguration != null) {
            if(config.buildConfiguration!!.allowHybrid != null && config.buildConfiguration!!.allowHybrid!!) {
                priority = "Hybrid"
            } else if(config.buildConfiguration!!.allowXge == null || config.buildConfiguration!!.allowXge!!) {
              // no-op. We're already set
            } else if(config.buildConfiguration!!.allowFastBuild == null || config.buildConfiguration!!.allowFastBuild!!) {
                priority = "FASTBuild"
            } else if(config.buildConfiguration!!.allowSNDBS == null || config.buildConfiguration!!.allowSNDBS!!) {
                priority = "SNDBS"
            } else if(config.buildConfiguration!!.allowHordeCompute == null || config.buildConfiguration!!.allowHordeCompute!!) {
                priority = "Horde"
            }
        }

        "Priority $priority"
    }

    fun executorActionFactory(name: String, prop: KMutableProperty1<BuildConfiguration, Boolean?>, selectedDefault: Boolean) : DumbAwareToggleAction {
        return object : DumbAwareToggleAction(name){
            override fun isSelected(e: AnActionEvent): Boolean {
                if(config.buildConfiguration == null || prop.get(config.buildConfiguration!!) == null) {
                    return selectedDefault
                }
                return prop.get(config.buildConfiguration!!)!!
            }

            override fun setSelected(e: AnActionEvent, state: Boolean) {
                if(config.buildConfiguration == null) {
                    config.buildConfiguration = BuildConfiguration()
                }

                prop.set(config.buildConfiguration!!, state)
                priorityExecutorComment.text = priorityExecutorCommentBuilder()
            }
        }
    }

    panel = panel() {
        group ("Build Configuration") {
            row() {
                label("Executors:")
            }
            row() {
                actionsButton(
                    executorActionFactory("1. Hybrid", BuildConfiguration::allowHybrid, false),
                    executorActionFactory("2. Incredibuild", BuildConfiguration::allowXge, true),
                    executorActionFactory("3. FASTBuild", BuildConfiguration::allowFastBuild, true),
                )
                priorityExecutorComment = label(priorityExecutorCommentBuilder()).component
            }
            row() {
                checkBox("Unity Build")
                    .bindSelected(
                        { configNullCheck(buildConfigProp, BuildConfiguration::useUnityBuild, true)},
                        { configAssign(UbtConfiguration::buildConfiguration, ::BuildConfiguration, BuildConfiguration::useUnityBuild, it) }
                    )
            }

            row() {
                checkBox("Adaptive Unity Build")
                    .bindSelected(
                        { configNullCheck(buildConfigProp, BuildConfiguration::useAdaptiveUnityBuild, true)},
                        { configAssign(UbtConfiguration::buildConfiguration, ::BuildConfiguration, BuildConfiguration::useAdaptiveUnityBuild, it) }
                    )
            }

            row() {
                checkBox("Warnings As Errors")
                    .bindSelected(
                        { configNullCheck(buildConfigProp, BuildConfiguration::warningsAsErrors, false)},
                        { configAssign(UbtConfiguration::buildConfiguration, ::BuildConfiguration, BuildConfiguration::warningsAsErrors, it) }
                    )
            }

            row() {
                spinner(IntRange(0, maxProcessors))
                    .label("Max Parallel Actions")
                    .bindIntValue(
                        { configNullCheck(buildConfigProp, BuildConfiguration::maxParallelActions, 0)},
                        { configAssign(UbtConfiguration::buildConfiguration, ::BuildConfiguration, BuildConfiguration::maxParallelActions, it) }
                    )
            }
        }

        group ("Parallel Executor") {
            row(){
                label("Process Count Multiplier")
            }
            row() {
                // TODO: Make make builder function with the quick action menu
                slider(0,20, 1, 5)
                    .labelTable(mapOf(
                        0 to JLabel("0"),
                        10 to JLabel("1"),
                        20 to JLabel("2")
                    )).bindValue(
                        { (configNullCheck(parallelExecutorProp, ParallelExecutorConfiguration::processorCountMultiplier, 1.0) * 10.0).toInt() },
                        { configAssign(UbtConfiguration::parallelExecutor, ::ParallelExecutorConfiguration, ParallelExecutorConfiguration::processorCountMultiplier, (it.toDouble() / 10))
                    })
            }
        }
        row() {
            link("Edit Configuration") {
                onCancel()
                UbtGlobalConfigurationWindow(config, onApply).show()
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
