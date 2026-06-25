package com.motorola.plugin.core.components;

import android.content.ComponentName;
import android.os.Looper;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.MarkFlag;

/* JADX INFO: compiled from: PackageEventMonitor.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PackageEventMonitor extends Disposable {

    /* JADX INFO: compiled from: PackageEventMonitor.kt */
    public interface PackageEventCallback {
        void onDisablePlugin(ComponentName componentName);

        void onPluginPackageChanged(PluginPackage pluginPackage, ComponentName componentName, MarkFlag markFlag);

        void onUserUnlocked();
    }

    void startListening(PackageEventCallback packageEventCallback, Looper looper);

    void stopListening();
}
