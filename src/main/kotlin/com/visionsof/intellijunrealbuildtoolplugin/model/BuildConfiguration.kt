package com.visionsof.intellijunrealbuildtoolplugin.model

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
data class BuildConfiguration (

    /* Executors */

    @property:UbtBooleanConfigProp("Hybrid","Using both remote and local executors")
    @field:XmlElement(name = "bAllowHybridExecutor", namespace = NAMESPACE)
    var allowHybrid: Boolean? = null,

    @property:UbtBooleanConfigProp("Incredibuild", "", true)
    @field:XmlElement(name = "bAllowXGE", namespace = NAMESPACE)
    var allowXge: Boolean? = null,

    @property:UbtBooleanConfigProp("FastBUILD", "", true)
    @field:XmlElement(name = "bAllowFASTBuild", namespace = NAMESPACE)
    var allowFastBuild: Boolean? = null,

    @field:XmlElement(name = "bAllowSNDBS", namespace = NAMESPACE)
    var allowSNDBS: Boolean? = null,

    @field:XmlElement(name = "bAllowHordeCompute", namespace = NAMESPACE)
    var allowHordeCompute: Boolean? = null,

    /* General Settings */

    @field:XmlElement(name = "bUsePCHFiles", namespace = NAMESPACE)
    var usePchFiles: Boolean? = null,

    @field:XmlElement(name = "MaxParallelActions", namespace = NAMESPACE)
    @property:UbtIntConfigProp("Max Parallel Actions","Number of actions that can be executed in parallel. If 0 then code will pick a default based on the number of cores and memory available. Applies to the ParallelExecutor, HybridExecutor, and LocalExecutor and has maximum value of 2x the number of logical processors of the machine.")
    var maxParallelActions: Int? = null,

    @field:XmlElement(name = "bAllCores", namespace = NAMESPACE)
    @property:UbtIntConfigProp("Use All Cores","onsider logical cores when determining how many total cpu cores are availablet¶ill pick a default based on the number of cores and memory available. Applies to the ParallelExecutor, HybridExecutor, and LocalExecutor and has maximum value of 2x the number of logical processors of the machine.")
    var allCores: Int? = null,

    @field:XmlElement(name = "bWarningsAsErrors", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Warnings as Errors",  "Treats compiler warnings as errors if enabled.")
    var warningsAsErrors: Boolean? = null,

    @field:XmlElement(name = "bIgnoreInvalidFiles", namespace = NAMESPACE)
    @property:UbtIntConfigProp("Ignore Invalid Files", "When single file targets are specified, via -File=, -SingleFile=, or -FileList=. If this option is set, no error will be produced if the source file is not included in the target. Additionally, if any file or file list is specified, the target will not be built if none of the specified files are part of that target, including the case where a file specified via -FileList= is empty.")
    var ignoreInvalidFiles: Boolean? = null,

    @field:XmlElement(name = "bCompactOutputCommandLine", namespace = NAMESPACE)
    @property:UbtIntConfigProp("Compact Output Command-line", " Instruct the executor to write compact output e.g. only errors, if supported by the executor. This field is used to hold the value when specified from the command line or XML")
    var compactOutputCommandLine: Boolean? = null,

    @field:XmlElement(name = "bPrintDebugInfo", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp(" Print Debug Info",  "Whether debug info should be written to the console.")
    var printDebugInfo: Boolean? = null,

    /* Sanitizers */

    @field:XmlElement(name = "bEnableAddressSanitizer", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Enable Address Sanitizer",  "Enables address sanitizer (ASan).")
    var enableAddressSanitizer: Boolean? = null,

    @field:XmlElement(name = "bEnableThreadSanitizer", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Enable Thread Sanitizer",  "Enables address sanitizer (TSan).")
    var enableThreadSanitizer: Boolean? = null,

    @field:XmlElement(name = "bEnableUndefinedBehaviorSanitizer", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Enable Undefined Behavior Sanitizer",  "Enables address sanitizer (UBSan).")
    var enableUndefinedBehaviorSanitizer: Boolean? = null,

    /* Unity builds */

    @field:XmlElement(name = "bUseUnityBuild", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Use Unity Build",  "Whether to unify C++ code into larger files for faster compilation.", true)
    var useUnityBuild: Boolean? = null,

    @field:XmlElement(name = "bForceUnityBuild", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Force Unity Build",  "Whether to force C++ source files to be combined into larger files for faster compilation.")
    var forceUnityBuild: Boolean? = null,

    @field:XmlElement(name = "bUseAdaptiveUnityBuild", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Use Adaptive Unity Build",  "Use a heuristic to determine which files are currently being iterated on and exclude them from unity blobs, result in faster incremental compile times. The current implementation uses the read-only flag to distinguish the working set, assuming that files will be made writable by the source control system if they are being modified. This is true for Perforce, but not for Git.", true)
    var useAdaptiveUnityBuild: Boolean? = null
)
