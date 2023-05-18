package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.*
import com.visionsof.intellijunrealbuildtoolplugin.model.FastBuildExecutorConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.*

@UbtConfigTabUi("FASTBuild Executor" )
fun buildUbtFastbuildExecutorTabUi(masterConfig: UbtConfiguration): DialogPanel {

    val config = checkConfig(masterConfig, UbtConfiguration::fastBuildConfiguration, ::FastBuildExecutorConfiguration)

    return panel() {
        group ("General") {
            buildBasicPropUi(this, config!!::executablePath)
            buildBasicPropUi(this, config::stopOnError)
            buildBasicPropUi(this, config::msvcCRTRedistVersion)
        }
        group ("Distribution") {
            buildEnablementSection(this, config!!::enableDistribution) {
                buildBasicPropUi(this, config::brokeragePath)
            }
        }
        group("Cache") {
            buildEnablementSection(this, config!!::enableDistribution) {
                buildBasicPropUi(this, config::buildCachePath)
                buildBasicPropUi(this, config::cacheMode)
            }
        }
    }
}