package com.motorola.taskbar.bar;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.model.NavIconController;
import com.motorola.taskbar.qsnotification.QsNotificationComponentStarter;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class TaskBarController_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider deviceProvisionedControllerProvider;
    private final Provider handlerProvider;
    private final Provider navIconControllerProvider;
    private final Provider qsNotificationComponentStarterProvider;
    private final Provider qsNotificationPanelControllerProvider;
    private final Provider taskBarServiceProxyProvider;
    private final Provider tutorialManagerProvider;

    public TaskBarController_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        this.contextProvider = provider;
        this.handlerProvider = provider2;
        this.taskBarServiceProxyProvider = provider3;
        this.qsNotificationPanelControllerProvider = provider4;
        this.deviceProvisionedControllerProvider = provider5;
        this.tutorialManagerProvider = provider6;
        this.qsNotificationComponentStarterProvider = provider7;
        this.navIconControllerProvider = provider8;
    }

    public static TaskBarController_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8) {
        return new TaskBarController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static TaskBarController newInstance(Context context, Handler handler, TaskBarServiceProxy taskBarServiceProxy, QSNotificationPanelController qSNotificationPanelController, DeviceProvisionedController deviceProvisionedController, ExternalDisplayTutorialManager externalDisplayTutorialManager, QsNotificationComponentStarter qsNotificationComponentStarter, NavIconController navIconController) {
        return new TaskBarController(context, handler, taskBarServiceProxy, qSNotificationPanelController, deviceProvisionedController, externalDisplayTutorialManager, qsNotificationComponentStarter, navIconController);
    }

    @Override // javax.inject.Provider
    public TaskBarController get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.handlerProvider.get(), (TaskBarServiceProxy) this.taskBarServiceProxyProvider.get(), (QSNotificationPanelController) this.qsNotificationPanelControllerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (ExternalDisplayTutorialManager) this.tutorialManagerProvider.get(), (QsNotificationComponentStarter) this.qsNotificationComponentStarterProvider.get(), (NavIconController) this.navIconControllerProvider.get());
    }
}
