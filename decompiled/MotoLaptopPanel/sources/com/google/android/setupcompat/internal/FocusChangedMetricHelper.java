package com.google.android.setupcompat.internal;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public abstract class FocusChangedMetricHelper {
    public static final Bundle getExtraBundle(Activity activity, TemplateLayout templateLayout, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("packageName", activity.getComponentName().getPackageName());
        bundle.putString("screenName", activity.getComponentName().getShortClassName());
        bundle.putInt("hash", templateLayout.hashCode());
        bundle.putBoolean("focus", z);
        bundle.putLong("timeInMillis", System.currentTimeMillis());
        return bundle;
    }

    public static final String getScreenName(Activity activity) {
        return activity.getComponentName().toShortString();
    }
}
