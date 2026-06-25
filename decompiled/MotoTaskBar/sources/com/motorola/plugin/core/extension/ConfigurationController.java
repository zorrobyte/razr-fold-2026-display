package com.motorola.plugin.core.extension;

import android.content.res.Configuration;
import com.motorola.plugin.core.misc.BitFlag;
import com.motorola.plugin.core.misc.Disposable;

/* JADX INFO: compiled from: ConfigurationController.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ConfigurationController extends Disposable {
    public static final int CONFIG_DENSITY_FONT_CHANGED = 2;
    public static final int CONFIG_LAYOUT_DIRECTION_CHANGED = 128;
    public static final int CONFIG_LOCALE_LIST_CHANGED = 64;
    public static final int CONFIG_OVERLAY_CHANGED = 8;
    public static final int CONFIG_SMALLEST_SCREEN_WIDTH_CHANGED = 4;
    public static final int CONFIG_THEME_CHANGED = 32;
    public static final int CONFIG_UI_MODE_CHANGED = 16;
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: ConfigurationController.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int CONFIG_DENSITY_FONT_CHANGED = 2;
        public static final int CONFIG_LAYOUT_DIRECTION_CHANGED = 128;
        public static final int CONFIG_LOCALE_LIST_CHANGED = 64;
        public static final int CONFIG_OVERLAY_CHANGED = 8;
        public static final int CONFIG_SMALLEST_SCREEN_WIDTH_CHANGED = 4;
        public static final int CONFIG_THEME_CHANGED = 32;
        public static final int CONFIG_UI_MODE_CHANGED = 16;

        private Companion() {
        }
    }

    /* JADX INFO: compiled from: ConfigurationController.kt */
    public interface ConfigurationListener {

        /* JADX INFO: compiled from: ConfigurationController.kt */
        public final class DefaultImpls {
            public static void onConfigChanged(ConfigurationListener configurationListener, Configuration configuration, BitFlag bitFlag) {
                configurationListener.getClass();
                configuration.getClass();
                bitFlag.getClass();
            }

            public static void onLowMemory(ConfigurationListener configurationListener) {
                configurationListener.getClass();
            }

            public static void onTrimMemory(ConfigurationListener configurationListener, int i) {
                configurationListener.getClass();
            }
        }

        void onConfigChanged(Configuration configuration, BitFlag bitFlag);

        void onLowMemory();

        void onTrimMemory(int i);
    }

    void addCallback(ConfigurationListener configurationListener);

    boolean isLayoutRtl();

    void notifyThemeChanged();

    void onConfigurationChanged(Configuration configuration);

    void removeCallback(ConfigurationListener configurationListener);
}
