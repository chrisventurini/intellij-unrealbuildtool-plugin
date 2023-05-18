package com.visionsof.intellijunrealbuildtoolplugin.model

import javax.xml.bind.annotation.*

private const val FILE_VAL_INTERNAL = 0
private const val DIRECTORY_VAL_INTERNAL = 1
private const val EXECUTABLE_VAL_INTERNAL = 2

enum class UbtConfigFilePropType(var value: Int) {
    FILE(FILE_VAL_INTERNAL),

    DIRECTORY(DIRECTORY_VAL_INTERNAL),

    EXECUTABLE(EXECUTABLE_VAL_INTERNAL);

    // To be able to use the values on Annotations
    companion object  {
        const val FILE_VAL = FILE_VAL_INTERNAL
        const val DIRECTORY_VAL = DIRECTORY_VAL_INTERNAL
        const val EXECUTABLE_VAL = EXECUTABLE_VAL_INTERNAL
    }
}

@Target(AnnotationTarget.PROPERTY)
annotation class UbtBooleanConfigProp(val prettyTitle: String, val description: String = "", val default:Boolean = false)


@Target(AnnotationTarget.PROPERTY)
annotation class UbtStringConfigProp(val prettyTitle: String, val description: String = "", val default:String = "", val regexValidation:String = "")

@Target(AnnotationTarget.PROPERTY)
annotation class UbtStringArrayConfigProp(val prettyTitle: String, val description: String = "", val default:String = "", val regexValidation:String="")

@Target(AnnotationTarget.PROPERTY)
annotation class UbtIntConfigProp(val prettyTitle: String, val description: String = "", val default:Int = 0)

@Target(AnnotationTarget.PROPERTY)
annotation class UbtLongConfigProp(val prettyTitle: String, val description: String = "", val default:Long = 0)

@Target(AnnotationTarget.PROPERTY)
annotation class UbtDoubleConfigProp(val prettyTitle: String, val description: String = "", val default:Double = 0.0)

@Target(AnnotationTarget.PROPERTY)
annotation class UbtStringEnumConfigProp(val prettyTitle: String, val description: String = "", val default:String = "")

@Target(AnnotationTarget.PROPERTY)
annotation class UbtFileConfigProp(val prettyTitle: String, val description: String = "", val fileType: Int = UbtConfigFilePropType.FILE_VAL, val default:String = "")

