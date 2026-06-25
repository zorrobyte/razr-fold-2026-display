package com.motorola.plugin.core.provider;

import com.motorola.plugin.core.provider.PluginInfoProvider;

/* JADX INFO: compiled from: SharedLockPluginInfoProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface SharedLockPluginInfoProvider extends PluginInfoProvider {

    /* JADX INFO: compiled from: SharedLockPluginInfoProvider.kt */
    public final class DefaultImpls {
        public static void onLowMemory(SharedLockPluginInfoProvider sharedLockPluginInfoProvider) {
            sharedLockPluginInfoProvider.getClass();
            PluginInfoProvider.DefaultImpls.onLowMemory(sharedLockPluginInfoProvider);
        }

        public static void onTrimMemory(SharedLockPluginInfoProvider sharedLockPluginInfoProvider, int i) {
            sharedLockPluginInfoProvider.getClass();
            PluginInfoProvider.DefaultImpls.onTrimMemory(sharedLockPluginInfoProvider, i);
        }
    }

    void installSharedLock(ISharedLock iSharedLock);
}
