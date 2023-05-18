package com.visionsof.intellijunrealbuildtoolplugin.services

import com.intellij.openapi.application.ApplicationManager
import com.visionsof.intellijunrealbuildtoolplugin.model.UbtConfiguration
import java.io.File
import javax.xml.bind.*

internal class UbtConfigService() {
    companion object
    {
        @JvmStatic
        fun getInstance(): UbtConfigService = ApplicationManager.getApplication().getService(UbtConfigService::class.java)

        val documentsConfigDirectory = System.getenv("userprofile") + "\\Documents\\Unreal Engine\\UnrealBuildTool"
        val documentsConfigFile = "${documentsConfigDirectory}\\BuildConfiguration.xml"
    }
    var config: UbtConfiguration

    private var marshaller: Marshaller
    private var docsConfigFile: File

    init {
        val context = JAXBContext.newInstance(UbtConfiguration::class.java)
        marshaller = context.createMarshaller()
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        docsConfigFile = File(documentsConfigFile)

        if(!docsConfigFile.exists())
        {
            File(documentsConfigDirectory).mkdirs()
            config = UbtConfiguration()
            save()
        }
        else
        {
            val unmarshaller = context.createUnmarshaller()
            config = unmarshaller.unmarshal(docsConfigFile) as UbtConfiguration
        }
    }

    fun save(){
        marshaller.marshal(config, docsConfigFile)
    }
}