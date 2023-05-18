@file:Suppress("UNCHECKED_CAST")

package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal

import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.ui.dsl.builder.Panel
import com.intellij.util.castSafelyTo
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtStringEnumConfigProp
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.buildDefaultLayout
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty1
import kotlin.reflect.full.primaryConstructor

// TODO: Probably going to need to handle more than strings
fun <T, U> buildEnumPropUi(parentBuilder: Panel, prop: KMutableProperty0<T?>, annon: UbtStringEnumConfigProp, enabled: Boolean = true, onChange: (value: T) -> Unit = {}) {

    buildDefaultLayout(parentBuilder, annon.description) { ->
        val propClass = (prop.returnType.classifier as KClass<*>)
        val propJavaClass = propClass.java

        var enumConstructorParams = propClass.primaryConstructor!!.parameters

        lateinit var valueProp: KProperty1<T, U>

        if (enumConstructorParams.isEmpty()) {
            TODO()
        } else {
            // TODO: Is this safe?
            valueProp = propClass.members.firstOrNull { it.name == enumConstructorParams[0].name }
                .castSafelyTo<KProperty1<T, U>>()!!
        }

        val enumValues = propJavaClass.enumConstants

        var propVal = prop.get()
        if(propVal == null)
        {
            propVal = enumValues.firstOrNull { valueProp.get(it as T) == annon.default } as T
        }

        val propGraph = PropertyGraph()
        val observableProp = propGraph.property<U>(valueProp.get(propVal!!))

        observableProp.afterChange {
            prop.set(enumValues.firstOrNull { enumVal -> valueProp.get(enumVal as T) == it } as T)
            onChange(it as T)
        }

        parentBuilder.row(annon.prettyTitle) {
            segmentedButton(enumValues.map { valueProp.get(it as T) }) { it.toString() }
                .bind(observableProp)
                .enabled(enabled)
        }
    }
}