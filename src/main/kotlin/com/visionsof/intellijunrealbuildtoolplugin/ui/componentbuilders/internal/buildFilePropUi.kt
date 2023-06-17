@file:Suppress("UNCHECKED_CAST")

package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal

import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.observable.properties.ObservableMutableProperty
import com.intellij.ui.dsl.builder.*
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfigFilePropType
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtFileConfigProp
import com.visionsof.intellijunrealbuildtoolplugin.platform.Os
import com.visionsof.intellijunrealbuildtoolplugin.platform.getOs
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.buildDefaultLayout
import com.visionsof.intellijunrealbuildtoolplugin.utilities.findBy
import kotlin.reflect.KMutableProperty0


internal fun <T> buildFilePropUi(parentBuilder: Panel, prop: KMutableProperty0<T?>, fileAnnon: UbtFileConfigProp, enabled: Boolean = true, onChange: (value: T) -> Unit = {}) {
    buildDefaultLayout(parentBuilder, fileAnnon.description) {

        val observableProp = buildObservableProp(prop, fileAnnon.default as T?, false, onChange)

        val propType = UbtConfigFilePropType::value findBy fileAnnon.fileType

        val fileDesc : FileChooserDescriptor
                = if(propType == UbtConfigFilePropType.EXECUTABLE && getOs() == Os.WINDOWS) {
            FileChooserDescriptorFactory.createSingleFileDescriptor("exe")
        } else if (propType == UbtConfigFilePropType.DIRECTORY) {
            FileChooserDescriptorFactory.createSingleFolderDescriptor()
        } else {
            FileChooserDescriptorFactory.createSingleFileDescriptor()
        }

        val prettyTitle = fileAnnon.prettyTitle

        label(prettyTitle)
        textFieldWithBrowseButton(prettyTitle, null, fileDesc) { it.path }
            .bindText(observableProp as ObservableMutableProperty<String>)
            .enabled(enabled)
    }
}
