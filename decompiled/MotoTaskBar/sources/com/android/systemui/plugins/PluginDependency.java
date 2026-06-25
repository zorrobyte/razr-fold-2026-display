package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

/* JADX INFO: loaded from: classes.dex */
@ProvidesInterface(version = 1)
public class PluginDependency {
    public static final int VERSION = 1;
    static DependencyProvider sProvider;

    abstract class DependencyProvider {
        DependencyProvider() {
        }

        abstract Object get(Plugin plugin, Class cls);
    }

    public static Object get(Plugin plugin, Class cls) {
        return sProvider.get(plugin, cls);
    }
}
