package com.motorola.plugin.core.provider;

import android.content.Intent;
import com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher;
import com.motorola.plugin.core.context.PluginContext;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.discovery.PluginInfo;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.misc.MarkFlag;
import java.util.List;

/* JADX INFO: compiled from: PluginInfoProvider.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginInfoProvider extends ConfigurationListenerChainedDispatcher.ConfigurationChainedListener, Disposable, ISnapshotAware {

    /* JADX INFO: compiled from: PluginInfoProvider.kt */
    public final class DefaultImpls {
        public static void onLowMemory(PluginInfoProvider pluginInfoProvider) {
            pluginInfoProvider.getClass();
            ConfigurationListenerChainedDispatcher.ConfigurationChainedListener.DefaultImpls.onLowMemory(pluginInfoProvider);
        }

        public static void onTrimMemory(PluginInfoProvider pluginInfoProvider, int i) {
            pluginInfoProvider.getClass();
            ConfigurationListenerChainedDispatcher.ConfigurationChainedListener.DefaultImpls.onTrimMemory(pluginInfoProvider, i);
        }
    }

    List getAvailableRemotePluginList();

    List getInstalledPluginInfoList();

    PluginContext getPluginContext(PluginInfo pluginInfo);

    boolean launchPlugin(Intent intent, PluginPackage pluginPackage);

    void onPluginPackageChanged(PluginPackage pluginPackage, MarkFlag markFlag);

    List queryPluginInfo(String str, PluginPackage pluginPackage);

    void startGatheringPluginInfoList();
}
