package com.visionsof.intellijunrealbuildtoolplugin.model

import javax.xml.bind.annotation.*

private const val DEFAULT_VAL_INTERNAL = "Default"
private const val CLANG_VAL_INTERNAL = "Clang"
private const val INTEL_VAL_INTERNAL = "Intel"
private const val VS_NINETEEN_VAL_INTERNAL = "VisualStudio2019"
private const val VS_TWENTY_TWO_VAL_INTERNAL = "VisualStudio2022"

@XmlEnum
enum class WindowsCompiler(val value: String) {

    @XmlEnumValue(DEFAULT_VAL_INTERNAL)
    DEFAULT(DEFAULT_VAL_INTERNAL),

    @XmlEnumValue(CLANG_VAL_INTERNAL)
    CLANG(CLANG_VAL_INTERNAL),

    @XmlEnumValue(INTEL_VAL_INTERNAL)
    INTEL(INTEL_VAL_INTERNAL),

    @XmlEnumValue(VS_NINETEEN_VAL_INTERNAL)
    VS_NINETEEN(VS_NINETEEN_VAL_INTERNAL),

    @XmlEnumValue(VS_TWENTY_TWO_VAL_INTERNAL)
    VS_TWENTY_TWO(VS_TWENTY_TWO_VAL_INTERNAL);

    // To be able to use the values on Annotations
    companion object  {
        const val DEFAULT_VAL = DEFAULT_VAL_INTERNAL
        const val CLANG_VAL = CLANG_VAL_INTERNAL
        const val INTEL_VAL = INTEL_VAL_INTERNAL
        const val VS_2019 = VS_NINETEEN_VAL_INTERNAL
        const val VS_2022 = VS_TWENTY_TWO_VAL_INTERNAL
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
data class WindowsPlatformConfiguration (

    // Compilation

    @field:XmlElement(name = "Compiler", namespace = NAMESPACE)
    @property:UbtStringEnumConfigProp("Compiler", "", WindowsCompiler.DEFAULT_VAL)
    var compiler: WindowsCompiler? = null,

    @field:XmlElement(name = "CompilerVersion", namespace = NAMESPACE)
    @property:UbtStringConfigProp("Compiler Version", """The specific toolchain version to use. This may be a specific version number (for example, "14.13.26128"), the string "Latest" to select the newest available version, or the string "Preview" to select the newest available preview version. By default, and if it is available, the toolchain version used is indicated by WindowsPlatform.DefaultToolChainVersion (otherwise, the latest version is used).""", "Latest")
    var compilerVersion: String? = null,

    @field:XmlElement(name = "WindowsSdkVersion", namespace = NAMESPACE)
    @property:UbtStringConfigProp("Windows SDK Version", """The specific Windows SDK version to use. This may be a specific version number (for example, "8.1", "10.0" or "10.0.10150.0"), or the string "Latest", to select the newest available version. By default, and if it is available, the Windows SDK version use will be indicated by WindowsPlatform.DefaultWindowsSdkVersion (otherwise, the latest version is used).""", "Latest")
    var windowsSdkVersion: String? = null,

    @field:XmlElement(name = "bStrictConformanceMode", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Strict Conformance Mode", "Enables strict standard conformance mode (/permissive-) in Visual Studio 2017 or newer.")
    var strictConformanceMode: Boolean? = null,

    @field:XmlElement(name = "bCompilerTrace", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Compiler Trace", "Outputs compile timing information so that it can be analyzed.")
    var compilerTrace: Boolean? = null,

    @field:XmlElement(name = "bShowIncludes", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Show Includes", "Print out files that are included by each source file.")
    var showIncludes: Boolean? = null,

    @field:XmlElement(name = "PCHMemoryAllocationFactor", namespace = NAMESPACE)
    @property:UbtIntConfigProp("PCH Memory Allocation Factor", "Determines the amount of memory that the compiler allocates to construct precompiled headers (/Zm)")
    var pchMemoryAllocationFactor: Int? = null,

    // Pathing

    @field:XmlElement(name = "MaxRootPathLength", namespace = NAMESPACE)
    @property:UbtIntConfigProp("Maximum Root Path Length", "", 50)
    var maxRootPathLength: Int? = null,

    @field:XmlElement(name = "MaxNestedPathLength", namespace = NAMESPACE)
    @property:UbtIntConfigProp("Maximum Nested Path Length", "Maximum length of a path relative to the root directory. Used on Windows to ensure paths are portable between machines.", 200)
    var maxNestedPathLength: Int? = null

)