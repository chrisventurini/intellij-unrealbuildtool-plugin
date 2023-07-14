@file:Suppress("UNCHECKED_CAST")

package com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal

import com.intellij.openapi.observable.properties.ObservableMutableProperty
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.Row
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.buildDefaultLayout
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.full.memberProperties

internal inline fun <reified T: Annotation, U> buildStandardWrapper(parentBuilder: Panel, prop: KMutableProperty0<U?>, withDescription: Boolean = true, noinline onChange: (value: U) -> Unit, crossinline controlBuilder: Row.(T, ObservableMutableProperty<U?>) -> Unit) : U? {
    var returnDefault: U? = null

    var annon = getAnnotation<T>(prop, true)!!

    val descriptionMember = (annon::class.memberProperties.firstOrNull { it.name == "description" } ) ?:
    throw Exception("Annotation ${T::class.simpleName} missing description member")

    var description = ""
    if(withDescription)
    {
        description = descriptionMember.getter.call(annon) as String
    }

    buildDefaultLayout(parentBuilder, description) {
        val defaultMember = (annon::class.memberProperties.firstOrNull { it.name == "default" } ) ?:
        throw Exception("Annotation ${T::class.simpleName} missing default member")

        returnDefault = defaultMember.getter.call(annon) as U

        val observableProp = buildObservableProp(prop, returnDefault, true, onChange)

        this.controlBuilder(annon, observableProp)
    }

    return returnDefault
}