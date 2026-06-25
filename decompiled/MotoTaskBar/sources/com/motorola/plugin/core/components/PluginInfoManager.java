package com.motorola.plugin.core.components;

import com.motorola.plugin.core.provider.PluginInfoProvider;

/* JADX INFO: compiled from: PluginInfoManager.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginInfoManager extends PluginInfoProvider {

    /* JADX INFO: compiled from: PluginInfoManager.kt */
    public final class DefaultImpls {
        public static void onLowMemory(PluginInfoManager pluginInfoManager) {
            pluginInfoManager.getClass();
            PluginInfoProvider.DefaultImpls.onLowMemory(pluginInfoManager);
        }

        public static void onTrimMemory(PluginInfoManager pluginInfoManager, int i) {
            pluginInfoManager.getClass();
            PluginInfoProvider.DefaultImpls.onTrimMemory(pluginInfoManager, i);
        }
    }
}
