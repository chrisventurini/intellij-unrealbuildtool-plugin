package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.services.BasicPropUiFactoryService

@UbtConfigTabUi("Windows Platform" )
fun buildWindowsPlatformConfigurationUi(masterConfig: UbtConfiguration): DialogPanel {
    val config = checkConfig(masterConfig, UbtConfiguration::windowsPlatform, ::WindowsPlatformConfiguration)

    val propService = BasicPropUiFactoryService.getInstance()

    return panel() {
        group ("Compilation"){
            propService.build(this, config!!::compiler)
            propService.build(this, config::compilerVersion)
            propService.build(this, config::windowsSdkVersion)
            propService.build(this, config::compilerTrace)
            propService.build(this, config::pchMemoryAllocationFactor)
        }
        group ("Pathing"){
            propService.build(this, config!!::maxNestedPathLength)
            propService.build(this, config::maxNestedPathLength)
        }
    }
}