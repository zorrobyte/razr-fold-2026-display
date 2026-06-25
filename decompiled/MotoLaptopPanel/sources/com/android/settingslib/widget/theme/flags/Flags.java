package com.android.settingslib.widget.theme.flags;

/* JADX INFO: loaded from: classes.dex */
public abstract class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();

    public static boolean isExpressiveDesignEnabled() {
        return FEATURE_FLAGS.isExpressiveDesignEnabled();
    }
}
