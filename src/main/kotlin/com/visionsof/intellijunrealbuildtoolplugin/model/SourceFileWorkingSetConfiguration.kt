package com.visionsof.intellijunrealbuildtoolplugin.model

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement

@XmlAccessorType(XmlAccessType.FIELD)
data class SourceFileWorkingSetConfiguration (

    @field:XmlElement(name = "Provider", namespace = NAMESPACE)
    var provider: String? = null
)
