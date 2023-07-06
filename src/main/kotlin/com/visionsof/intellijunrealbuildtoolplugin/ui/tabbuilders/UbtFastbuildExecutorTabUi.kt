package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import com.visionsof.intellijunrealbuildtoolplugin.model.FastBuildExecutorConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.services.BasicPropUiFactoryService
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.*

@UbtConfigTabUi("FASTBuild Executor" )
fun buildUbtFastbuildExecutorTabUi(masterConfig: UbtConfiguration): DialogPanel {

    val config = checkConfig(masterConfig, UbtConfiguration::fastBuildConfiguration, ::FastBuildExecutorConfiguration)

    val propService = BasicPropUiFactoryService.getInstance()

    return panel() {
        group ("General") {
            propService.build(this, config!!::executablePath)
            propService.build(this, config::stopOnError)
            propService.build(this, config::msvcCRTRedistVersion)
        }
        group ("Distribution") {
            buildEnablementSection(this, config!!::enableDistribution) {
                propService.build(this, config::brokeragePath)
            }
        }
        group("Cache") {
            buildEnablementSection(this, config!!::enableDistribution) {
                propService.build(this, config::buildCachePath)
                propService.build(this, config::cacheMode)
            }
        }
    }
}