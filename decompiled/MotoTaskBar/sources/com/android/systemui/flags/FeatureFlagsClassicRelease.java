package com.android.systemui.flags;

import android.content.res.Resources;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class FeatureFlagsClassicRelease implements FeatureFlagsClassic {
    private final Resources mResources;
    private final SystemPropertiesHelper mSystemProperties;
    private final Map mAllFlags = new HashMap();
    private final Map mBooleanCache = new HashMap();
    private final Map mStringCache = new HashMap();
    private final Map mIntCache = new HashMap();

    public FeatureFlagsClassicRelease(Resources resources, SystemPropertiesHelper systemPropertiesHelper) {
        this.mResources = resources;
        this.mSystemProperties = systemPropertiesHelper;
    }

    private boolean isEnabledInternal(String str, boolean z) {
        if (!this.mBooleanCache.containsKey(str)) {
            this.mBooleanCache.put(str, Boolean.valueOf(z));
        }
        return ((Boolean) this.mBooleanCache.get(str)).booleanValue();
    }

    @Override // android.util.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.android.systemui.flags.FeatureFlags
    public boolean isEnabled(ReleasedFlag releasedFlag) {
        return isEnabledInternal(releasedFlag.getName(), true);
    }

    @Override // com.android.systemui.flags.FeatureFlags
    public boolean isEnabled(ResourceBooleanFlag resourceBooleanFlag) {
        return isEnabledInternal(resourceBooleanFlag.getName(), this.mResources.getBoolean(resourceBooleanFlag.getResourceId()));
    }

    @Override // com.android.systemui.flags.FeatureFlags
    public boolean isEnabled(UnreleasedFlag unreleasedFlag) {
        return false;
    }
}
