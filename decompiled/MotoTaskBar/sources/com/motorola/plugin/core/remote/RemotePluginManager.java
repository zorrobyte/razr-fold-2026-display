package com.motorola.plugin.core.remote;

import android.content.pm.LauncherApps;
import android.os.Handler;

/* JADX INFO: compiled from: RemotePluginManager.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface RemotePluginManager {
    void registerCallback(LauncherApps.Callback callback, Handler handler);

    void unregisterCallback(LauncherApps.Callback callback);
}
