package com.motorola.plugin.core.components;

import android.content.Context;

/* JADX INFO: compiled from: PluginManagerComponents.kt */
/* JADX INFO: loaded from: classes2.dex */
@PluginScope
public interface PluginManagerComponents {

    /* JADX INFO: compiled from: PluginManagerComponents.kt */
    public interface Factory {
        PluginManagerComponents create(Context context);
    }

    PluginManager mockManager();

    PluginManager newPluginManager();
}
