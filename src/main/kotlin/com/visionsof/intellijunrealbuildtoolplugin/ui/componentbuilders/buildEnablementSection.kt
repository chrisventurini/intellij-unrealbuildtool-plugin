package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders

import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.RowsRange
import kotlin.reflect.KMutableProperty0

fun buildEnablementSection(parentBuilder: Panel, enablementProp: KMutableProperty0<Boolean?>, rangeBuilder: Panel.() -> Unit) {
    @Suppress("JoinDeclarationAndAssignment")
    lateinit var enablementRange: RowsRange

    val enabled = buildBasicPropUi(parentBuilder, enablementProp) { enablementRange.visible(it) }

    enablementRange = parentBuilder.rowsRange {
        parentBuilder.rangeBuilder()
    }.visible(enabled!!)
}