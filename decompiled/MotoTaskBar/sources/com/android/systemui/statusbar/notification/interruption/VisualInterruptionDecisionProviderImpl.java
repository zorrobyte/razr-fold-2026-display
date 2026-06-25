package com.android.systemui.statusbar.notification.interruption;

import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Trace;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.FullScreenIntentDecisionProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionSuppressor;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VisualInterruptionDecisionProviderImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VisualInterruptionDecisionProviderImpl implements VisualInterruptionDecisionProvider {
    private final AmbientDisplayConfiguration ambientDisplayConfiguration;
    private final AvalancheProvider avalancheProvider;
    private final List conditions;
    private final int displayId;
    private final List filters;
    private final FullScreenIntentDecisionProvider fullScreenIntentDecisionProvider;
    private final GlobalSettings globalSettings;
    private final HeadsUpManager headsUpManager;
    private final KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider;
    private final Set legacySuppressors;
    private final VisualInterruptionDecisionLogger logger;
    private final Handler mainHandler;
    private final PowerManager powerManager;
    private boolean started;
    private final SystemClock systemClock;
    private final SystemSettings systemSettings;
    private final UiEventLogger uiEventLogger;
    private final UserTracker userTracker;

    /* JADX INFO: compiled from: VisualInterruptionDecisionProviderImpl.kt */
    final class DecisionImpl implements VisualInterruptionDecisionProvider.Decision {
        private final String logReason;
        private final boolean shouldInterrupt;

        public DecisionImpl(boolean z, String str) {
            str.getClass();
            this.shouldInterrupt = z;
            this.logReason = str;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public String getLogReason() {
            return this.logReason;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public boolean getShouldInterrupt() {
            return this.shouldInterrupt;
        }
    }

    /* JADX INFO: compiled from: VisualInterruptionDecisionProviderImpl.kt */
    final class FullScreenIntentDecisionImpl implements VisualInterruptionDecisionProvider.FullScreenIntentDecision, Loggable {
        private final NotificationEntry entry;
        private final FullScreenIntentDecisionProvider.Decision fsiDecision;
        private boolean hasBeenLogged;

        public FullScreenIntentDecisionImpl(NotificationEntry notificationEntry, FullScreenIntentDecisionProvider.Decision decision) {
            notificationEntry.getClass();
            decision.getClass();
            this.entry = notificationEntry;
            this.fsiDecision = decision;
        }

        public final NotificationEntry getEntry() {
            return this.entry;
        }

        public final boolean getHasBeenLogged() {
            return this.hasBeenLogged;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public String getLogReason() {
            return this.fsiDecision.getLogReason();
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public boolean getShouldInterrupt() {
            return this.fsiDecision.getShouldFsi();
        }

        public final boolean getShouldLog() {
            return this.fsiDecision.getShouldLog();
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.FullScreenIntentDecision
        public boolean getWouldInterruptWithoutDnd() {
            return this.fsiDecision.getWouldFsiWithoutDnd();
        }

        public final boolean isWarning() {
            return this.fsiDecision.isWarning();
        }

        public final void setHasBeenLogged(boolean z) {
            this.hasBeenLogged = z;
        }
    }

    /* JADX INFO: compiled from: VisualInterruptionDecisionProviderImpl.kt */
    public interface Loggable {
    }

    /* JADX INFO: compiled from: VisualInterruptionDecisionProviderImpl.kt */
    final class LoggableDecision implements Loggable {
        public static final Companion Companion = new Companion(null);
        private static final LoggableDecision unsuppressed = new LoggableDecision(new DecisionImpl(true, "not suppressed"), null, null, 6, null);
        private final DecisionImpl decision;
        private final VisualInterruptionSuppressor.EventLogData eventLogData;
        private final UiEventLogger.UiEventEnum uiEventId;

        /* JADX INFO: compiled from: VisualInterruptionDecisionProviderImpl.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final LoggableDecision getUnsuppressed() {
                return LoggableDecision.unsuppressed;
            }

            public final LoggableDecision suppressed(VisualInterruptionSuppressor visualInterruptionSuppressor) {
                visualInterruptionSuppressor.getClass();
                return new LoggableDecision(new DecisionImpl(false, visualInterruptionSuppressor.getReason()), visualInterruptionSuppressor.getUiEventId(), visualInterruptionSuppressor.getEventLogData(), null);
            }
        }

        private LoggableDecision(DecisionImpl decisionImpl, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor.EventLogData eventLogData) {
            this.decision = decisionImpl;
            this.uiEventId = uiEventEnum;
            this.eventLogData = eventLogData;
        }

        /* synthetic */ LoggableDecision(DecisionImpl decisionImpl, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor.EventLogData eventLogData, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(decisionImpl, (i & 2) != 0 ? null : uiEventEnum, (i & 4) != 0 ? null : eventLogData);
        }

        public /* synthetic */ LoggableDecision(DecisionImpl decisionImpl, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor.EventLogData eventLogData, DefaultConstructorMarker defaultConstructorMarker) {
            this(decisionImpl, uiEventEnum, eventLogData);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LoggableDecision)) {
                return false;
            }
            LoggableDecision loggableDecision = (LoggableDecision) obj;
            return Intrinsics.areEqual(this.decision, loggableDecision.decision) && Intrinsics.areEqual(this.uiEventId, loggableDecision.uiEventId) && Intrinsics.areEqual(this.eventLogData, loggableDecision.eventLogData);
        }

        public final DecisionImpl getDecision() {
            return this.decision;
        }

        public int hashCode() {
            int iHashCode = this.decision.hashCode() * 31;
            UiEventLogger.UiEventEnum uiEventEnum = this.uiEventId;
            int iHashCode2 = (iHashCode + (uiEventEnum == null ? 0 : uiEventEnum.hashCode())) * 31;
            VisualInterruptionSuppressor.EventLogData eventLogData = this.eventLogData;
            return iHashCode2 + (eventLogData != null ? eventLogData.hashCode() : 0);
        }

        public String toString() {
            return "LoggableDecision(decision=" + this.decision + ", uiEventId=" + this.uiEventId + ", eventLogData=" + this.eventLogData + ")";
        }
    }

    public VisualInterruptionDecisionProviderImpl(AmbientDisplayConfiguration ambientDisplayConfiguration, DeviceProvisionedController deviceProvisionedController, GlobalSettings globalSettings, HeadsUpManager headsUpManager, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, VisualInterruptionDecisionLogger visualInterruptionDecisionLogger, Handler handler, int i, PowerManager powerManager, SystemClock systemClock, UiEventLogger uiEventLogger, UserTracker userTracker, AvalancheProvider avalancheProvider, SystemSettings systemSettings) {
        ambientDisplayConfiguration.getClass();
        deviceProvisionedController.getClass();
        globalSettings.getClass();
        headsUpManager.getClass();
        keyguardNotificationVisibilityProvider.getClass();
        visualInterruptionDecisionLogger.getClass();
        handler.getClass();
        powerManager.getClass();
        systemClock.getClass();
        uiEventLogger.getClass();
        userTracker.getClass();
        avalancheProvider.getClass();
        systemSettings.getClass();
        this.ambientDisplayConfiguration = ambientDisplayConfiguration;
        this.globalSettings = globalSettings;
        this.headsUpManager = headsUpManager;
        this.keyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.logger = visualInterruptionDecisionLogger;
        this.mainHandler = handler;
        this.displayId = i;
        this.powerManager = powerManager;
        this.systemClock = systemClock;
        this.uiEventLogger = uiEventLogger;
        this.userTracker = userTracker;
        this.avalancheProvider = avalancheProvider;
        this.systemSettings = systemSettings;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        VisualInterruptionRefactor visualInterruptionRefactor = VisualInterruptionRefactor.INSTANCE;
        boolean zVisualInterruptionsRefactor = Flags.visualInterruptionsRefactor();
        if (!zVisualInterruptionsRefactor) {
            refactorFlagUtils.assertOnEngBuild("New code path expects " + ((Object) "VisualInterruptionRefactor") + " to be enabled.");
        }
        if (!zVisualInterruptionsRefactor) {
            throw new IllegalStateException("Check failed.");
        }
        this.fullScreenIntentDecisionProvider = new FullScreenIntentDecisionProvider(deviceProvisionedController, powerManager);
        this.legacySuppressors = new LinkedHashSet();
        this.conditions = new ArrayList();
        this.filters = new ArrayList();
    }

    private final LoggableDecision checkConditions(VisualInterruptionType visualInterruptionType) {
        Object next;
        Iterator it = this.conditions.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            VisualInterruptionCondition visualInterruptionCondition = (VisualInterruptionCondition) next;
            if (visualInterruptionCondition.getTypes().contains(visualInterruptionType) && visualInterruptionCondition.shouldSuppress()) {
                break;
            }
        }
        VisualInterruptionCondition visualInterruptionCondition2 = (VisualInterruptionCondition) next;
        if (visualInterruptionCondition2 != null) {
            return LoggableDecision.Companion.suppressed(visualInterruptionCondition2);
        }
        return null;
    }

    private final LoggableDecision checkFilters(VisualInterruptionType visualInterruptionType, NotificationEntry notificationEntry) {
        Object next;
        Iterator it = this.filters.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            VisualInterruptionFilter visualInterruptionFilter = (VisualInterruptionFilter) next;
            if (visualInterruptionFilter.getTypes().contains(visualInterruptionType) && visualInterruptionFilter.shouldSuppress(notificationEntry)) {
                break;
            }
        }
        VisualInterruptionFilter visualInterruptionFilter2 = (VisualInterruptionFilter) next;
        if (visualInterruptionFilter2 != null) {
            return LoggableDecision.Companion.suppressed(visualInterruptionFilter2);
        }
        return null;
    }

    private final LoggableDecision checkSuppressAwakeHeadsUp(NotificationEntry notificationEntry) {
        Iterator it = this.legacySuppressors.iterator();
        if (it.hasNext()) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(null);
        return null;
    }

    private final LoggableDecision checkSuppressAwakeInterruptions(NotificationEntry notificationEntry) {
        Iterator it = this.legacySuppressors.iterator();
        if (it.hasNext()) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(null);
        return null;
    }

    private final LoggableDecision checkSuppressInterruptions(NotificationEntry notificationEntry) {
        Iterator it = this.legacySuppressors.iterator();
        if (it.hasNext()) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(null);
        return null;
    }

    private final void logDecision(VisualInterruptionType visualInterruptionType, NotificationEntry notificationEntry, LoggableDecision loggableDecision) {
        this.logger.logDecision(visualInterruptionType.name(), notificationEntry, loggableDecision.getDecision());
        logEvents(notificationEntry, loggableDecision);
    }

    private final void logEvents(NotificationEntry notificationEntry, Loggable loggable) {
    }

    private final LoggableDecision makeLoggablePeekDecision(NotificationEntry notificationEntry) {
        VisualInterruptionType visualInterruptionType = VisualInterruptionType.PEEK;
        LoggableDecision loggableDecisionCheckConditions = checkConditions(visualInterruptionType);
        if (loggableDecisionCheckConditions != null) {
            return loggableDecisionCheckConditions;
        }
        LoggableDecision loggableDecisionCheckFilters = checkFilters(visualInterruptionType, notificationEntry);
        if (loggableDecisionCheckFilters != null || (loggableDecisionCheckFilters = checkSuppressInterruptions(notificationEntry)) != null || (loggableDecisionCheckFilters = checkSuppressAwakeInterruptions(notificationEntry)) != null) {
            return loggableDecisionCheckFilters;
        }
        LoggableDecision loggableDecisionCheckSuppressAwakeHeadsUp = checkSuppressAwakeHeadsUp(notificationEntry);
        return loggableDecisionCheckSuppressAwakeHeadsUp == null ? LoggableDecision.Companion.getUnsuppressed() : loggableDecisionCheckSuppressAwakeHeadsUp;
    }

    public void addCondition(VisualInterruptionCondition visualInterruptionCondition) {
        visualInterruptionCondition.getClass();
        this.conditions.add(visualInterruptionCondition);
        visualInterruptionCondition.start();
    }

    public void addFilter(VisualInterruptionFilter visualInterruptionFilter) {
        visualInterruptionFilter.getClass();
        this.filters.add(visualInterruptionFilter);
        visualInterruptionFilter.start();
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void logFullScreenIntentDecision(VisualInterruptionDecisionProvider.FullScreenIntentDecision fullScreenIntentDecision) {
        fullScreenIntentDecision.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#logFullScreenIntentDecision");
        }
        try {
            if (!this.started) {
                throw new IllegalStateException("Check failed.");
            }
            if (!(fullScreenIntentDecision instanceof FullScreenIntentDecisionImpl)) {
                Log.wtf("VisualInterruptionDecisionProviderImpl", "FSI decision " + fullScreenIntentDecision + " was not created by this class");
                if (zIsEnabled) {
                    return;
                } else {
                    return;
                }
            }
            if (((FullScreenIntentDecisionImpl) fullScreenIntentDecision).getHasBeenLogged()) {
                Log.wtf("VisualInterruptionDecisionProviderImpl", "FSI decision " + fullScreenIntentDecision + " has already been logged");
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                    return;
                }
                return;
            }
            ((FullScreenIntentDecisionImpl) fullScreenIntentDecision).setHasBeenLogged(true);
            if (!((FullScreenIntentDecisionImpl) fullScreenIntentDecision).getShouldLog()) {
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            } else {
                this.logger.logFullScreenIntentDecision(((FullScreenIntentDecisionImpl) fullScreenIntentDecision).getEntry(), fullScreenIntentDecision, ((FullScreenIntentDecisionImpl) fullScreenIntentDecision).isWarning());
                logEvents(((FullScreenIntentDecisionImpl) fullScreenIntentDecision).getEntry(), (Loggable) fullScreenIntentDecision);
                Unit unit = Unit.INSTANCE;
                if (zIsEnabled) {
                    TraceUtilsKt.endSlice();
                }
            }
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public VisualInterruptionDecisionProvider.Decision makeAndLogHeadsUpDecision(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#makeAndLogHeadsUpDecision");
        }
        try {
            if (!this.started) {
                throw new IllegalStateException("Check failed.");
            }
            LoggableDecision loggableDecisionMakeLoggablePeekDecision = makeLoggablePeekDecision(notificationEntry);
            logDecision(VisualInterruptionType.PEEK, notificationEntry, loggableDecisionMakeLoggablePeekDecision);
            return loggableDecisionMakeLoggablePeekDecision.getDecision();
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public VisualInterruptionDecisionProvider.FullScreenIntentDecision makeUnloggedFullScreenIntentDecision(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#makeUnloggedFullScreenIntentDecision");
        }
        try {
            if (!this.started) {
                throw new IllegalStateException("Check failed.");
            }
            return new FullScreenIntentDecisionImpl(notificationEntry, this.fullScreenIntentDecisionProvider.makeFullScreenIntentDecision(notificationEntry, makeUnloggedHeadsUpDecision(notificationEntry).getShouldInterrupt()));
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public VisualInterruptionDecisionProvider.Decision makeUnloggedHeadsUpDecision(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("VisualInterruptionDecisionProviderImpl#makeUnloggedHeadsUpDecision");
        }
        try {
            if (this.started) {
                return makeLoggablePeekDecision(notificationEntry).getDecision();
            }
            throw new IllegalStateException("Check failed.");
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void removeCondition(VisualInterruptionCondition visualInterruptionCondition) {
        visualInterruptionCondition.getClass();
        this.conditions.remove(visualInterruptionCondition);
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void removeFilter(VisualInterruptionFilter visualInterruptionFilter) {
        visualInterruptionFilter.getClass();
        this.filters.remove(visualInterruptionFilter);
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void removeLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor) {
        notificationInterruptSuppressor.getClass();
        this.legacySuppressors.remove(notificationInterruptSuppressor);
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void start() {
        if (this.started) {
            throw new IllegalStateException("Check failed.");
        }
        addCondition(new PeekDisabledSuppressor(this.globalSettings, this.headsUpManager, this.logger, this.mainHandler));
        addFilter(new PeekPackageSnoozedSuppressor(this.headsUpManager));
        addFilter(new PeekAlreadyBubbledSuppressor());
        addFilter(new PeekDndSuppressor());
        addFilter(new PeekNotImportantSuppressor());
        addCondition(new PeekDeviceNotInUseSuppressor(this.displayId, this.powerManager));
        addFilter(new PeekOldWhenSuppressor(this.systemClock));
        addFilter(new PulseEffectSuppressor());
        addFilter(new PulseLockscreenVisibilityPrivateSuppressor());
        addFilter(new PulseLowImportanceSuppressor());
        addFilter(new BubbleNotAllowedSuppressor());
        addFilter(new BubbleNoMetadataSuppressor());
        addFilter(new HunGroupAlertBehaviorSuppressor());
        addFilter(new HunJustLaunchedFsiSuppressor());
        addFilter(new AlertAppSuspendedSuppressor());
        addFilter(new AlertKeyguardVisibilitySuppressor(this.keyguardNotificationVisibilityProvider));
        if (Flags.notificationAvalancheSuppression()) {
            addFilter(new AvalancheSuppressor(this.avalancheProvider, this.systemClock, this.systemSettings));
            this.avalancheProvider.register();
        }
        this.started = true;
    }
}
