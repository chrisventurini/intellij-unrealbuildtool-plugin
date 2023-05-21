package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders

import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.Row
import com.intellij.ui.dsl.builder.bindValue
import com.intellij.ui.dsl.builder.labelTable
import com.visionsof.intellijunrealbuildtoolplugin.model.ParallelExecutorConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtDoubleConfigProp
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.getAnnotation
import javax.swing.JLabel
import kotlin.reflect.KMutableProperty0

private fun buildControl(rowBuilder: Row, prop: KMutableProperty0<Double?>, annon: UbtDoubleConfigProp) {
    rowBuilder.slider(0, 20, 1, 5)
        .labelTable(
            mapOf(
                0 to JLabel("0"),
                10 to JLabel("1"),
                20 to JLabel("2")
            )
        ).bindValue({
            (prop.get() ?: annon.default).toInt() * 10
        }, {
            prop.set(it.toDouble() / 10)
        })
}

internal fun buildProcessCountMultiplierSlider(parentBuilder: Row, config: ParallelExecutorConfiguration) {
    val processCountMultiProp = config::processorCountMultiplier
    val processCountMultiAnnon = getAnnotation<UbtDoubleConfigProp>(processCountMultiProp, true)!!

    buildControl(parentBuilder, processCountMultiProp, processCountMultiAnnon)
}

internal fun buildProcessCountMultiplierSliderWithLayout(parentBuilder: Panel, config: ParallelExecutorConfiguration) {
    val processCountMultiProp = config::processorCountMultiplier
    val processCountMultiAnnon = getAnnotation<UbtDoubleConfigProp>(processCountMultiProp, true)!!

    buildDefaultLayout(parentBuilder, processCountMultiAnnon.description) {
        buildControl(this, processCountMultiProp, processCountMultiAnnon)
    }
}
