package com.android.systemui.displays;

import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public interface ActivityStarter {

    public interface OnDismissAction {
        boolean onDismiss();
    }

    void dismissKeyguardThenExecute(OnDismissAction onDismissAction, Runnable runnable, boolean z);

    void postStartActivityDismissingKeyguard(PendingIntent pendingIntent);

    void postStartActivityDismissingKeyguard(Intent intent, int i);

    void startActivity(Intent intent, boolean z);

    void startActivity(Intent intent, boolean z, boolean z2, int i);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view);
}
