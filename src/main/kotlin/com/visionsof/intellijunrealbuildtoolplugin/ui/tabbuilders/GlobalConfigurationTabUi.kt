package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.visionsof.intellijunrealbuildtoolplugin.model.BuildConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.*

@UbtConfigTabUi("Global" )
fun buildGlobalConfigurationUi(masterConfig: UbtConfiguration): DialogPanel {
    val config = checkConfig(masterConfig, UbtConfiguration::buildConfiguration, ::BuildConfiguration)

    val maxProcessors = Runtime.getRuntime().availableProcessors()

    return panel() {
        group("Executors") {
            buildBasicPropUi(this, config!!::allowHybrid)
            buildBasicPropUi(this, config::allowXge)
            buildBasicPropUi(this, config::allowFastBuild)
        }
        group ("General"){
            buildBasicPropUi(this, config!!::warningsAsErrors)
            buildBasicPropUi(this, config::printDebugInfo)
            buildSpinnerPropUi(this, config::maxParallelActions, 0..(maxProcessors * 2), 1 )
            buildBasicPropUi(this, config::allCores)
        }
        group ("Unity Builds") {
            buildEnablementSection(this, config!!::useUnityBuild) {
                buildBasicPropUi(this, config::forceUnityBuild)
                buildBasicPropUi(this, config::useAdaptiveUnityBuild)
            }
        }
        group ("Sanitizers") {
            buildBasicPropUi(this, config!!::enableAddressSanitizer)
            buildBasicPropUi(this, config::enableThreadSanitizer)
            buildBasicPropUi(this, config::enableUndefinedBehaviorSanitizer)
        }
    }
}