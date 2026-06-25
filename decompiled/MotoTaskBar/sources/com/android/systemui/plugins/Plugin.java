package com.android.systemui.plugins;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface Plugin {
    @Deprecated
    default int getVersion() {
        return -1;
    }

    default void onCreate(Context context, Context context2) {
    }

    default void onDestroy() {
    }
}
