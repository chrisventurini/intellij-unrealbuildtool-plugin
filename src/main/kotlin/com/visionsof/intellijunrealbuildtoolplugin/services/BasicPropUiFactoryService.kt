@file:Suppress("UNCHECKED_CAST")

package com.visionsof.intellijunrealbuildtoolplugin.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.ui.dsl.builder.*
import com.jetbrains.rd.util.addUnique
import com.visionsof.intellijunrealbuildtoolplugin.model.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.basic.*
import com.visionsof.intellijunrealbuildtoolplugin.ui.componentbuilders.internal.*
import com.visionsof.intellijunrealbuildtoolplugin.utilities.getAllAnnotatedFunctions
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.typeOf

internal class BasicPropUiFactoryService() {
    companion object {
        @JvmStatic
        fun getInstance(): BasicPropUiFactoryService = ApplicationManager.getApplication().getService(BasicPropUiFactoryService::class.java)
    }

    private var builderMap: MutableMap<String, KFunction<*>> = mutableMapOf()

    init {
        val allBuilderFuncs = getAllAnnotatedFunctions(BasicComponentBuilder::class)

        for(builderFunc in allBuilderFuncs) {
            val annon = builderFunc.findAnnotation<BasicComponentBuilder>()

            builderMap[annon!!.type] = builderFunc
        }
    }

    fun <T> build(parentBuilder: Panel, prop: KMutableProperty0<T?>, withDescription: Boolean = true, enabled: Boolean = true, onChange: (value: T) -> Unit = {}) : T? {
        var returnDefault: T? = null

        if((prop.returnType.classifier as KClass<*>).java.isEnum) {
            val enumAnnon = getAnnotation<UbtStringEnumConfigProp>(prop, true)!!
            buildEnumPropUi<T, String>(parentBuilder, prop, enumAnnon)
        } else {
            returnDefault = builderMap[prop.returnType.toString()]?.call(parentBuilder, prop, withDescription, enabled, onChange) as T?
        }

        return returnDefault
    }
}