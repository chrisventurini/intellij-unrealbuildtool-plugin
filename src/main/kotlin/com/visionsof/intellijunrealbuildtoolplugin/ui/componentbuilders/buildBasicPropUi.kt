@file:Suppress("UNCHECKED_CAST")

package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders

import com.intellij.openapi.observable.properties.ObservableMutableProperty
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.JBColor
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.*
import com.intellij.ui.dsl.builder.*
import com.intellij.util.ui.JBUI
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.basic.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.*
import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import javax.swing.BoxLayout
import javax.swing.DefaultListModel
import javax.swing.ListSelectionModel
import javax.swing.ScrollPaneConstants
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.typeOf

// TODO: Convert when statement to factory
fun <T> buildBasicPropUi(parentBuilder: Panel, prop: KMutableProperty0<T?>, enabled: Boolean = true, onChange: (value: T) -> Unit = {}) : T? {
    var returnDefault: T? = null

    if((prop.returnType.classifier as KClass<*>).java.isEnum)
    {
        val enumAnnon = getAnnotation<UbtStringEnumConfigProp>(prop, true)!!
        buildEnumPropUi<T, String>(parentBuilder, prop, enumAnnon)

    } else {
        when (prop.returnType) {
            typeOf<Boolean?>() -> {
                returnDefault = buildBooleanBasicComponent(parentBuilder, prop, enabled, onChange)
            }

            typeOf<Int?>() -> {
                returnDefault = buildIntBasicComponent(parentBuilder, prop, enabled, onChange)
            }

            typeOf<String?>() -> {
                returnDefault = buildStingBasicComponent(parentBuilder, prop, enabled, onChange)
            }

            typeOf<Array<String>?>() -> {
                returnDefault = buildStingArrayBasicComponent(parentBuilder, prop, enabled, onChange)
            }

            typeOf<Double?>() -> {
                returnDefault = buildDoubleBasicComponent(parentBuilder, prop, enabled, onChange)
            }
        }
    }

    return returnDefault
}
