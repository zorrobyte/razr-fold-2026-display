package com.android.systemui;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.ProfilerInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.plugins.moto.ActivityStarter;

/* JADX INFO: loaded from: classes.dex */
public class ActivityStarterDelegate implements ActivityStarter {
    private Context mContext;
    private Handler mMainThreadHandler;

    public ActivityStarterDelegate(Context context, Looper looper) {
        this.mContext = context;
        this.mMainThreadHandler = new Handler(looper);
    }

    private void postOnUiThread(Runnable runnable) {
        this.mMainThreadHandler.post(runnable);
    }

    private void startActivityDismissingKeyguard(Intent intent, boolean z, boolean z2, boolean z3, ActivityStarter.Callback callback, int i, int i2) {
        int iStartActivityAsUser;
        intent.setFlags(335544320);
        intent.addFlags(i);
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setDisallowEnterPictureInPictureWhileLaunching(z3);
        activityOptionsMakeBasic.setLaunchDisplayId(i2);
        try {
            iStartActivityAsUser = ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, this.mContext.getBasePackageName(), this.mContext.getAttributionTag(), intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, activityOptionsMakeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
        } catch (RemoteException e) {
            Log.w("ActivityStarterDelegate", "Unable to start activity", e);
            iStartActivityAsUser = -96;
        }
        if (callback != null) {
            callback.onActivityStarted(iStartActivityAsUser);
        }
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void dismissKeyguardThenExecute(final ActivityStarter.OnDismissAction onDismissAction, Runnable runnable, boolean z) {
        this.mMainThreadHandler.post(new Runnable() { // from class: com.android.systemui.ActivityStarterDelegate$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                onDismissAction.onDismiss();
            }
        });
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void postQSRunnableDismissingKeyguard(Runnable runnable) {
        this.mMainThreadHandler.post(runnable);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void postStartActivityDismissingKeyguard(PendingIntent pendingIntent, int i) {
        startPendingIntentDismissingKeyguard(pendingIntent, null, i);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void postStartActivityDismissingKeyguard(Intent intent, int i, int i2) {
        startActivityDismissingKeyguard(intent, false, false, false, null, 0, i2);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void startActivity(Intent intent, ActivityOptions activityOptions) {
        startActivity(intent, activityOptions, (ActivityStarter.Callback) null);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void startActivity(Intent intent, ActivityOptions activityOptions, ActivityStarter.Callback callback) {
        int iStartActivityAsUser;
        intent.addFlags(335544320);
        try {
            iStartActivityAsUser = ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, this.mContext.getBasePackageName(), this.mContext.getAttributionTag(), intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, (activityOptions == null ? ActivityOptions.makeBasic() : activityOptions).toBundle(), UserHandle.CURRENT.getIdentifier());
        } catch (RemoteException e) {
            Log.w("ActivityStarterDelegate", "Unable to start activity", e);
            iStartActivityAsUser = -96;
        }
        if (callback != null) {
            callback.onActivityStarted(iStartActivityAsUser);
        }
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void startActivity(Intent intent, boolean z, int i) {
        startActivityDismissingKeyguard(intent, false, z, false, null, 0, i);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void startActivity(Intent intent, boolean z, ActivityStarter.Callback callback, int i) {
        startActivityDismissingKeyguard(intent, false, z, false, callback, 0, i);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void startActivity(Intent intent, boolean z, boolean z2, int i) {
        startActivityDismissingKeyguard(intent, z, z2, false, null, 0, i);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void startActivity(Intent intent, boolean z, boolean z2, int i, int i2) {
        startActivityDismissingKeyguard(intent, z, z2, false, null, i, i2);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, int i) {
        startPendingIntentDismissingKeyguard(pendingIntent, null, i);
    }

    @Override // com.android.systemui.plugins.moto.ActivityStarter
    public void startPendingIntentDismissingKeyguard(PendingIntent pendingIntent, Runnable runnable, int i) {
        try {
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setLaunchDisplayId(i);
            activityOptionsMakeBasic.setCallerDisplayId(i);
            activityOptionsMakeBasic.setPendingIntentBackgroundActivityLaunchAllowed(true);
            activityOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(null, 0, null, null, null, null, activityOptionsMakeBasic.toBundle());
        } catch (PendingIntent.CanceledException e) {
            Log.w("ActivityStarterDelegate", "Sending intent failed: " + e);
        }
        if (runnable != null) {
            postOnUiThread(runnable);
        }
    }
}
