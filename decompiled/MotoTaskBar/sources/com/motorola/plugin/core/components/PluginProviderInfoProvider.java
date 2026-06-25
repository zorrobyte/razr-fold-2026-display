package com.motorola.plugin.core.components;

import java.util.List;

/* JADX INFO: compiled from: PluginProviderInfoProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginProviderInfoProvider {

    /* JADX INFO: compiled from: PluginProviderInfoProvider.kt */
    public final class DefaultImpls {
        public static /* synthetic */ List getInstalledPluginProviderInfoList$default(PluginProviderInfoProvider pluginProviderInfoProvider, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getInstalledPluginProviderInfoList");
            }
            if ((i & 1) != 0) {
                z = true;
            }
            return pluginProviderInfoProvider.getInstalledPluginProviderInfoList(z);
        }
    }

    List getInstalledPluginProviderInfoList(boolean z);
}
