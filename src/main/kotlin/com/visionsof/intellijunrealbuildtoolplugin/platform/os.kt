package com.visionsof.intellijunrealbuildtoolplugin.platform

enum class Os {
    WINDOWS, LINUX, MAC
}

fun getOs(): Os? {
    val os = System.getProperty("os.name").lowercase()
    return when {
        os.contains("win") -> {
            Os.WINDOWS
        }
        os.contains("nix") || os.contains("nux") || os.contains("aix") -> {
            Os.LINUX
        }
        os.contains("mac") -> {
            Os.MAC
        }
        else -> null
    }
}