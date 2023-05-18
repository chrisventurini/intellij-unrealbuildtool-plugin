package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.*

@UbtConfigTabUi("Incredibuild Executor" )
fun buildIncredibuildConfigurationUi(masterConfig: UbtConfiguration): DialogPanel {
    val config = checkConfig(masterConfig, UbtConfiguration::incredibuildExecutorConfiguration, ::IncredibuildExecutorConfiguration)

    return panel() {
        group ("General"){
            buildBasicPropUi(this, config!!::unavailableIfInUse)
            buildBasicPropUi(this, config::useVCCompilerMode)
            buildBasicPropUi(this, config::minActions)
        }
        group ("Network"){
            buildBasicPropUi(this, config!!::allowRemoteLinking)
            buildEnablementSection(this, config::allowOverVpn) {
                buildBasicPropUi(this, config::vpnSubnets)
            }
        }
    }
}