@file:Suppress("UNCHECKED_CAST")

package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.basic

import com.intellij.openapi.observable.properties.ObservableMutableProperty
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.JBColor
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.bindText
import com.intellij.util.ui.JBUI
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.buildSpinnerPropUi
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.buildFilePropUi
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.buildStandardControl
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.getAnnotation
import java.awt.Color
import java.awt.Dimension
import java.awt.Point
import javax.swing.BoxLayout
import javax.swing.DefaultListModel
import javax.swing.ListSelectionModel
import javax.swing.ScrollPaneConstants
import kotlin.reflect.KMutableProperty0

internal annotation class BasicComponentBuilder(val type: String)

@BasicComponentBuilder("kotlin.Int?")
fun <T> buildIntBasicComponent(parentBuilder: Panel, prop: KMutableProperty0<T?>, enabled: Boolean = true, onChange: (value: T) -> Unit) : T? {
    return buildStandardControl<UbtIntConfigProp, T>(parentBuilder, prop, onChange) { annon, observableProp ->
        intTextField()
            .label(annon.prettyTitle)
            .bindIntText(observableProp as ObservableMutableProperty<Int>)
            .enabled(enabled)
    }
}

@BasicComponentBuilder("kotlin.Double?")
fun <T> buildDoubleBasicComponent(parentBuilder: Panel, prop: KMutableProperty0<T?>, enabled: Boolean = true, onChange: (value: T) -> Unit) : T? {
    return buildSpinnerPropUi(parentBuilder, prop as KMutableProperty0<Double?>, 0.0..100000000000.0, 0.1) as T
}

@BasicComponentBuilder("kotlin.Boolean?")
fun <T> buildBooleanBasicComponent(parentBuilder: Panel, prop: KMutableProperty0<T?>, enabled: Boolean = true, onChange: (value: T) -> Unit) : T? {
    return  buildStandardControl<UbtBooleanConfigProp, T>(parentBuilder, prop,onChange) { annon, observableProp ->
        checkBox(annon.prettyTitle)
            .bindSelected(observableProp as ObservableMutableProperty<Boolean>)
            .enabled(enabled)
    }
}

@BasicComponentBuilder("kotlin.String?")
fun <T> buildStingBasicComponent(parentBuilder: Panel, prop: KMutableProperty0<T?>, enabled: Boolean = true, onChange: (value: T) -> Unit) : T? {
    val fileAnnon = getAnnotation<UbtFileConfigProp>(prop, false)

    if (fileAnnon == null) {
        return buildStandardControl<UbtStringConfigProp, T>(parentBuilder, prop, onChange) { annon, observableProp ->
            textField()
                .label(annon.prettyTitle)
                .bindText(observableProp as ObservableMutableProperty<String>)
                .enabled(enabled)
        }
    }

    buildFilePropUi(parentBuilder, prop, fileAnnon)

    return null
}

@BasicComponentBuilder("kotlin.Array<kotlin.String>?")
fun <T> buildStingArrayBasicComponent(parentBuilder: Panel, prop: KMutableProperty0<T?>, enabled: Boolean = true, onChange: (value: T) -> Unit) : T? {
     return buildStandardControl<UbtStringArrayConfigProp, T>(parentBuilder, prop, onChange) { annon, _ ->
            val values = (prop.get() as Array<String>?) ?: arrayOf()
            val listModel = DefaultListModel<String>().apply { addAll( values.toList() ) }

            parentBuilder.row { label(annon.prettyTitle) }

            parentBuilder.row {
                val prefSize = Dimension(100, 75)
                val ipAddressList = JBList(listModel).apply {
                    setEmptyText("None")
                    fixedCellWidth = prefSize.width
                    prototypeCellValue = annon.prototypeFormat
                    selectionMode = ListSelectionModel.SINGLE_SELECTION
                }

                parentBuilder.onApply {
                    val allValues = mutableListOf<String>()
                    for(i in 0 until listModel.size()) {
                        allValues.add(listModel.getElementAt(i) as String)
                    }
                    prop.set(allValues.toTypedArray() as T)
                }

                val containerPanel = JBPanel<JBPanel<*>>().apply { preferredSize = prefSize }
                containerPanel.layout = BoxLayout(containerPanel, BoxLayout.Y_AXIS)
                containerPanel.add(JBScrollPane(ipAddressList).apply {
                    horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                })
                cell(containerPanel)

                button("Remove") {
                    val selectedValue = ipAddressList.selectedValue
                    if(selectedValue != null) {
                        JBPopupFactory.getInstance().createConfirmation("Are you sure?", {
                            listModel.removeElement(selectedValue)
                        }, 1).showInFocusCenter()
                    }
                }
            }

            parentBuilder.row {
                val newValueTextField = textField().apply { component.size.width = 100 }

                button("Add") {
                    val value = newValueTextField.component.text
                    if(annon.regexValidation != "" && Regex(annon.regexValidation).matches(value)) {
                        listModel.addElement(value)
                        newValueTextField.component.text = ""
                        newValueTextField.component.border = JBUI.Borders.customLine(JBColor.border())
                    } else {
                        newValueTextField.component.border = JBUI.Borders.customLine(Color.RED)
                        val errBalloon = JBPopupFactory.getInstance().createBalloonBuilder(JBLabel("Not Valid"))
                            .setFadeoutTime(1000)

                        val location = RelativePoint(newValueTextField.component, Point(30, 0))
                        errBalloon.createBalloon().show(location, Balloon.Position.above)
                    }
                }
            }
        }
}