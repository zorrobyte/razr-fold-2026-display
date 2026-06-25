package com.android.systemui.statusbar.notification;

import com.android.systemui.util.Compile;

/* JADX INFO: compiled from: NotifPipelineFlags.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifPipelineFlags {
    public final boolean isDevLoggingEnabled() {
        return Compile.IS_DEBUG;
    }
}
