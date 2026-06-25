package com.motorola.plugin.core.discovery;

/* JADX INFO: compiled from: LocalAppPluginDiscovery.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class LocalAppPluginDiscoveryRegistry {
    public static final LocalAppPluginDiscoveryRegistry INSTANCE = new LocalAppPluginDiscoveryRegistry();

    private LocalAppPluginDiscoveryRegistry() {
    }

    public final void registerLocalPlugin(Class cls, Class cls2) {
        cls.getClass();
        cls2.getClass();
        LocalAppPluginDiscovery.INSTANCE.registerLocalPlugin(cls, cls2);
    }
}
