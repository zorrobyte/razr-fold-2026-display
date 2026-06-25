package com.android.systemui.plugins;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface PluginListener {
    default boolean onPluginAttached(PluginLifecycleManager pluginLifecycleManager) {
        return true;
    }

    @Deprecated
    default void onPluginConnected(Plugin plugin, Context context) {
    }

    default void onPluginDetached(PluginLifecycleManager pluginLifecycleManager) {
    }

    @Deprecated
    default void onPluginDisconnected(Plugin plugin) {
    }

    default void onPluginLoaded(Plugin plugin, Context context, PluginLifecycleManager pluginLifecycleManager) {
        onPluginConnected(plugin, context);
    }

    default void onPluginUnloaded(Plugin plugin, PluginLifecycleManager pluginLifecycleManager) {
        onPluginDisconnected(plugin);
    }
}
