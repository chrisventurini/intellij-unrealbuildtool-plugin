package com.visionsof.intellijunrealbuildtoolplugin.model

import javax.xml.bind.annotation.*

private const val READ_WRITE_VAL_INTERNAL = "ReadWrite"
private const val READ_ONLY_VAL_INTERNAL = "ReadOnly"
private const val WRITE_ONLY_VAL_INTERNAL = "WriteOnly"

@XmlEnum
enum class FastbuildCacheModes(val value: String) {

    @XmlEnumValue(READ_WRITE_VAL_INTERNAL)
    READ_WRITE(READ_WRITE_VAL_INTERNAL),

    @XmlEnumValue(READ_ONLY_VAL_INTERNAL)
    READ_ONLY(READ_ONLY_VAL_INTERNAL),

    @XmlEnumValue(WRITE_ONLY_VAL_INTERNAL)
    WRITE_ONLY(WRITE_ONLY_VAL_INTERNAL);

    // To be able to use the values on Annotations
    companion object  {
        const val READ_WRITE_VAL = READ_WRITE_VAL_INTERNAL
        const val READ_ONLY_VAL = READ_ONLY_VAL_INTERNAL
        const val WRITE_ONLY_VALE = WRITE_ONLY_VAL_INTERNAL
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
data class FastBuildExecutorConfiguration(

    // General

    @field:XmlElement(name = "FBuildExecutablePath", namespace = NAMESPACE)
    @property:UbtFileConfigProp("FASTBuild Executable Path",  "Used to specify the location of fbuild.exe if the distributed binary isn't being used.", UbtConfigFilePropType.EXECUTABLE_VAL)
    var executablePath: String? = null,

    @field:XmlElement(name = "bStopOnError", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Stop On Error", "Whether to stop the build process on error.")
    var stopOnError: Boolean? = null,

    @field:XmlElement(name = "MsvcCRTRedistVersion", namespace = NAMESPACE)
    @property:UbtFileConfigProp("C Runtime Distributable Path",  "Used to specify the location of the C Runtime redistributable used for FASTBuild. If empty or not found, a preferred path is used.", UbtConfigFilePropType.DIRECTORY_VAL)
    var msvcCRTRedistVersion: String? = null,

    // Distribution

    @field:XmlElement(name = "bEnableDistribution", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Enable Network Build Distribution", "", true)
    var enableDistribution: Boolean? = null,

    @field:XmlElement(name = "bForceRemote", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Force Remote Distribution Build")
    var bForceRemote: Boolean? = null,

    @field:XmlElement(name = "FBuildBrokeragePath", namespace = NAMESPACE)
    @property:UbtFileConfigProp("FASTBuild Brokerage Path",  "Used to specify the location of the brokerage. If null, FASTBuild will fall back to checking FASTBUILD_BROKERAGE_PATH environment variable.", UbtConfigFilePropType.DIRECTORY_VAL)
    var brokeragePath: String? = null,

    // Caching

    @field:XmlElement(name = "bEnableCaching", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Enable Caching",  "Controls whether to use caching at all. CachePath and FASTCacheMode are only relevant if this is enabled.")
    var enableCaching: Boolean? = null,

    @field:XmlElement(name = "Cache Path", namespace = NAMESPACE)
    @property:UbtFileConfigProp("FASTBuild Brokerage Path",  "Used to specify the location of the cache. If empty, FASTBuild will fall back to checking the FASTBUILD_CACHE_PATH environment variable.", UbtConfigFilePropType.DIRECTORY_VAL)
    var buildCachePath: String? = null,

    @field:XmlElement(name = "CacheMode", namespace = NAMESPACE)
    @property:UbtStringEnumConfigProp("Cache Access", "", FastbuildCacheModes.READ_WRITE_VAL)
    var cacheMode: FastbuildCacheModes? = null,
)