package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders

import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.RowsRange
import com.visionsof.intellijunrealbuildtoolplugin.services.BasicPropUiFactoryService
import kotlin.reflect.KMutableProperty0

fun buildEnablementSection(parentBuilder: Panel, enablementProp: KMutableProperty0<Boolean?>, rangeBuilder: Panel.() -> Unit) {
    @Suppress("JoinDeclarationAndAssignment")
    lateinit var enablementRange: RowsRange

    val propService = BasicPropUiFactoryService.getInstance()

    val enabled = propService.build(parentBuilder, enablementProp) { enablementRange.visible(it) }

    enablementRange = parentBuilder.rowsRange {
        parentBuilder.rangeBuilder()
    }.visible(enabled!!)
}