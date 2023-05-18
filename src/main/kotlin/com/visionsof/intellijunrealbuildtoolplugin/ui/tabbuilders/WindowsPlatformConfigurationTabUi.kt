package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.*

@UbtConfigTabUi("Windows Platform" )
fun buildWindowsPlatformConfigurationUi(masterConfig: UbtConfiguration): DialogPanel {
    val config = checkConfig(masterConfig, UbtConfiguration::windowsPlatform, ::WindowsPlatformConfiguration)

    return panel() {
        group ("Compilation"){
            buildBasicPropUi(this, config!!::compiler)
            buildBasicPropUi(this, config::compilerVersion)
            buildBasicPropUi(this, config::windowsSdkVersion)
            buildBasicPropUi(this, config::compilerTrace)
            buildBasicPropUi(this, config::pchMemoryAllocationFactor)
        }
        group ("Pathing"){
            buildBasicPropUi(this, config!!::maxNestedPathLength)
            buildBasicPropUi(this, config::maxNestedPathLength)
        }
    }
}