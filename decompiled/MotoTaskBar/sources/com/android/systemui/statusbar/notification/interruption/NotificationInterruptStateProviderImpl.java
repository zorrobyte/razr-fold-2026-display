package com.android.systemui.statusbar.notification.interruption;

import android.app.Notification;
import android.database.ContentObserver;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationInterruptStateProviderImpl implements NotificationInterruptStateProvider {
    private final AmbientDisplayConfiguration mAmbientDisplayConfiguration;
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final NotifPipelineFlags mFlags;
    private final GlobalSettings mGlobalSettings;
    private final HeadsUpManager mHeadsUpManager;
    private final KeyguardNotificationVisibilityProvider mKeyguardNotificationVisibilityProvider;
    private final NotificationInterruptLogger mLogger;
    private final PowerManager mPowerManager;
    private final SystemClock mSystemClock;
    private final UiEventLogger mUiEventLogger;
    private final UserTracker mUserTracker;
    private final List mSuppressors = new ArrayList();
    protected boolean mUseHeadsUp = false;

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl$2, reason: invalid class name */
    abstract /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision;

        static {
            int[] iArr = new int[NotificationInterruptStateProvider.FullScreenIntentDecision.values().length];
            $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision = iArr;
            try {
                iArr[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FULL_SCREEN_INTENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_BUBBLE_METADATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision[NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NO_HUN_OR_KEYGUARD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum NotificationInterruptEvent implements UiEventLogger.UiEventEnum {
        FSI_SUPPRESSED_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR(1235),
        FSI_SUPPRESSED_SUPPRESSIVE_BUBBLE_METADATA(1353),
        FSI_SUPPRESSED_NO_HUN_OR_KEYGUARD(1236),
        HUN_SUPPRESSED_OLD_WHEN(1237),
        HUN_SNOOZE_BYPASSED_POTENTIALLY_SUPPRESSED_FSI(1269);

        private final int mId;

        NotificationInterruptEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public NotificationInterruptStateProviderImpl(PowerManager powerManager, AmbientDisplayConfiguration ambientDisplayConfiguration, HeadsUpManager headsUpManager, NotificationInterruptLogger notificationInterruptLogger, Handler handler, NotifPipelineFlags notifPipelineFlags, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, UiEventLogger uiEventLogger, UserTracker userTracker, DeviceProvisionedController deviceProvisionedController, SystemClock systemClock, GlobalSettings globalSettings) {
        this.mPowerManager = powerManager;
        this.mAmbientDisplayConfiguration = ambientDisplayConfiguration;
        this.mHeadsUpManager = headsUpManager;
        this.mLogger = notificationInterruptLogger;
        this.mFlags = notifPipelineFlags;
        this.mKeyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.mUiEventLogger = uiEventLogger;
        this.mUserTracker = userTracker;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mSystemClock = systemClock;
        this.mGlobalSettings = globalSettings;
        ContentObserver contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl = NotificationInterruptStateProviderImpl.this;
                boolean z2 = notificationInterruptStateProviderImpl.mUseHeadsUp;
                boolean z3 = notificationInterruptStateProviderImpl.mGlobalSettings.getInt("heads_up_notifications_enabled", 0) != 0;
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl2 = NotificationInterruptStateProviderImpl.this;
                notificationInterruptStateProviderImpl2.mUseHeadsUp = z3;
                notificationInterruptStateProviderImpl2.mLogger.logHeadsUpFeatureChanged(NotificationInterruptStateProviderImpl.this.mUseHeadsUp);
                NotificationInterruptStateProviderImpl notificationInterruptStateProviderImpl3 = NotificationInterruptStateProviderImpl.this;
                boolean z4 = notificationInterruptStateProviderImpl3.mUseHeadsUp;
                if (z2 == z4 || z4) {
                    return;
                }
                notificationInterruptStateProviderImpl3.mLogger.logWillDismissAll();
                NotificationInterruptStateProviderImpl.this.mHeadsUpManager.releaseAllImmediately();
            }
        };
        globalSettings.registerContentObserver(globalSettings.getUriFor("heads_up_notifications_enabled"), true, contentObserver);
        globalSettings.registerContentObserver(globalSettings.getUriFor("ticker_gets_heads_up"), true, contentObserver);
        contentObserver.onChange(true);
    }

    private boolean canAlertAwakeCommon(NotificationEntry notificationEntry, boolean z) {
        notificationEntry.getSbn();
        if (this.mSuppressors.size() <= 0) {
            return true;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mSuppressors.get(0));
        throw null;
    }

    private boolean canAlertCommon(NotificationEntry notificationEntry, boolean z) {
        if (this.mSuppressors.size() > 0) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mSuppressors.get(0));
            throw null;
        }
        if (notificationEntry.getRanking().isSuspended()) {
            if (z) {
                this.mLogger.logNoAlertingAppSuspended(notificationEntry);
            }
            return false;
        }
        if (!this.mKeyguardNotificationVisibilityProvider.shouldHideNotification(notificationEntry)) {
            return true;
        }
        if (z) {
            this.mLogger.logNoAlertingNotificationHidden(notificationEntry);
        }
        return false;
    }

    private boolean canAlertHeadsUpCommon(NotificationEntry notificationEntry, boolean z) {
        StatusBarNotification sbn = notificationEntry.getSbn();
        if (sbn.isGroup() && sbn.getNotification().suppressAlertingDueToGrouping()) {
            if (z) {
                this.mLogger.logNoAlertingGroupAlertBehavior(notificationEntry);
            }
            return false;
        }
        if (!notificationEntry.hasJustLaunchedFullScreenIntent()) {
            return true;
        }
        if (z) {
            this.mLogger.logNoAlertingRecentFullscreen(notificationEntry);
        }
        return false;
    }

    private NotificationInterruptStateProvider.FullScreenIntentDecision getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision, boolean z) {
        return z ? fullScreenIntentDecision.shouldLaunch ? NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_ONLY_BY_DND : NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_BY_DND : fullScreenIntentDecision;
    }

    private boolean isSnoozedPackage(StatusBarNotification statusBarNotification) {
        return this.mHeadsUpManager.isSnoozed(statusBarNotification.getPackageName());
    }

    private boolean shouldHeadsUpWhenAwake(NotificationEntry notificationEntry, boolean z) {
        StatusBarNotification sbn = notificationEntry.getSbn();
        if (!this.mUseHeadsUp) {
            if (z) {
                this.mLogger.logNoHeadsUpFeatureDisabled();
            }
            return false;
        }
        if (!canAlertCommon(notificationEntry, z) || !canAlertHeadsUpCommon(notificationEntry, z) || !canAlertAwakeCommon(notificationEntry, z)) {
            return false;
        }
        boolean zIsSnoozedPackage = isSnoozedPackage(sbn);
        boolean z2 = sbn.getNotification().fullScreenIntent != null;
        if (zIsSnoozedPackage && !z2) {
            if (z) {
                this.mLogger.logNoHeadsUpPackageSnoozed(notificationEntry);
            }
            return false;
        }
        if (notificationEntry.shouldSuppressPeek()) {
            if (z) {
                this.mLogger.logNoHeadsUpSuppressedByDnd(notificationEntry);
            }
            return false;
        }
        if (notificationEntry.getImportance() < 4) {
            if (z) {
                this.mLogger.logNoHeadsUpNotImportant(notificationEntry);
            }
            return false;
        }
        if (shouldSuppressHeadsUpWhenAwakeForOldWhen(notificationEntry, z)) {
            return false;
        }
        if (this.mSuppressors.size() > 0) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mSuppressors.get(0));
            throw null;
        }
        if (!zIsSnoozedPackage) {
            if (z) {
                this.mLogger.logHeadsUp(notificationEntry);
            }
            return true;
        }
        if (z) {
            this.mLogger.logHeadsUpPackageSnoozeBypassedHasFsi(notificationEntry);
            this.mUiEventLogger.log(NotificationInterruptEvent.HUN_SNOOZE_BYPASSED_POTENTIALLY_SUPPRESSED_FSI, notificationEntry.getSbn().getUid(), notificationEntry.getSbn().getPackageName());
        }
        return true;
    }

    private boolean shouldSuppressHeadsUpWhenAwakeForOldWhen(NotificationEntry notificationEntry, boolean z) {
        Notification notification = notificationEntry.getSbn().getNotification();
        if (notification == null) {
            return false;
        }
        long j = notification.when;
        long jCurrentTimeMillis = this.mSystemClock.currentTimeMillis() - j;
        if (jCurrentTimeMillis < 86400000) {
            return false;
        }
        if (j <= 0) {
            if (z) {
                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, j, jCurrentTimeMillis, "when <= 0");
            }
            return false;
        }
        if (notification.fullScreenIntent != null) {
            if (z) {
                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, j, jCurrentTimeMillis, "full-screen intent");
            }
            return false;
        }
        if (notification.isForegroundService()) {
            if (z) {
                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, j, jCurrentTimeMillis, "foreground service");
            }
            return false;
        }
        if (notification.isUserInitiatedJob()) {
            if (z) {
                this.mLogger.logMaybeHeadsUpDespiteOldWhen(notificationEntry, j, jCurrentTimeMillis, "user initiated job");
            }
            return false;
        }
        if (z) {
            this.mLogger.logNoHeadsUpOldWhen(notificationEntry, j, jCurrentTimeMillis);
        }
        this.mUiEventLogger.log(NotificationInterruptEvent.HUN_SUPPRESSED_OLD_WHEN, notificationEntry.getSbn().getUid(), notificationEntry.getSbn().getPackageName());
        return true;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider
    public boolean checkHeadsUp(NotificationEntry notificationEntry, boolean z) {
        return shouldHeadsUpWhenAwake(notificationEntry, z);
    }

    @Override // com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider
    public NotificationInterruptStateProvider.FullScreenIntentDecision getFullScreenIntentDecision(NotificationEntry notificationEntry) {
        if (notificationEntry.getSbn().getNotification().fullScreenIntent == null) {
            return notificationEntry.isStickyAndNotDemoted() ? NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SHOW_STICKY_HUN : NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FULL_SCREEN_INTENT;
        }
        boolean zShouldSuppressFullScreenIntent = notificationEntry.shouldSuppressFullScreenIntent();
        if (notificationEntry.getImportance() < 4) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NOT_IMPORTANT_ENOUGH, zShouldSuppressFullScreenIntent);
        }
        StatusBarNotification sbn = notificationEntry.getSbn();
        if (sbn.isGroup() && sbn.getNotification().suppressAlertingDueToGrouping()) {
            return getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, zShouldSuppressFullScreenIntent);
        }
        Notification.BubbleMetadata bubbleMetadata = sbn.getNotification().getBubbleMetadata();
        return (bubbleMetadata == null || !bubbleMetadata.isNotificationSuppressed()) ? notificationEntry.getRanking().isSuspended() ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUSPENDED, zShouldSuppressFullScreenIntent) : !this.mPowerManager.isInteractive() ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_DEVICE_NOT_INTERACTIVE, zShouldSuppressFullScreenIntent) : checkHeadsUp(notificationEntry, false) ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_EXPECTED_TO_HUN, zShouldSuppressFullScreenIntent) : !this.mDeviceProvisionedController.isDeviceProvisioned() ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_NOT_PROVISIONED, zShouldSuppressFullScreenIntent) : !this.mDeviceProvisionedController.isCurrentUserSetup() ? getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.FSI_USER_SETUP_INCOMPLETE, zShouldSuppressFullScreenIntent) : getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_NO_HUN_OR_KEYGUARD, zShouldSuppressFullScreenIntent) : getDecisionGivenSuppression(NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSIVE_BUBBLE_METADATA, zShouldSuppressFullScreenIntent);
    }

    @Override // com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider
    public void logFullScreenIntentDecision(NotificationEntry notificationEntry, NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision) {
        int uid = notificationEntry.getSbn().getUid();
        String packageName = notificationEntry.getSbn().getPackageName();
        int i = AnonymousClass2.$SwitchMap$com$android$systemui$statusbar$notification$interruption$NotificationInterruptStateProvider$FullScreenIntentDecision[fullScreenIntentDecision.ordinal()];
        if (i != 1) {
            if (i == 2) {
                this.mUiEventLogger.log(NotificationInterruptEvent.FSI_SUPPRESSED_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, uid, packageName);
                this.mLogger.logNoFullscreenWarning(notificationEntry, fullScreenIntentDecision + ": GroupAlertBehavior will prevent HUN");
                return;
            }
            if (i == 3) {
                this.mUiEventLogger.log(NotificationInterruptEvent.FSI_SUPPRESSED_SUPPRESSIVE_BUBBLE_METADATA, uid, packageName);
                this.mLogger.logNoFullscreenWarning(notificationEntry, fullScreenIntentDecision + ": BubbleMetadata may prevent HUN");
                return;
            }
            if (i != 4) {
                if (fullScreenIntentDecision.shouldLaunch) {
                    this.mLogger.logFullscreen(notificationEntry, fullScreenIntentDecision.name());
                    return;
                } else {
                    this.mLogger.logNoFullscreen(notificationEntry, fullScreenIntentDecision.name());
                    return;
                }
            }
            this.mUiEventLogger.log(NotificationInterruptEvent.FSI_SUPPRESSED_NO_HUN_OR_KEYGUARD, uid, packageName);
            this.mLogger.logNoFullscreenWarning(notificationEntry, fullScreenIntentDecision + ": Expected not to HUN while not on keyguard");
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider
    public void removeSuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor) {
        this.mSuppressors.remove(notificationInterruptSuppressor);
    }
}
