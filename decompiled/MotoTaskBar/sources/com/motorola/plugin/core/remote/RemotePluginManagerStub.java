package com.motorola.plugin.core.remote;

import android.content.pm.LauncherApps;
import android.os.Handler;

/* JADX INFO: compiled from: RemotePluginManager.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RemotePluginManagerStub implements RemotePluginManager {
    @Override // com.motorola.plugin.core.remote.RemotePluginManager
    public void registerCallback(LauncherApps.Callback callback, Handler handler) {
        callback.getClass();
        handler.getClass();
    }

    @Override // com.motorola.plugin.core.remote.RemotePluginManager
    public void unregisterCallback(LauncherApps.Callback callback) {
        callback.getClass();
    }
}
