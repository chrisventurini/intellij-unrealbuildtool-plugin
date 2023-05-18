package com.visionsof.intellijunrealbuildtoolplugin.model

import javax.xml.bind.annotation.*

private const val IDLE_VAL_INTERNAL = "Idle"
private const val BELOW_NORMAL_VAL_INTERNAL = "BelowNormal"
private const val NORMAL_VAL_INTERNAL = "Normal"
private const val ABOVE_NORMAL_VAL_INTERNAL = "AboveNormal"
private const val HIGH_VAL_INTERNAL = "High"

@XmlEnum
enum class ProcessPriority(val value: String) {
    @XmlEnumValue(IDLE_VAL_INTERNAL)
    IDLE(IDLE_VAL_INTERNAL),

    @XmlEnumValue(BELOW_NORMAL_VAL_INTERNAL)
    BELOW_NORMAL(BELOW_NORMAL_VAL_INTERNAL),

    @XmlEnumValue(NORMAL_VAL_INTERNAL)
    NORMAL(NORMAL_VAL_INTERNAL),

    @XmlEnumValue(ABOVE_NORMAL_VAL_INTERNAL)
    ABOVE_NORMAL(ABOVE_NORMAL_VAL_INTERNAL),

    @XmlEnumValue(HIGH_VAL_INTERNAL)
    HIGH(HIGH_VAL_INTERNAL);

    // To be able to use the values on Annotations
    companion object  {
        const val IDLE_VAL = IDLE_VAL_INTERNAL
        const val BELOW_NORMAL_VAL = BELOW_NORMAL_VAL_INTERNAL
        const val NORMAL_VAL = NORMAL_VAL_INTERNAL
        const val ABOVE_NORMAL_VAL = ABOVE_NORMAL_VAL_INTERNAL
        const val HIGH_VAL = HIGH_VAL_INTERNAL
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
data class ParallelExecutorConfiguration(

    // General

    @field:XmlElement(name = "bStopCompilationAfterErrors", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Stop Compilation After Errors",  "When enabled, will stop compiling targets after a compile error occurs.")
    var stopCompilationAfterErrors: Boolean? = null,

    @field:XmlElement(name="ProcessorCountMultiplier", namespace = NAMESPACE)
    @property:UbtDoubleConfigProp("Processor Count Multiplier", "Processor count multiplier for local execution. Can be below 1 to reserve CPU for other tasks.", 1.0)
    var processorCountMultiplier : Double? = null,

    @field:XmlElement(name="MemoryPerActionBytes", namespace = NAMESPACE)
    @property:UbtLongConfigProp("Memory Per-Action ", "Free memory per action in bytes, used to limit the number of parallel actions if the machine is memory starved. Set to 0 to disable free memory checking.", 16106127361)
    var memoryPerActionBytes : Long? = null,

    @field:XmlElement(name = "ProcessPriority", namespace = NAMESPACE)
    @property:UbtStringEnumConfigProp("Process Priority", "The priority to set for spawned processes", ProcessPriority.BELOW_NORMAL_VAL)
    var processPriority: ProcessPriority? = null,

    // Logging

    @field:XmlElement(name = "bShowCompilationTimes", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Show Compilation Times",  "Whether to show compilation times along with worst offenders or not.")
    var showCompilationTimes: Boolean? = null,

    @field:XmlElement(name = "bShowPerActionCompilationTimes", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Show Per-Action Compilation Times",  "Whether to show compilation times for each executed action.")
    var showPerActionCompilationTimes: Boolean? = null,

    @field:XmlElement(name = "bLogActionCommandLines", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Log Action Commandlines",  "Whether to log command lines for actions being executed")
    var logActionCommandLines: Boolean? = null,
)
