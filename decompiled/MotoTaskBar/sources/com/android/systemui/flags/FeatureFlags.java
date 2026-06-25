package com.android.systemui.flags;

import android.util.Dumpable;

/* JADX INFO: compiled from: FeatureFlags.kt */
/* JADX INFO: loaded from: classes.dex */
public interface FeatureFlags extends Dumpable {
    boolean isEnabled(ReleasedFlag releasedFlag);

    boolean isEnabled(ResourceBooleanFlag resourceBooleanFlag);

    boolean isEnabled(UnreleasedFlag unreleasedFlag);
}
