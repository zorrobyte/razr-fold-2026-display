package com.motorola.taskbar;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.dagger.SystemUIRootComponent;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.glass.GlassesMonitor;
import com.motorola.taskbar.TaskBarAppComponentFactory;
import com.motorola.taskbar.bar.TaskBarController;
import com.motorola.taskbar.settings.di.SettingsComponent;
import com.motorola.taskbar.settings.di.SettingsComponentFactory;
import com.motorola.taskbar.settings.di.SettingsComponentFactoryProvider;
import com.motorola.taskbar.settings.utils.SettingsUtils;
import com.motorola.taskbar.shortcut.ShortcutKeyDispatcher;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.NotificationChannelsIds;
import com.motorola.trackpad.ReadyForProxy;

/* JADX INFO: loaded from: classes2.dex */
public class TaskBarApplication extends Application implements TaskBarAppComponentFactory.ContextInitializer, SettingsComponentFactoryProvider {
    private ConfigurationController mConfigurationController;
    private TaskBarAppComponentFactory.ContextAvailableCallback mContextAvailableCallback;
    private GlassesMonitor mGlassesMonitor;
    private SystemUIRootComponent mRootComponent;
    private TaskBarController mTaskBarController;

    @Override // android.app.Application, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        ConfigurationController configurationController = this.mConfigurationController;
        if (configurationController != null) {
            configurationController.onConfigurationChanged(configuration);
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        if (DebugConfig.DEBUG_APP_INITIATE) {
            Log.d("TaskBarApplication", "onCreate");
        }
        this.mContextAvailableCallback.onContextAvailable(this);
        this.mRootComponent = SystemUIFactory.getInstance().getRootComponent();
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            TaskBarController taskBarController = (TaskBarController) Dependency.get(TaskBarController.class);
            this.mTaskBarController = taskBarController;
            taskBarController.start();
            this.mConfigurationController = (ConfigurationController) Dependency.get(ConfigurationController.class);
            ((ShortcutKeyDispatcher) Dependency.get(ShortcutKeyDispatcher.class)).start();
            ((ReadyForProxy) Dependency.get(ReadyForProxy.class)).start();
            GlassesMonitor glassesMonitor = (GlassesMonitor) Dependency.get(GlassesMonitor.class);
            this.mGlassesMonitor = glassesMonitor;
            glassesMonitor.init();
        }
        NotificationChannelsIds.createAll(this);
        SettingsUtils.init(getApplicationContext());
    }

    @Override // com.motorola.taskbar.settings.di.SettingsComponentFactoryProvider
    public SettingsComponent.Factory providerSettingsComponentFactory() {
        return new SettingsComponentFactory();
    }

    @Override // com.motorola.taskbar.TaskBarAppComponentFactory.ContextInitializer
    public void setContextAvailableCallback(TaskBarAppComponentFactory.ContextAvailableCallback contextAvailableCallback) {
        if (DebugConfig.DEBUG_APP_INITIATE) {
            Log.d("TaskBarApplication", "setContextAvailableCallback");
        }
        this.mContextAvailableCallback = contextAvailableCallback;
    }
}
