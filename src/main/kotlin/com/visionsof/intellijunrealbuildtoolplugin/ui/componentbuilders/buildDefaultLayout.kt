package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders

import com.intellij.ui.dsl.builder.*

internal fun buildDefaultLayout(parentBuilder: Panel, description: String, controlBuilder: Row.() -> Unit) {
    parentBuilder.row {
        controlBuilder()
    }

    if(description != "") {
        parentBuilder.row {
            comment(description, maxLineLength = 100)
        }
    }
}
