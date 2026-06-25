package com.motorola.plugin.core.components;

import android.content.ComponentName;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: compiled from: PluginEnabler.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface PluginEnabler {
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int DISABLED_FROM_EXPLICIT_CRASH = 3;
    public static final int DISABLED_FROM_SYSTEM_CRASH = 4;
    public static final int DISABLED_INVALID_VERSION = 2;
    public static final int DISABLED_MANUALLY = 1;
    public static final int ENABLED = 0;

    /* JADX INFO: compiled from: PluginEnabler.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int DISABLED_FROM_EXPLICIT_CRASH = 3;
        public static final int DISABLED_FROM_SYSTEM_CRASH = 4;
        public static final int DISABLED_INVALID_VERSION = 2;
        public static final int DISABLED_MANUALLY = 1;
        public static final int ENABLED = 0;

        private Companion() {
        }

        public final String disableReasonToString(@DisableReason int i) {
            return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "???" : "Disabled(System Crash)" : "Disabled(Plugin Crash)" : "Disabled(Invalid Version)" : "Disabled(Manually)" : "Enabled";
        }
    }

    /* JADX INFO: compiled from: PluginEnabler.kt */
    @Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DisableReason {
    }

    @DisableReason
    int getDisableReason(ComponentName componentName);

    boolean isEnabled(ComponentName componentName);

    void setDisabled(ComponentName componentName, @DisableReason int i);

    void setEnabled(ComponentName componentName);
}
