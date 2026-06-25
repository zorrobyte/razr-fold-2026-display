package com.motorola.plugin.core.components;

import android.content.Context;
import com.motorola.plugin.core.PluginListener;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.misc.MarkFlag;

/* JADX INFO: compiled from: PluginListenerDispatcher.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginListenerDispatcher extends ISnapshotAware, Disposable {
    void addPluginListener(Class cls, PluginListener pluginListener);

    void dispatchPluginConnected(String str, String str2, com.motorola.plugin.sdk.Plugin plugin, Context context);

    void dispatchPluginDisconnected(String str, String str2, com.motorola.plugin.sdk.Plugin plugin);

    void dispatchPluginFailedLoad(String str, String str2);

    void dispatchPluginPackageChanged(PluginPackage pluginPackage, MarkFlag markFlag);

    void removePluginListener(Class cls, PluginListener pluginListener);
}
