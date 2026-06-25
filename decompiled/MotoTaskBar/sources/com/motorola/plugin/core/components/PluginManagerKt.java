package com.motorola.plugin.core.components;

import com.motorola.plugin.core.PluginListener;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginManager.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerKt {
    public static final String INTENT_DISABLE_PLUGIN = "com.motorola.plugin.action.DISABLE_PLUGIN";
    public static final String INTENT_PLUGIN_CHANGED = "com.motorola.plugin.action.PLUGIN_CHANGED";

    public static final /* synthetic */ void addPluginListener(PluginManager pluginManager, PluginListener pluginListener) {
        pluginManager.getClass();
        pluginListener.getClass();
        Intrinsics.reifiedOperationMarker(4, "T");
        pluginManager.addPluginListener(com.motorola.plugin.sdk.Plugin.class, pluginListener);
    }

    public static final /* synthetic */ void removePluginListener(PluginManager pluginManager, PluginListener pluginListener) {
        pluginManager.getClass();
        pluginListener.getClass();
        Intrinsics.reifiedOperationMarker(4, "T");
        pluginManager.removePluginListener(com.motorola.plugin.sdk.Plugin.class, pluginListener);
    }
}
