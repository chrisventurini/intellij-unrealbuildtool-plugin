package com.visionsof.intellijunrealbuildtoolplugin.model

import javax.xml.bind.annotation.*

@XmlAccessorType(XmlAccessType.FIELD)
data class IncredibuildExecutorConfiguration(

    // General

    @field:XmlElement(name = "bUnavailableIfInUse", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Unavailable If In Use", "Check for a concurrent Incredibuild build and treat the Incredibuild executor as unavailable if it's in use. This will allow UBT to fall back to another executor such as the parallel executor.")
    var unavailableIfInUse: Boolean? = null,

    @field:XmlElement(name = "bUseVCCompilerMode", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Use VC Compiler Mode", "Whether to enable the VCCompiler=true setting. This requires an additional license for VC tools.")
    var useVCCompilerMode: Boolean? = null,

    @field:XmlElement(name = "MinActions", namespace = NAMESPACE)
    @property:UbtIntConfigProp("Minimum Number of Actions")
    var minActions: Int? = null,

    // Network

    @field:XmlElement(name = "bAllowRemoteLinking", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Allow Remote Linking")
    var allowRemoteLinking: Boolean? = null,

    @field:XmlElement(name = "bAllowOverVpn", namespace = NAMESPACE)
    @property:UbtBooleanConfigProp("Allow Over VPN", "When set to false, Incredibuild will not be enabled when running connected to the coordinator over VPN. Configure VPN-assigned subnets via the VpnSubnets parameter.")
    var allowOverVpn: Boolean? = null,

    @field:XmlElement(name = "VpnSubnets", namespace = NAMESPACE)
    @property:UbtStringArrayConfigProp("VPN Subnets", "List of subnets containing IP addresses assigned by VPN", "", "XXX.XXX.XXX.XXX", """^((25[0-5]|(2[0-4]|1\d|[1-9]|)\d)(\.(?!${'$'})|${'$'})){4}${'$'}""")
    var vpnSubnets: Array<String>? = null,

){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IncredibuildExecutorConfiguration

        if (unavailableIfInUse != other.unavailableIfInUse) return false
        if (useVCCompilerMode != other.useVCCompilerMode) return false
        if (minActions != other.minActions) return false
        if (allowRemoteLinking != other.allowRemoteLinking) return false
        if (allowOverVpn != other.allowOverVpn) return false
        if (vpnSubnets != null) {
            if (other.vpnSubnets == null) return false
            if (!vpnSubnets.contentEquals(other.vpnSubnets)) return false
        } else if (other.vpnSubnets != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = unavailableIfInUse?.hashCode() ?: 0
        result = 31 * result + (useVCCompilerMode?.hashCode() ?: 0)
        result = 31 * result + (minActions ?: 0)
        result = 31 * result + (allowRemoteLinking?.hashCode() ?: 0)
        result = 31 * result + (allowOverVpn?.hashCode() ?: 0)
        result = 31 * result + (vpnSubnets?.contentHashCode() ?: 0)
        return result
    }
}