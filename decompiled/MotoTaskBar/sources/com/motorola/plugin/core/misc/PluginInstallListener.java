package com.motorola.plugin.core.misc;

import com.motorola.plugin.core.context.PluginId;

/* JADX INFO: compiled from: InstallListener.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginInstallListener {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int RESULT_CODE_INVALID_PLUGIN_FILE = -2;
    public static final int RESULT_CODE_NO_SUCH_FILE = -1;
    public static final int RESULT_CODE_OK = 0;

    /* JADX INFO: compiled from: InstallListener.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int RESULT_CODE_INVALID_PLUGIN_FILE = -2;
        public static final int RESULT_CODE_NO_SUCH_FILE = -1;
        public static final int RESULT_CODE_OK = 0;

        private Companion() {
        }
    }

    /* JADX INFO: compiled from: InstallListener.kt */
    public final class DefaultImpls {
        public static void onPluginProgress(PluginInstallListener pluginInstallListener, int i, PluginId pluginId) {
            pluginInstallListener.getClass();
            pluginId.getClass();
        }
    }

    void onPluginInstalled(PluginId pluginId, int i);

    void onPluginProgress(int i, PluginId pluginId);
}
