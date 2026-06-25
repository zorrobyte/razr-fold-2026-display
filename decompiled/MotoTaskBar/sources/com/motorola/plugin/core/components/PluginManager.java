package com.motorola.plugin.core.components;

import android.content.Context;
import com.motorola.plugin.core.PluginListener;
import com.motorola.plugin.core.extension.ConfigurationController;
import com.motorola.plugin.core.extension.ExtensionController;
import com.motorola.plugin.core.misc.Disposable;
import com.motorola.plugin.core.misc.Dumpable;
import com.motorola.plugin.core.misc.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: compiled from: PluginManager.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginManager extends PluginSubscriberAbility, Disposable, Dumpable {
    public static final Factory Factory = Factory.$$INSTANCE;

    /* JADX INFO: compiled from: PluginManager.kt */
    public final class DefaultImpls {
        public static void addPluginListener(PluginManager pluginManager, PluginListener pluginListener) {
            pluginManager.getClass();
            pluginListener.getClass();
            pluginManager.addPluginListener(com.motorola.plugin.sdk.Plugin.class, pluginListener);
        }

        public static void dump(PluginManager pluginManager, String str, FileDescriptor fileDescriptor, PrintWriter printWriter) {
            pluginManager.getClass();
            str.getClass();
            printWriter.getClass();
            pluginManager.dump(new IndentingPrintWriter(printWriter, null, 0, 6, null));
        }

        public static void removePluginListener(PluginManager pluginManager, PluginListener pluginListener) {
            pluginManager.getClass();
            pluginListener.getClass();
            pluginManager.removePluginListener(com.motorola.plugin.sdk.Plugin.class, pluginListener);
        }
    }

    /* JADX INFO: compiled from: PluginManager.kt */
    public final class Factory {
        static final /* synthetic */ Factory $$INSTANCE = new Factory();

        private Factory() {
        }

        private final PluginManagerComponents newComponents(Context context) {
            return DaggerPluginManagerComponents.factory().create(context);
        }

        public final PluginManager create(Context context) {
            context.getClass();
            return newComponents(context).newPluginManager();
        }

        public final PluginManager mock(Context context) {
            context.getClass();
            return newComponents(context).mockManager();
        }
    }

    static PluginManager create(Context context) {
        return Factory.create(context);
    }

    static PluginManager mock(Context context) {
        return Factory.mock(context);
    }

    void addPluginListener(PluginListener pluginListener);

    void addPluginListener(Class cls, PluginListener pluginListener);

    void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter);

    ExtensionController getExtensionController();

    ConfigurationController getPluginConfigurationController();

    PluginProviderInfoProvider getPluginProviderInfoProvider();

    PluginWhitelistPolicy getPluginWhitelistPolicy();

    void initialize();

    boolean isInitialized();

    void removePluginListener(PluginListener pluginListener);

    void removePluginListener(Class cls, PluginListener pluginListener);
}
