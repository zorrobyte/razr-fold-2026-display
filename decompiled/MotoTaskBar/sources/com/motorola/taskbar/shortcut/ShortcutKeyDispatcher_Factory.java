package com.motorola.taskbar.shortcut;

import android.app.KeyguardManager;
import android.content.Context;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.motorola.taskbar.bar.QSNotificationPanelController;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.recent.RecentsController;
import com.motorola.taskbar.shortcut.record.RecordManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class ShortcutKeyDispatcher_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider controllerProvider;
    private final Provider deviceProvisionedControllerProvider;
    private final Provider keyguardManagerProvider;
    private final Provider recentsControllerProvider;
    private final Provider recordManagerProvider;
    private final Provider taskBarControllerProvider;

    public ShortcutKeyDispatcher_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.controllerProvider = provider2;
        this.taskBarControllerProvider = provider3;
        this.recentsControllerProvider = provider4;
        this.deviceProvisionedControllerProvider = provider5;
        this.keyguardManagerProvider = provider6;
        this.recordManagerProvider = provider7;
    }

    public static ShortcutKeyDispatcher_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new ShortcutKeyDispatcher_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static ShortcutKeyDispatcher newInstance(Context context, QSNotificationPanelController qSNotificationPanelController, TaskBarController taskBarController, RecentsController recentsController, DeviceProvisionedController deviceProvisionedController, KeyguardManager keyguardManager, RecordManager recordManager) {
        return new ShortcutKeyDispatcher(context, qSNotificationPanelController, taskBarController, recentsController, deviceProvisionedController, keyguardManager, recordManager);
    }

    @Override // javax.inject.Provider
    public ShortcutKeyDispatcher get() {
        return newInstance((Context) this.contextProvider.get(), (QSNotificationPanelController) this.controllerProvider.get(), (TaskBarController) this.taskBarControllerProvider.get(), (RecentsController) this.recentsControllerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (KeyguardManager) this.keyguardManagerProvider.get(), (RecordManager) this.recordManagerProvider.get());
    }
}
