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
                returnDefault =
                    buildStandardControl<UbtBooleanConfigProp, T>(parentBuilder, prop,onChange) { annon, observableProp ->
                    checkBox(annon.prettyTitle)
                        .bindSelected(observableProp as ObservableMutableProperty<Boolean>)
                        .enabled(enabled)
                }

            }

            typeOf<Int?>() -> {
                returnDefault =
                    buildStandardControl<UbtIntConfigProp, T>(parentBuilder, prop, onChange) { annon, observableProp ->
                        intTextField()
                            .label(annon.prettyTitle)
                            .bindIntText(observableProp as ObservableMutableProperty<Int>)
                    }
            }

            typeOf<String?>() -> {
                val fileAnnon = getAnnotation<UbtFileConfigProp>(prop, false)
                if (fileAnnon == null) {
                    returnDefault =
                        buildStandardControl<UbtStringConfigProp, T>(parentBuilder, prop, onChange) { annon, observableProp ->
                        textField()
                            .label(annon.prettyTitle)
                            .bindText(observableProp as ObservableMutableProperty<String>)
                            .enabled(enabled)
                    }
                } else {
                    buildFilePropUi(parentBuilder, prop, fileAnnon)
                }
            }

            typeOf<Array<String>?>() -> {
                returnDefault =
                    buildStandardControl<UbtStringArrayConfigProp, T>(parentBuilder, prop, onChange) { annon, _ ->
                    val values = (prop.get() as Array<String>?) ?: arrayOf()
                    val ipAddressesModel = DefaultListModel<String>().apply { addAll( values.toList() ) }

                    parentBuilder.row { label(annon.prettyTitle) }

                    parentBuilder.row {
                        val prefSize = Dimension(100, 75)
                        val ipAddressList = JBList(ipAddressesModel).apply {
                            setEmptyText("None")
                            fixedCellWidth = prefSize.width
                            prototypeCellValue = "XXX.XXX.XXX.XXX"
                            selectionMode = ListSelectionModel.SINGLE_SELECTION
                        }

                        parentBuilder.onApply {
                            val allValues = mutableListOf<String>()
                            for(i in 0 until ipAddressesModel.size()) {
                                allValues.add(ipAddressesModel.getElementAt(i) as String)
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
                                    ipAddressesModel.removeElement(selectedValue)
                                }, 1).showInFocusCenter()
                            }
                        }
                    }

                    parentBuilder.row {
                        val newIpTextField = textField().apply { component.size.width = 100 }

                        button("Add") {
                            val value = newIpTextField.component.text
                            if(annon.regexValidation != "" && Regex(annon.regexValidation).matches(value)) {
                                ipAddressesModel.addElement(value)
                                newIpTextField.component.text = ""
                                newIpTextField.component.border = JBUI.Borders.customLine(JBColor.border())
                            } else {
                                newIpTextField.component.border = JBUI.Borders.customLine(Color.RED)
                                val errBalloon = JBPopupFactory.getInstance().createBalloonBuilder(JBLabel("Not Valid"))
                                    .setFadeoutTime(1000)

                                val location = RelativePoint(newIpTextField.component, Point(20, newIpTextField.component.height))
                                errBalloon.createBalloon().show(location, Balloon.Position.below)
                            }
                        }
                    }
                }
            }

            typeOf<Double?>() -> {
                returnDefault = buildSpinnerPropUi(parentBuilder, prop as KMutableProperty0<Double?>, 0.0..100000000000.0, 0.1) as T
            }
        }
    }

    return returnDefault
}
