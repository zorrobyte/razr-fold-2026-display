package com.motorola.plugin.core.components;

import android.content.ComponentName;
import com.motorola.plugin.core.context.PluginId;
import com.motorola.plugin.core.misc.ISnapshotAware;
import kotlin.Deprecated;

/* JADX INFO: compiled from: PluginWhitelistPolicy.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginWhitelistPolicy extends ISnapshotAware {

    /* JADX INFO: compiled from: PluginWhitelistPolicy.kt */
    public final class DefaultImpls {
        @Deprecated
        public static void installWhitelistedPlugins(PluginWhitelistPolicy pluginWhitelistPolicy, String[] strArr) {
            pluginWhitelistPolicy.getClass();
            strArr.getClass();
        }
    }

    boolean getEnableAllPluginsIfDebugBuild();

    @Deprecated
    void installWhitelistedPlugins(String[] strArr);

    boolean isPluginWhitelisted(ComponentName componentName);

    boolean isPluginWhitelisted(PluginId pluginId);

    void setEnableAllPluginsIfDebugBuild(boolean z);
}
