package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.services.BasicPropUiFactoryService
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.textformatting.ExpressionNumberFormatter
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel
import javax.swing.text.DefaultFormatterFactory

@UbtConfigTabUi("Parallel Executor" )
fun buildParallelConfigurationUi(masterConfig: UbtConfiguration): DialogPanel {
    val config = checkConfig(masterConfig, UbtConfiguration::parallelExecutor, ::ParallelExecutorConfiguration)

    val propService = BasicPropUiFactoryService.getInstance()

    return panel() {
        group ("General"){
            propService.build(this, config!!::stopCompilationAfterErrors)

            buildProcessCountMultiplierSliderWithLayout(this, config)

            val memoryPerActionProp = config::memoryPerActionBytes
            val memoryPerActionAnnon = getAnnotation<UbtLongConfigProp>(memoryPerActionProp, true)!!
            buildDefaultLayout(this, memoryPerActionAnnon.description) {
                var spinner = JSpinner()
                var value = getPropFunc(memoryPerActionProp, memoryPerActionAnnon.default)
                spinner.model = SpinnerNumberModel(value, 0L, Long.MAX_VALUE, 1000000L )

                val format = "0000.000000E0"
                val editor = JSpinner.NumberEditor(spinner, format)
                val expNumberFormatter = ExpressionNumberFormatter(editor.format).apply {
                    valueClass = Long::class.java
                    install(editor.textField)
                }
                (editor.textField.formatterFactory as DefaultFormatterFactory).editFormatter = expNumberFormatter
                spinner.editor = editor

                label(memoryPerActionAnnon.prettyTitle)

                cell(spinner)

                comment("bytes (scientific notation)")
            }

            propService.build(this, config::processPriority)
        }
        group ("Logging"){
            propService.build(this, config!!::showCompilationTimes)
            propService.build(this, config::showPerActionCompilationTimes)
            propService.build(this, config::logActionCommandLines)
        }
    }
}