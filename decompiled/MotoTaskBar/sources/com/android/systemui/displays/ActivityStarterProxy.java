package com.android.systemui.displays;

import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.plugins.moto.ActivityStarter;

/* JADX INFO: loaded from: classes.dex */
public class ActivityStarterProxy implements ActivityStarter {
    private final com.android.systemui.plugins.moto.ActivityStarter mActivityStarterDelegate;
    private final int mDisplayId;

    public ActivityStarterProxy(int i, com.android.systemui.plugins.moto.ActivityStarter activityStarter) {
        this.mDisplayId = i;
        this.mActivityStarterDelegate = activityStarter;
    }

    @Override // com.android.systemui.displays.ActivityStarter
    public void dismissKeyguardThenExecute(final ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        com.android.systemui.plugins.moto.ActivityStarter activityStarter = this.mActivityStarterDelegate;
        onDismissAction.getClass();
        activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.displays.ActivityStarterProxy$$ExternalSyntheticLambda0
            @Override // com.android.systemui.plugins.moto.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                return onDismissAction.onDismiss();
            }
        }, runnable, z);
    }

    @Override // com.android.systemui.displays.ActivityStarter
    public void postStartActivityDismissingKeyguard(PendingIntent pendingIntent) {
        this.mActivityStarterDelegate.postStartActivityDismissingKeyguard(pendingIntent, this.mDisplayId);
    }

    @Override // com.android.systemui.displays.ActivityStarter
    public void postStartActivityDismissingKeyguard(Intent intent, int i) {
        this.mActivityStarterDelegate.postStartActivityDismissingKeyguard(intent, i, this.mDisplayId);
    }

    @Override // com.android.systemui.displays.ActivityStarter
    public void startActivity(Intent intent, boolean z) {
        this.mActivityStarterDelegate.startActivity(intent, z, this.mDisplayId);
    }

    @Override // com.android.systemui.displays.ActivityStarter
    public void startActivity(Intent intent, boolean z, boolean z2, int i) {
        this.mActivityStarterDelegate.startActivity(intent, z, z2, i, this.mDisplayId);
    }

    @Override // com.android.systemui.displays.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent) {
        this.mActivityStarterDelegate.startPendingIntentDismissingKeyguard(pendingIntent, this.mDisplayId);
    }

    @Override // com.android.systemui.displays.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, View view) {
        this.mActivityStarterDelegate.startPendingIntentDismissingKeyguard(pendingIntent, runnable, this.mDisplayId);
    }
}
