package com.android.systemui.plugins.moto;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public interface ActivityStarter {
    public static final int VERSION = 2;

    public interface Callback {
        void onActivityStarted(int i);
    }

    public interface OnDismissAction {
        boolean onDismiss();
    }

    void dismissKeyguardThenExecute(OnDismissAction onDismissAction, Runnable runnable, boolean z);

    void postQSRunnableDismissingKeyguard(Runnable runnable);

    void postStartActivityDismissingKeyguard(PendingIntent pendingIntent, int i);

    void postStartActivityDismissingKeyguard(Intent intent, int i, int i2);

    void startActivity(Intent intent, ActivityOptions activityOptions);

    void startActivity(Intent intent, ActivityOptions activityOptions, Callback callback);

    void startActivity(Intent intent, boolean z, int i);

    void startActivity(Intent intent, boolean z, Callback callback, int i);

    void startActivity(Intent intent, boolean z, boolean z2, int i);

    void startActivity(Intent intent, boolean z, boolean z2, int i, int i2);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, int i);

    void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, int i);
}
