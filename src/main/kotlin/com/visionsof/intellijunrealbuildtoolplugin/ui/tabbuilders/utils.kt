package com.visionsof.intellijunrealbuildtoolplugin.ui.tabbuilders

import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import kotlin.reflect.KMutableProperty1

internal inline fun <reified T> checkConfig(config: UbtConfiguration, prop: KMutableProperty1<UbtConfiguration, T>, factory: () -> T) : T {
    if(prop.get(config) == null) {
        prop.set(config, factory() )
    }

    return prop.get(config)
}

