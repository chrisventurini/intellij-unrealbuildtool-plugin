@file:Suppress("UNCHECKED_CAST")

package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders

import com.intellij.ui.dsl.builder.*
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.textformatting.ExpressionNumberFormatter
import javax.swing.JSpinner
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.typeOf
import javax.swing.JSpinner.NumberEditor
import javax.swing.text.DefaultFormatterFactory
import kotlin.reflect.KClass


fun <T : Comparable<T>> buildSpinnerPropUi(parentBuilder: Panel, prop: KMutableProperty0<T?>, closedRange: ClosedRange<T>, step: T, enabled: Boolean = true, onChange: (value: T) -> Unit = {}) : T {

    var returnDefault : T? = null

    fun completeSpinner(prettyTitle:String, description: String, classType: KClass<*>, spinnerFactory: (Row) -> Cell<JSpinner>) {

        buildDefaultLayout(parentBuilder, description) {
            spinnerFactory(this)
                .label(prettyTitle)
                .enabled(enabled)
                .applyToComponent {
                    val editor = this.editor as NumberEditor
                    val expNumberFormatter = ExpressionNumberFormatter(editor.format).apply {
                        valueClass = classType.java
                        install(editor.textField)
                    }

                    (editor.textField.formatterFactory as DefaultFormatterFactory).editFormatter = expNumberFormatter

                    addChangeListener {
                        var valueAsType = this.value as T

                        if(valueAsType > closedRange.endInclusive) {
                            this.value = closedRange.endInclusive
                            valueAsType = closedRange.endInclusive
                        } else if (valueAsType < closedRange.start) {
                            this.value = closedRange.start
                            valueAsType = closedRange.start
                        }

                        onChange(valueAsType)
                    }
                }
        }
    }

    when (prop.returnType) {
        typeOf<Double?>() -> {
            val annon = getAnnotation<UbtDoubleConfigProp>(prop, true)!!
                .apply { returnDefault = this.default as T }

            completeSpinner(annon.prettyTitle, annon.description, Double::class) { row ->
                row.spinner(closedRange as ClosedRange<Double>, step as Double)
                    .bindValue({ getPropFunc(prop, annon.default as T) as Double }, { prop.set(it as T) })
            }
        }
        typeOf<Int?>() -> {
            val annon = getAnnotation<UbtIntConfigProp>(prop, true)!!
                .apply { returnDefault = this.default as T }

            val intRange = closedRange as ClosedRange<Int>

            completeSpinner(annon.prettyTitle, annon.description, Int::class) { row ->
                row.spinner(IntRange(intRange.start, intRange.endInclusive), step as Int)
                    .bindIntValue({ getPropFunc(prop, annon.default as T) as Int }, { prop.set(it as T) })
            }
        }
    }

    return returnDefault!!
}
