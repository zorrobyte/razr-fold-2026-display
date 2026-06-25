package com.motorola.plugin.core.components;

import android.content.ComponentName;
import com.motorola.plugin.core.components.ConfigurationListenerChainedDispatcher;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.core.misc.MarkFlag;

/* JADX INFO: compiled from: PluginSubscriber.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginSubscriber extends PluginSubscriberAbility, ConfigurationListenerChainedDispatcher.ConfigurationChainedListener, Disposable, ISnapshotAware {

    /* JADX INFO: compiled from: PluginSubscriber.kt */
    public final class DefaultImpls {
        public static void onLowMemory(PluginSubscriber pluginSubscriber) {
            pluginSubscriber.getClass();
            ConfigurationListenerChainedDispatcher.ConfigurationChainedListener.DefaultImpls.onLowMemory(pluginSubscriber);
        }

        public static void onTrimMemory(PluginSubscriber pluginSubscriber, int i) {
            pluginSubscriber.getClass();
            ConfigurationListenerChainedDispatcher.ConfigurationChainedListener.DefaultImpls.onTrimMemory(pluginSubscriber, i);
        }
    }

    void initialize();

    void onPluginPackageChanged(PluginPackage pluginPackage, ComponentName componentName, MarkFlag markFlag);

    void onPluginPackageRemoved(PluginPackage pluginPackage, ComponentName componentName);

    boolean willDisableAllPlugins();

    boolean willDisableAnyPlugin(String str);
}
