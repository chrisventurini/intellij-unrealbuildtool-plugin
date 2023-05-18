package com.visionsof.intellijunrealbuildtoolplugin.model

import javax.xml.bind.annotation.*

const val NAMESPACE = "https://www.unrealengine.com/BuildConfiguration"

@XmlRootElement(name="Configuration", namespace = NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
data class UbtConfiguration (

    @field:XmlElement(name="BuildConfiguration", namespace = NAMESPACE)
    var buildConfiguration:BuildConfiguration? = null,

    @field:XmlElement(name="SourceFileWorkingSet", namespace = NAMESPACE)
    var sourceFileWorkingSet:SourceFileWorkingSetConfiguration? = null,

    @field:XmlElement(name="FASTBuild", namespace = NAMESPACE)
    var fastBuildConfiguration:FastBuildExecutorConfiguration? = null,

    @field:XmlElement(name="ParallelExecutor", namespace = NAMESPACE)
    var parallelExecutor:ParallelExecutorConfiguration? = null,

    @field:XmlElement(name="XGE", namespace = NAMESPACE)
    var incredibuildExecutorConfiguration: IncredibuildExecutorConfiguration? = null,

    @field:XmlElement(name="WindowsPlatform", namespace = NAMESPACE)
    var windowsPlatform: WindowsPlatformConfiguration? = null
)



