package com.android.systemui.statusbar.policy;

import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.ExecutorContentObserver;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.telephony.TelephonyManager;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.util.FrameworkStatsLog;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.Assert;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public class SensitiveNotificationProtectionControllerImpl implements SensitiveNotificationProtectionController {
    private SensitiveNotificatioMediaProjectionSession mActiveMediaProjectionSession;
    final MediaProjectionManager.Callback mMediaProjectionCallback;
    private final PackageManager mPackageManager;
    private volatile MediaProjectionInfo mProjection;
    private final ArraySet mSessionProtectionExemptPackages = new ArraySet();
    private final ArraySet mNotificationProtectionExemptPackages = new ArraySet();
    private final ListenerSet mListeners = new ListenerSet();
    boolean mDisableScreenShareProtections = false;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$2, reason: invalid class name */
    class AnonymousClass2 extends ExecutorContentObserver {
        final /* synthetic */ Handler val$mainHandler;
        final /* synthetic */ GlobalSettings val$settings;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(Executor executor, GlobalSettings globalSettings, Handler handler) {
            super(executor);
            this.val$settings = globalSettings;
            this.val$mainHandler = handler;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChange$0(boolean z) {
            SensitiveNotificationProtectionControllerImpl.this.mDisableScreenShareProtections = z;
        }

        public void onChange(boolean z) {
            super.onChange(z);
            final boolean z2 = this.val$settings.getInt("disable_screen_share_protections_for_apps_and_notifications", 0) != 0;
            this.val$mainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onChange$0(z2);
                }
            });
        }
    }

    class SensitiveNotificatioMediaProjectionSession {
        final boolean mExempt;
        final int mProjectionAppUid;
        final long mSessionId;

        SensitiveNotificatioMediaProjectionSession(long j, int i, boolean z) {
            this.mSessionId = j;
            this.mProjectionAppUid = i;
            this.mExempt = z;
        }
    }

    public SensitiveNotificationProtectionControllerImpl(final Context context, GlobalSettings globalSettings, MediaProjectionManager mediaProjectionManager, final IActivityManager iActivityManager, PackageManager packageManager, final TelephonyManager telephonyManager, final Handler handler, Executor executor) {
        MediaProjectionManager.Callback callback = new MediaProjectionManager.Callback() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl.1
            public void onStart(MediaProjectionInfo mediaProjectionInfo) {
                int packageUidAsUser;
                Trace.beginSection("SNPC.onProjectionStart");
                try {
                    SensitiveNotificationProtectionControllerImpl.this.updateProjectionStateAndNotifyListeners(mediaProjectionInfo);
                    try {
                        packageUidAsUser = SensitiveNotificationProtectionControllerImpl.this.mPackageManager.getPackageUidAsUser(mediaProjectionInfo.getPackageName(), mediaProjectionInfo.getUserHandle().getIdentifier());
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.w("SNPC", "Package " + mediaProjectionInfo.getPackageName() + " not found");
                        packageUidAsUser = -1;
                    }
                    SensitiveNotificationProtectionControllerImpl.this.logSensitiveContentProtectionSessionStart(new Random().nextLong(), packageUidAsUser, !SensitiveNotificationProtectionControllerImpl.this.isSensitiveStateActive());
                } finally {
                    Trace.endSection();
                }
            }

            public void onStop(MediaProjectionInfo mediaProjectionInfo) {
                Trace.beginSection("SNPC.onProjectionStop");
                try {
                    SensitiveNotificationProtectionControllerImpl.this.logSensitiveContentProtectionSessionStop();
                    SensitiveNotificationProtectionControllerImpl.this.updateProjectionStateAndNotifyListeners(null);
                } finally {
                    Trace.endSection();
                }
            }
        };
        this.mMediaProjectionCallback = callback;
        this.mPackageManager = packageManager;
        if (FlagsFake.screenshareNotificationHiding()) {
            final ExecutorContentObserver anonymousClass2 = new AnonymousClass2(executor, globalSettings, handler);
            globalSettings.registerContentObserver("disable_screen_share_protections_for_apps_and_notifications", (ContentObserver) anonymousClass2);
            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    anonymousClass2.onChange(true);
                }
            });
            executor.execute(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$2(context, iActivityManager, telephonyManager, handler);
                }
            });
            mediaProjectionManager.addCallback(callback, handler);
        }
    }

    private boolean canRecordSensitiveContent(String str) {
        return this.mPackageManager.checkPermission("android.permission.RECORD_SENSITIVE_CONTENT", str) == 0;
    }

    private MediaProjectionInfo getNonExemptProjectionInfo(MediaProjectionInfo mediaProjectionInfo) {
        if (this.mDisableScreenShareProtections) {
            Log.w("SNPC", "Screen share protections disabled");
            return null;
        }
        if (mediaProjectionInfo != null && this.mSessionProtectionExemptPackages.contains(mediaProjectionInfo.getPackageName())) {
            Log.w("SNPC", "Screen share protections exempt for package " + mediaProjectionInfo.getPackageName());
            return null;
        }
        if (mediaProjectionInfo == null || !canRecordSensitiveContent(mediaProjectionInfo.getPackageName())) {
            if (mediaProjectionInfo == null || mediaProjectionInfo.getLaunchCookie() == null) {
                return mediaProjectionInfo;
            }
            Log.w("SNPC", "Screen share protections exempt for single app screenshare");
            return null;
        }
        Log.w("SNPC", "Screen share protections exempt for package " + mediaProjectionInfo.getPackageName() + " via permission");
        return null;
    }

    private static ArraySet getNotificationProtectionExemptPackages(TelephonyManager telephonyManager) {
        ArraySet arraySet = new ArraySet();
        if (FlagsFake.screenshareNotificationHidingBugFix()) {
            try {
                String emergencyAssistancePackageName = telephonyManager.getEmergencyAssistancePackageName();
                if (emergencyAssistancePackageName != null) {
                    arraySet.add(emergencyAssistancePackageName);
                    return arraySet;
                }
            } catch (IllegalStateException e) {
                Log.w("SNPC", "Error adding emergency assistance package to exemption", e);
            }
        }
        return arraySet;
    }

    private static ArraySet getSessionProtectionExemptPackages(Context context, IActivityManager iActivityManager) {
        ArraySet arraySet = new ArraySet();
        arraySet.add(context.getPackageName());
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ArraySet arraySet, ArraySet arraySet2) {
        Trace.beginSection("SNPC.exemptPackagesUpdated");
        try {
            updateExemptPackagesAndNotifyListeners(arraySet, arraySet2);
        } finally {
            Trace.endSection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(Context context, IActivityManager iActivityManager, TelephonyManager telephonyManager, Handler handler) {
        final ArraySet sessionProtectionExemptPackages = getSessionProtectionExemptPackages(context, iActivityManager);
        final ArraySet notificationProtectionExemptPackages = getNotificationProtectionExemptPackages(telephonyManager);
        handler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$1(sessionProtectionExemptPackages, notificationProtectionExemptPackages);
            }
        });
    }

    private void logSensitiveContentProtectionSession(SensitiveNotificatioMediaProjectionSession sensitiveNotificatioMediaProjectionSession, int i) {
        FrameworkStatsLog.write(830, sensitiveNotificatioMediaProjectionSession.mSessionId, sensitiveNotificatioMediaProjectionSession.mProjectionAppUid, sensitiveNotificatioMediaProjectionSession.mExempt, i, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logSensitiveContentProtectionSessionStart(long j, int i, boolean z) {
        SensitiveNotificatioMediaProjectionSession sensitiveNotificatioMediaProjectionSession = new SensitiveNotificatioMediaProjectionSession(j, i, z);
        this.mActiveMediaProjectionSession = sensitiveNotificatioMediaProjectionSession;
        logSensitiveContentProtectionSession(sensitiveNotificatioMediaProjectionSession, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logSensitiveContentProtectionSessionStop() {
        SensitiveNotificatioMediaProjectionSession sensitiveNotificatioMediaProjectionSession = this.mActiveMediaProjectionSession;
        if (sensitiveNotificatioMediaProjectionSession == null) {
            return;
        }
        logSensitiveContentProtectionSession(sensitiveNotificatioMediaProjectionSession, 2);
        this.mActiveMediaProjectionSession = null;
    }

    private void updateExemptPackagesAndNotifyListeners(ArraySet arraySet, ArraySet arraySet2) {
        Assert.isMainThread();
        this.mSessionProtectionExemptPackages.addAll(arraySet);
        if (FlagsFake.screenshareNotificationHidingBugFix()) {
            this.mNotificationProtectionExemptPackages.addAll(arraySet2);
        }
        if (this.mProjection != null) {
            updateProjectionStateAndNotifyListeners(this.mProjection);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProjectionStateAndNotifyListeners(MediaProjectionInfo mediaProjectionInfo) {
        Assert.isMainThread();
        boolean zIsSensitiveStateActive = isSensitiveStateActive();
        this.mProjection = getNonExemptProjectionInfo(mediaProjectionInfo);
        if (zIsSensitiveStateActive || isSensitiveStateActive()) {
            this.mListeners.forEach(new Consumer() { // from class: com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((Runnable) obj).run();
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController
    public boolean isSensitiveStateActive() {
        return this.mProjection != null;
    }

    @Override // com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController
    public void registerSensitiveStateListener(Runnable runnable) {
        this.mListeners.addIfAbsent(runnable);
    }

    @Override // com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController
    public boolean shouldProtectNotification(NotificationEntry notificationEntry) {
        MediaProjectionInfo mediaProjectionInfo;
        if (!isSensitiveStateActive() || (mediaProjectionInfo = this.mProjection) == null) {
            return false;
        }
        StatusBarNotification sbn = notificationEntry.getSbn();
        if (sbn.getNotification().isFgsOrUij() && sbn.getPackageName().equals(mediaProjectionInfo.getPackageName())) {
            return false;
        }
        if (FlagsFake.screenshareNotificationHidingBugFix() && UserHandle.isCore(sbn.getUid())) {
            return false;
        }
        if (FlagsFake.screenshareNotificationHidingBugFix() && this.mNotificationProtectionExemptPackages.contains(sbn.getPackageName())) {
            return false;
        }
        return notificationEntry.isNotificationVisibilityPrivate() || notificationEntry.isChannelVisibilityPrivate();
    }
}
