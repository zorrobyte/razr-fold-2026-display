package com.android.systemui.plugins;

import android.content.ComponentName;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes.dex */
public interface PluginLifecycleManager {
    ComponentName getComponentName();

    String getPackage();

    Plugin getPlugin();

    default boolean isLoaded() {
        return getPlugin() != null;
    }

    void loadPlugin();

    void setLogFunc(BiConsumer biConsumer);

    void unloadPlugin();
}
