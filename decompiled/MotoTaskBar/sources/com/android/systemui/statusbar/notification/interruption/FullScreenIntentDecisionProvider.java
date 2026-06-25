package com.android.systemui.statusbar.notification.interruption;

import android.app.Notification;
import android.os.PowerManager;
import android.service.notification.StatusBarNotification;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionSuppressor;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FullScreenIntentDecisionProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FullScreenIntentDecisionProvider {
    private final DeviceProvisionedController deviceProvisionedController;
    private final PowerManager powerManager;

    /* JADX INFO: compiled from: FullScreenIntentDecisionProvider.kt */
    public interface Decision {
        String getLogReason();

        boolean getShouldFsi();

        boolean getShouldLog();

        boolean getWouldFsiWithoutDnd();

        boolean isWarning();
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: FullScreenIntentDecisionProvider.kt */
    final class DecisionImpl implements Decision {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ DecisionImpl[] $VALUES;
        public static final DecisionImpl FSI_DEVICE_DREAMING;
        public static final DecisionImpl FSI_DEVICE_NOT_INTERACTIVE;
        public static final DecisionImpl FSI_DEVICE_NOT_PROVISIONED;
        public static final DecisionImpl FSI_KEYGUARD_OCCLUDED;
        public static final DecisionImpl FSI_KEYGUARD_SHOWING;
        public static final DecisionImpl FSI_LOCKED_SHADE;
        public static final DecisionImpl FSI_USER_SETUP_INCOMPLETE;
        public static final DecisionImpl NO_FSI_EXPECTED_TO_HUN;
        public static final DecisionImpl NO_FSI_NOT_IMPORTANT_ENOUGH;
        public static final DecisionImpl NO_FSI_NO_FULL_SCREEN_INTENT;
        public static final DecisionImpl NO_FSI_NO_HUN_OR_KEYGUARD;
        public static final DecisionImpl NO_FSI_PACKAGE_SUSPENDED;
        public static final DecisionImpl NO_FSI_SHOW_STICKY_HUN;
        public static final DecisionImpl NO_FSI_SUPPRESSED_BY_DND;
        public static final DecisionImpl NO_FSI_SUPPRESSED_ONLY_BY_DND;
        public static final DecisionImpl NO_FSI_SUPPRESSIVE_BUBBLE_METADATA;
        public static final DecisionImpl NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR;
        private final VisualInterruptionSuppressor.EventLogData eventLogData;
        private final boolean isWarning;
        private final String logReason;
        private final boolean shouldFsi;
        private final boolean shouldLog;
        private final boolean supersedesDnd;
        private final UiEventLogger.UiEventEnum uiEventId;
        private final boolean wouldFsiWithoutDnd;

        private static final /* synthetic */ DecisionImpl[] $values() {
            return new DecisionImpl[]{NO_FSI_NO_FULL_SCREEN_INTENT, NO_FSI_SHOW_STICKY_HUN, NO_FSI_NOT_IMPORTANT_ENOUGH, NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, NO_FSI_SUPPRESSIVE_BUBBLE_METADATA, NO_FSI_PACKAGE_SUSPENDED, FSI_DEVICE_NOT_INTERACTIVE, FSI_DEVICE_DREAMING, FSI_KEYGUARD_SHOWING, NO_FSI_EXPECTED_TO_HUN, FSI_KEYGUARD_OCCLUDED, FSI_LOCKED_SHADE, FSI_DEVICE_NOT_PROVISIONED, FSI_USER_SETUP_INCOMPLETE, NO_FSI_NO_HUN_OR_KEYGUARD, NO_FSI_SUPPRESSED_BY_DND, NO_FSI_SUPPRESSED_ONLY_BY_DND};
        }

        static {
            boolean z = false;
            VisualInterruptionSuppressor.EventLogData eventLogData = null;
            NO_FSI_NO_FULL_SCREEN_INTENT = new DecisionImpl("NO_FSI_NO_FULL_SCREEN_INTENT", 0, false, "no full-screen intent", false, true, false, z, null, eventLogData, 228, null);
            boolean z2 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData2 = null;
            NO_FSI_SHOW_STICKY_HUN = new DecisionImpl("NO_FSI_SHOW_STICKY_HUN", 1, false, "full-screen intents are disabled", false, true, z, z2, eventLogData, eventLogData2, 244, null);
            boolean z3 = false;
            NO_FSI_NOT_IMPORTANT_ENOUGH = new DecisionImpl("NO_FSI_NOT_IMPORTANT_ENOUGH", 2, false, "not important enough", false, z, z2, z3, eventLogData2, null, 252, null);
            NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR = new DecisionImpl("NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR", 3, false, "suppressive group alert behavior", z, z2, z3, true, NotificationInterruptStateProviderImpl.NotificationInterruptEvent.FSI_SUPPRESSED_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR, new VisualInterruptionSuppressor.EventLogData("231322873", "groupAlertBehavior"), 28, null);
            boolean z4 = false;
            NO_FSI_SUPPRESSIVE_BUBBLE_METADATA = new DecisionImpl("NO_FSI_SUPPRESSIVE_BUBBLE_METADATA", 4, false, "suppressive bubble metadata", z2, z3, z4, true, NotificationInterruptStateProviderImpl.NotificationInterruptEvent.FSI_SUPPRESSED_SUPPRESSIVE_BUBBLE_METADATA, new VisualInterruptionSuppressor.EventLogData("274759612", "bubbleMetadata"), 28, null);
            boolean z5 = false;
            boolean z6 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData3 = null;
            NO_FSI_PACKAGE_SUSPENDED = new DecisionImpl("NO_FSI_PACKAGE_SUSPENDED", 5, false, "package suspended", z3, z4, z5, z6, null, eventLogData3, 252, null);
            boolean z7 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData4 = null;
            FSI_DEVICE_NOT_INTERACTIVE = new DecisionImpl("FSI_DEVICE_NOT_INTERACTIVE", 6, true, "device is not interactive", z4, z5, z6, z7, eventLogData3, eventLogData4, 252, null);
            boolean z8 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData5 = null;
            FSI_DEVICE_DREAMING = new DecisionImpl("FSI_DEVICE_DREAMING", 7, true, "device is dreaming", z5, z6, z7, z8, eventLogData4, eventLogData5, 252, null);
            boolean z9 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData6 = null;
            FSI_KEYGUARD_SHOWING = new DecisionImpl("FSI_KEYGUARD_SHOWING", 8, true, "keyguard is showing", z6, z7, z8, z9, eventLogData5, eventLogData6, 252, null);
            boolean z10 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData7 = null;
            NO_FSI_EXPECTED_TO_HUN = new DecisionImpl("NO_FSI_EXPECTED_TO_HUN", 9, false, "expected to heads-up instead", z7, z8, z9, z10, eventLogData6, eventLogData7, 252, null);
            boolean z11 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData8 = null;
            FSI_KEYGUARD_OCCLUDED = new DecisionImpl("FSI_KEYGUARD_OCCLUDED", 10, true, "keyguard is occluded", z8, z9, z10, z11, eventLogData7, eventLogData8, 252, null);
            boolean z12 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData9 = null;
            FSI_LOCKED_SHADE = new DecisionImpl("FSI_LOCKED_SHADE", 11, true, "locked shade", z9, z10, z11, z12, eventLogData8, eventLogData9, 252, null);
            boolean z13 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData10 = null;
            FSI_DEVICE_NOT_PROVISIONED = new DecisionImpl("FSI_DEVICE_NOT_PROVISIONED", 12, true, "device not provisioned", z10, z11, z12, z13, eventLogData9, eventLogData10, 252, null);
            boolean z14 = false;
            FSI_USER_SETUP_INCOMPLETE = new DecisionImpl("FSI_USER_SETUP_INCOMPLETE", 13, true, "user setup incomplete", z11, z12, z13, z14, eventLogData10, null, 252, null);
            NO_FSI_NO_HUN_OR_KEYGUARD = new DecisionImpl("NO_FSI_NO_HUN_OR_KEYGUARD", 14, false, "no HUN or keyguard", z12, z13, z14, true, NotificationInterruptStateProviderImpl.NotificationInterruptEvent.FSI_SUPPRESSED_NO_HUN_OR_KEYGUARD, new VisualInterruptionSuppressor.EventLogData("231322873", "no hun or keyguard"), 28, null);
            boolean z15 = false;
            boolean z16 = false;
            VisualInterruptionSuppressor.EventLogData eventLogData11 = null;
            NO_FSI_SUPPRESSED_BY_DND = new DecisionImpl("NO_FSI_SUPPRESSED_BY_DND", 15, false, "suppressed by DND", false, false, z15, z16, null, eventLogData11, 248, null);
            NO_FSI_SUPPRESSED_ONLY_BY_DND = new DecisionImpl("NO_FSI_SUPPRESSED_ONLY_BY_DND", 16, false, "suppressed only by DND", true, z15, z16, false, eventLogData11, null, 248, null);
            DecisionImpl[] decisionImplArr$values = $values();
            $VALUES = decisionImplArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(decisionImplArr$values);
        }

        private DecisionImpl(String str, int i, boolean z, String str2, boolean z2, boolean z3, boolean z4, boolean z5, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor.EventLogData eventLogData) {
            this.shouldFsi = z;
            this.logReason = str2;
            this.wouldFsiWithoutDnd = z2;
            this.supersedesDnd = z3;
            this.shouldLog = z4;
            this.isWarning = z5;
            this.uiEventId = uiEventEnum;
            this.eventLogData = eventLogData;
        }

        /* synthetic */ DecisionImpl(String str, int i, boolean z, String str2, boolean z2, boolean z3, boolean z4, boolean z5, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor.EventLogData eventLogData, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, i, z, str2, (i2 & 4) != 0 ? z : z2, (i2 & 8) != 0 ? false : z3, (i2 & 16) != 0 ? true : z4, (i2 & 32) != 0 ? false : z5, (i2 & 64) != 0 ? null : uiEventEnum, (i2 & 128) != 0 ? null : eventLogData);
        }

        public static DecisionImpl valueOf(String str) {
            return (DecisionImpl) Enum.valueOf(DecisionImpl.class, str);
        }

        public static DecisionImpl[] values() {
            return (DecisionImpl[]) $VALUES.clone();
        }

        @Override // com.android.systemui.statusbar.notification.interruption.FullScreenIntentDecisionProvider.Decision
        public String getLogReason() {
            return this.logReason;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.FullScreenIntentDecisionProvider.Decision
        public boolean getShouldFsi() {
            return this.shouldFsi;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.FullScreenIntentDecisionProvider.Decision
        public boolean getShouldLog() {
            return this.shouldLog;
        }

        public final boolean getSupersedesDnd() {
            return this.supersedesDnd;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.FullScreenIntentDecisionProvider.Decision
        public boolean getWouldFsiWithoutDnd() {
            return this.wouldFsiWithoutDnd;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.FullScreenIntentDecisionProvider.Decision
        public boolean isWarning() {
            return this.isWarning;
        }
    }

    public FullScreenIntentDecisionProvider(DeviceProvisionedController deviceProvisionedController, PowerManager powerManager) {
        deviceProvisionedController.getClass();
        powerManager.getClass();
        this.deviceProvisionedController = deviceProvisionedController;
        this.powerManager = powerManager;
    }

    private final DecisionImpl makeDecisionWithoutDnd(NotificationEntry notificationEntry, boolean z) {
        StatusBarNotification sbn = notificationEntry.getSbn();
        sbn.getClass();
        Notification notification = sbn.getNotification();
        notification.getClass();
        if (notification.fullScreenIntent == null) {
            return notificationEntry.isStickyAndNotDemoted() ? DecisionImpl.NO_FSI_SHOW_STICKY_HUN : DecisionImpl.NO_FSI_NO_FULL_SCREEN_INTENT;
        }
        if (notificationEntry.getImportance() < 4) {
            return DecisionImpl.NO_FSI_NOT_IMPORTANT_ENOUGH;
        }
        if (sbn.isGroup() && notification.suppressAlertingDueToGrouping()) {
            return DecisionImpl.NO_FSI_SUPPRESSIVE_GROUP_ALERT_BEHAVIOR;
        }
        Notification.BubbleMetadata bubbleMetadata = notification.getBubbleMetadata();
        return (bubbleMetadata == null || !bubbleMetadata.isNotificationSuppressed()) ? notificationEntry.getRanking().isSuspended() ? DecisionImpl.NO_FSI_PACKAGE_SUSPENDED : !this.powerManager.isInteractive() ? DecisionImpl.FSI_DEVICE_NOT_INTERACTIVE : z ? DecisionImpl.NO_FSI_EXPECTED_TO_HUN : !this.deviceProvisionedController.isDeviceProvisioned() ? DecisionImpl.FSI_DEVICE_NOT_PROVISIONED : !this.deviceProvisionedController.isCurrentUserSetup() ? DecisionImpl.FSI_USER_SETUP_INCOMPLETE : DecisionImpl.NO_FSI_NO_HUN_OR_KEYGUARD : DecisionImpl.NO_FSI_SUPPRESSIVE_BUBBLE_METADATA;
    }

    public final Decision makeFullScreenIntentDecision(NotificationEntry notificationEntry, boolean z) {
        notificationEntry.getClass();
        DecisionImpl decisionImplMakeDecisionWithoutDnd = makeDecisionWithoutDnd(notificationEntry, z);
        boolean shouldFsi = decisionImplMakeDecisionWithoutDnd.getShouldFsi();
        boolean zShouldSuppressFullScreenIntent = notificationEntry.shouldSuppressFullScreenIntent();
        return decisionImplMakeDecisionWithoutDnd.getSupersedesDnd() ? decisionImplMakeDecisionWithoutDnd : (zShouldSuppressFullScreenIntent && shouldFsi) ? DecisionImpl.NO_FSI_SUPPRESSED_ONLY_BY_DND : zShouldSuppressFullScreenIntent ? DecisionImpl.NO_FSI_SUPPRESSED_BY_DND : decisionImplMakeDecisionWithoutDnd;
    }
}
