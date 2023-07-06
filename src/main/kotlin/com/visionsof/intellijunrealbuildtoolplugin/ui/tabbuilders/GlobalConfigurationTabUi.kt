package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import com.visionsof.intellijunrealbuildtoolplugin.model.BuildConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.services.BasicPropUiFactoryService
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.*

@UbtConfigTabUi("Global" )
fun buildGlobalConfigurationUi(masterConfig: UbtConfiguration): DialogPanel {
    val config = checkConfig(masterConfig, UbtConfiguration::buildConfiguration, ::BuildConfiguration)
    val maxProcessors = Runtime.getRuntime().availableProcessors()

    val propService = BasicPropUiFactoryService.getInstance()

    return panel() {
        group("Executors") {
            propService.build(this, config!!::allowHybrid)
            propService.build(this, config::allowXge)
            propService.build(this, config::allowFastBuild)
        }
        group ("General"){
            propService.build(this, config!!::warningsAsErrors)
            propService.build(this, config::printDebugInfo)
            buildSpinnerPropUi(this, config::maxParallelActions, 0..(maxProcessors * 2), 1 )
            propService.build(this, config::allCores)
        }
        group ("Unity Builds") {
            buildEnablementSection(this, config!!::useUnityBuild) {
                propService.build(this, config::forceUnityBuild)
                propService.build(this, config::useAdaptiveUnityBuild)
            }
        }
        group ("Sanitizers") {
            propService.build(this, config!!::enableAddressSanitizer)
            propService.build(this, config::enableThreadSanitizer)
            propService.build(this, config::enableUndefinedBehaviorSanitizer)
        }
    }
}