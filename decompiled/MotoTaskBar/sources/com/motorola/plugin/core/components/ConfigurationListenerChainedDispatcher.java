package com.motorola.plugin.core.components;

import android.content.res.Configuration;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;

/* JADX INFO: compiled from: ConfigurationListenerChainedDispatcher.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ConfigurationListenerChainedDispatcher extends ConfigurationController.ConfigurationListener, ISnapshotAware, Disposable {

    /* JADX INFO: compiled from: ConfigurationListenerChainedDispatcher.kt */
    public interface ChainedDispatcher {
        void processNextConfigChanged(Configuration configuration, BitFlag bitFlag);
    }

    /* JADX INFO: compiled from: ConfigurationListenerChainedDispatcher.kt */
    public interface ConfigurationChainedListener {

        /* JADX INFO: compiled from: ConfigurationListenerChainedDispatcher.kt */
        public final class DefaultImpls {
            public static void onLowMemory(ConfigurationChainedListener configurationChainedListener) {
                configurationChainedListener.getClass();
            }

            public static void onTrimMemory(ConfigurationChainedListener configurationChainedListener, int i) {
                configurationChainedListener.getClass();
            }
        }

        void onConfigChanged(Configuration configuration, BitFlag bitFlag, ChainedDispatcher chainedDispatcher);

        void onLowMemory();

        void onTrimMemory(int i);
    }

    /* JADX INFO: compiled from: ConfigurationListenerChainedDispatcher.kt */
    public final class DefaultImpls {
        public static void onConfigChanged(ConfigurationListenerChainedDispatcher configurationListenerChainedDispatcher, Configuration configuration, BitFlag bitFlag) {
            configurationListenerChainedDispatcher.getClass();
            configuration.getClass();
            bitFlag.getClass();
            ConfigurationController.ConfigurationListener.DefaultImpls.onConfigChanged(configurationListenerChainedDispatcher, configuration, bitFlag);
        }

        public static void onLowMemory(ConfigurationListenerChainedDispatcher configurationListenerChainedDispatcher) {
            configurationListenerChainedDispatcher.getClass();
            ConfigurationController.ConfigurationListener.DefaultImpls.onLowMemory(configurationListenerChainedDispatcher);
        }

        public static void onTrimMemory(ConfigurationListenerChainedDispatcher configurationListenerChainedDispatcher, int i) {
            configurationListenerChainedDispatcher.getClass();
            ConfigurationController.ConfigurationListener.DefaultImpls.onTrimMemory(configurationListenerChainedDispatcher, i);
        }
    }

    void addChainedListener(ConfigurationChainedListener... configurationChainedListenerArr);

    void removeChainedListener(ConfigurationChainedListener... configurationChainedListenerArr);
}
