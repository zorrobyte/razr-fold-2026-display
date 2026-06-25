package com.motorola.plugin.core.container;

import android.content.res.Configuration;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.discovery.DiscoverInfo;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.ISnapshotAware;
import com.motorola.plugin.sdk.Plugin;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: PluginInstanceContainer.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginInstanceContainer extends ConfigurationController.ConfigurationListener, Disposable, ISnapshotAware {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int DISCONNECT_REASON_CANCELED = 7;
    public static final int DISCONNECT_REASON_CRASH = 1;
    public static final int DISCONNECT_REASON_DISABLE = 0;
    public static final int DISCONNECT_REASON_DISPOSE = 4;
    public static final int DISCONNECT_REASON_EMPTY_CACHE = 6;
    public static final int DISCONNECT_REASON_MANUALLY = 5;
    public static final int DISCONNECT_REASON_PKG_REMOVE = 2;
    public static final int DISCONNECT_REASON_PKG_UPDATE = 3;

    /* JADX INFO: compiled from: PluginInstanceContainer.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final long AUTO_RELEASE_TIMEOUT_DEFAULT;
        private static final long AUTO_RELEASE_TIMEOUT_FOR_LOW_RAM;
        private static final long AUTO_RELEASE_TIMEOUT_FOR_RESTRICT;
        public static final int DISCONNECT_REASON_CANCELED = 7;
        public static final int DISCONNECT_REASON_CRASH = 1;
        public static final int DISCONNECT_REASON_DISABLE = 0;
        public static final int DISCONNECT_REASON_DISPOSE = 4;
        public static final int DISCONNECT_REASON_EMPTY_CACHE = 6;
        public static final int DISCONNECT_REASON_MANUALLY = 5;
        public static final int DISCONNECT_REASON_PKG_REMOVE = 2;
        public static final int DISCONNECT_REASON_PKG_UPDATE = 3;

        static {
            TimeUnit timeUnit = TimeUnit.MINUTES;
            AUTO_RELEASE_TIMEOUT_DEFAULT = timeUnit.toMillis(30L);
            AUTO_RELEASE_TIMEOUT_FOR_LOW_RAM = timeUnit.toMillis(10L);
            AUTO_RELEASE_TIMEOUT_FOR_RESTRICT = TimeUnit.SECONDS.toMillis(30L);
        }

        private Companion() {
        }

        public final String disconnectReason2String$core_stubRelease(int i) {
            switch (i) {
                case 0:
                    return "disable";
                case 1:
                    return "crash";
                case 2:
                    return "package removed";
                case 3:
                    return "package updated";
                case 4:
                    return "dispose";
                case 5:
                    return "manually";
                case 6:
                    return "timeout release";
                case 7:
                    return "canceled";
                default:
                    return "unknown";
            }
        }

        public final long getAUTO_RELEASE_TIMEOUT_DEFAULT$core_stubRelease() {
            return AUTO_RELEASE_TIMEOUT_DEFAULT;
        }

        public final long getAUTO_RELEASE_TIMEOUT_FOR_LOW_RAM$core_stubRelease() {
            return AUTO_RELEASE_TIMEOUT_FOR_LOW_RAM;
        }

        public final long getAUTO_RELEASE_TIMEOUT_FOR_RESTRICT$core_stubRelease() {
            return AUTO_RELEASE_TIMEOUT_FOR_RESTRICT;
        }
    }

    /* JADX INFO: compiled from: PluginInstanceContainer.kt */
    public interface DeathRecipient {
        void onPluginInstanceContainerDied(PluginPackage pluginPackage, int i);
    }

    /* JADX INFO: compiled from: PluginInstanceContainer.kt */
    public final class DefaultImpls {
        public static void onConfigChanged(PluginInstanceContainer pluginInstanceContainer, Configuration configuration, BitFlag bitFlag) {
            pluginInstanceContainer.getClass();
            configuration.getClass();
            bitFlag.getClass();
            ConfigurationController.ConfigurationListener.DefaultImpls.onConfigChanged(pluginInstanceContainer, configuration, bitFlag);
        }

        public static void onLowMemory(PluginInstanceContainer pluginInstanceContainer) {
            pluginInstanceContainer.getClass();
            ConfigurationController.ConfigurationListener.DefaultImpls.onLowMemory(pluginInstanceContainer);
        }

        public static void onTrimMemory(PluginInstanceContainer pluginInstanceContainer, int i) {
            pluginInstanceContainer.getClass();
            ConfigurationController.ConfigurationListener.DefaultImpls.onTrimMemory(pluginInstanceContainer, i);
        }
    }

    void attach(PluginPackage pluginPackage);

    boolean checkAndDisable(String str);

    Object createPluginInstance(DiscoverInfo discoverInfo, Class cls, Continuation continuation);

    boolean dependsOn(Plugin plugin, Class cls);

    void destroy(int i);

    void destroyPluginInstance(Class cls, int i);

    boolean disableAll();

    Map getAvailablePlugins();

    Plugin getPlugin(Class cls);

    void keepChannelConnectionAlive(boolean z);

    void linkToDeath(DeathRecipient deathRecipient);

    void onPluginPackageChanged(PluginPackage pluginPackage);

    void unlinkToDeath(DeathRecipient deathRecipient);
}
