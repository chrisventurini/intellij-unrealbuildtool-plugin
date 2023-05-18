package com.visionsof.intellijunrealbuildtoolplugin.actions

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.visionsof.intellijunrealbuildtoolplugin.services.UbtConfigService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint
import com.visionsof.intellijunrealbuildtoolplugin.ui.buildUbtQuickMenu
import java.awt.Point


internal class UbtQuickAction ()  : AnAction() {

    override fun actionPerformed(action: AnActionEvent) {

        val unrealBuildToolConfigService = UbtConfigService.getInstance()

        lateinit var menuPopup: JBPopup

        val applyCallback = {

            unrealBuildToolConfigService.save()
            menuPopup.closeOk(null)

            Notifications.Bus.notify(Notification("UnrealBuildTool", "UnrealBuildTool", "Build configuration saved", NotificationType.INFORMATION))
        }

        val cancelCallback = {
            menuPopup.closeOk(null)
        }

        val menuPopupBuilder = JBPopupFactory.getInstance().createComponentPopupBuilder(buildUbtQuickMenu(unrealBuildToolConfigService.config, applyCallback, cancelCallback), null)
            .setFocusable(true)
            .setRequestFocus(true)
        menuPopup = menuPopupBuilder.createPopup()

        val refComp = action.inputEvent.component
        val location = RelativePoint(refComp, Point(0, refComp.height))
        menuPopup.show(location)
    }
}