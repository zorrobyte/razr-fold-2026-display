package com.android.systemui.statusbar.notification.collection;

/* JADX INFO: compiled from: NotifPipelineChoreographer.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotifPipelineChoreographer {
    void addOnEvalListener(Runnable runnable);

    void schedule();
}
