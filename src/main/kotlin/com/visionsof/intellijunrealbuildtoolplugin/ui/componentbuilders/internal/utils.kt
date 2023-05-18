@file:Suppress("UNCHECKED_CAST")

package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal

import com.intellij.openapi.observable.properties.ObservableMutableProperty
import com.intellij.openapi.observable.properties.PropertyGraph
import com.intellij.ui.dsl.builder.*
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import kotlin.reflect.*
import kotlin.reflect.full.findAnnotation


internal inline fun <reified T: Annotation> getAnnotation(prop: KMutableProperty0<*>, throwOnNull: Boolean): T? {
    val annon = prop.findAnnotation<T>()

    if(throwOnNull && annon == null) {
        throw Exception("${T::class.simpleName} annotation is missed for ${prop.name}")
    }

    return annon
}


internal fun <T> buildObservableProp(prop: KMutableProperty0<T?>, defaultValue: T?, throwOnNull: Boolean, onChange: (value: T) -> Unit) : ObservableMutableProperty<T?> {

    if(throwOnNull && defaultValue == null) { throw Exception("default value cannot be null") }

    val initialValue = prop.get() ?: defaultValue

    val propGraph = PropertyGraph()
    val observableProp = propGraph.property(initialValue)
    observableProp.afterChange {
        prop.set(it)
        onChange(it!!)
    }

    return observableProp
}

internal fun <T> getPropFunc (prop: KMutableProperty0<T?>, defaultValue: T) : T {
    if(prop.get() == null) {
        return defaultValue
    }
    return prop.get()!!
}
