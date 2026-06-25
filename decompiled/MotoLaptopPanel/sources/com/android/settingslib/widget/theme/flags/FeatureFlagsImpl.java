package com.android.settingslib.widget.theme.flags;

import android.os.flagging.AconfigPackage;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class FeatureFlagsImpl implements FeatureFlags {
    private static volatile boolean isCached = false;
    private static boolean isExpressiveDesignEnabled = false;

    private void init() {
        try {
            isExpressiveDesignEnabled = AconfigPackage.load("com.android.settingslib.widget.theme.flags").getBooleanFlagValue("is_expressive_design_enabled", false);
        } catch (Exception e) {
            Log.e("FeatureFlagsImplExport", e.toString());
        } catch (LinkageError e2) {
            Log.w("FeatureFlagsImplExport", e2.toString());
        }
        isCached = true;
    }

    @Override // com.android.settingslib.widget.theme.flags.FeatureFlags
    public boolean isExpressiveDesignEnabled() {
        if (!isCached) {
            init();
        }
        return isExpressiveDesignEnabled;
    }
}
