package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.services.BasicPropUiFactoryService
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.*

@UbtConfigTabUi("Incredibuild Executor" )
fun buildIncredibuildConfigurationUi(masterConfig: UbtConfiguration): DialogPanel {
    val config = checkConfig(masterConfig, UbtConfiguration::incredibuildExecutorConfiguration, ::IncredibuildExecutorConfiguration)

    val propService = BasicPropUiFactoryService.getInstance()

    return panel() {
        group ("General"){
            propService.build(this, config!!::unavailableIfInUse)
            propService.build(this, config::useVCCompilerMode)
            propService.build(this, config::minActions)
        }
        group ("Network"){
            propService.build(this, config!!::allowRemoteLinking)
            buildEnablementSection(this, config::allowOverVpn) {
                propService.build(this, config::vpnSubnets)
            }
        }
    }
}