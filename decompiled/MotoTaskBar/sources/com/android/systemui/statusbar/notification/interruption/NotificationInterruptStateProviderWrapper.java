package com.android.systemui.statusbar.notification.interruption;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.Flags;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import kotlin.Unit;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotificationInterruptStateProviderWrapper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationInterruptStateProviderWrapper implements VisualInterruptionDecisionProvider {
    private final NotificationInterruptStateProvider wrapped;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: NotificationInterruptStateProviderWrapper.kt */
    public final class DecisionImpl implements VisualInterruptionDecisionProvider.Decision {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ DecisionImpl[] $VALUES;
        public static final Companion Companion;
        public static final DecisionImpl SHOULD_INTERRUPT = new DecisionImpl("SHOULD_INTERRUPT", 0, true);
        public static final DecisionImpl SHOULD_NOT_INTERRUPT = new DecisionImpl("SHOULD_NOT_INTERRUPT", 1, false);
        private final String logReason = "unknown";
        private final boolean shouldInterrupt;

        /* JADX INFO: compiled from: NotificationInterruptStateProviderWrapper.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            public final DecisionImpl of(boolean z) {
                return z ? DecisionImpl.SHOULD_INTERRUPT : DecisionImpl.SHOULD_NOT_INTERRUPT;
            }
        }

        private static final /* synthetic */ DecisionImpl[] $values() {
            return new DecisionImpl[]{SHOULD_INTERRUPT, SHOULD_NOT_INTERRUPT};
        }

        static {
            DecisionImpl[] decisionImplArr$values = $values();
            $VALUES = decisionImplArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(decisionImplArr$values);
            Companion = new Companion(null);
        }

        private DecisionImpl(String str, int i, boolean z) {
            this.shouldInterrupt = z;
        }

        public static DecisionImpl valueOf(String str) {
            return (DecisionImpl) Enum.valueOf(DecisionImpl.class, str);
        }

        public static DecisionImpl[] values() {
            return (DecisionImpl[]) $VALUES.clone();
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

    /* JADX INFO: compiled from: NotificationInterruptStateProviderWrapper.kt */
    public final class FullScreenIntentDecisionImpl implements VisualInterruptionDecisionProvider.FullScreenIntentDecision {
        private final String logReason;
        private final NotificationInterruptStateProvider.FullScreenIntentDecision originalDecision;
        private final NotificationEntry originalEntry;
        private final boolean shouldInterrupt;
        private final boolean wouldInterruptWithoutDnd;

        public FullScreenIntentDecisionImpl(NotificationEntry notificationEntry, NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision) {
            notificationEntry.getClass();
            fullScreenIntentDecision.getClass();
            this.originalEntry = notificationEntry;
            this.originalDecision = fullScreenIntentDecision;
            this.shouldInterrupt = fullScreenIntentDecision.shouldLaunch;
            this.wouldInterruptWithoutDnd = fullScreenIntentDecision == NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_ONLY_BY_DND;
            this.logReason = fullScreenIntentDecision.name();
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public String getLogReason() {
            return this.logReason;
        }

        public final NotificationInterruptStateProvider.FullScreenIntentDecision getOriginalDecision() {
            return this.originalDecision;
        }

        public final NotificationEntry getOriginalEntry() {
            return this.originalEntry;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public boolean getShouldInterrupt() {
            return this.shouldInterrupt;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.FullScreenIntentDecision
        public boolean getWouldInterruptWithoutDnd() {
            return this.wouldInterruptWithoutDnd;
        }
    }

    public NotificationInterruptStateProviderWrapper(NotificationInterruptStateProvider notificationInterruptStateProvider) {
        notificationInterruptStateProvider.getClass();
        this.wrapped = notificationInterruptStateProvider;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        VisualInterruptionRefactor visualInterruptionRefactor = VisualInterruptionRefactor.INSTANCE;
        if (Flags.visualInterruptionsRefactor()) {
            throw new IllegalStateException(("Legacy code path not supported when " + ((Object) "VisualInterruptionRefactor") + " is enabled.").toString());
        }
    }

    private final void notValidInLegacyMode() {
        RefactorFlagUtils.INSTANCE.assertOnEngBuild("This method is only implemented in VisualInterruptionDecisionProviderImpl, and so should only be called when FLAG_VISUAL_INTERRUPTIONS_REFACTOR is enabled.");
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void logFullScreenIntentDecision(VisualInterruptionDecisionProvider.FullScreenIntentDecision fullScreenIntentDecision) {
        fullScreenIntentDecision.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#logFullScreenIntentDecision");
        }
        try {
            FullScreenIntentDecisionImpl fullScreenIntentDecisionImpl = (FullScreenIntentDecisionImpl) fullScreenIntentDecision;
            this.wrapped.logFullScreenIntentDecision(fullScreenIntentDecisionImpl.getOriginalEntry(), fullScreenIntentDecisionImpl.getOriginalDecision());
            Unit unit = Unit.INSTANCE;
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
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#makeAndLogHeadsUpDecision");
        }
        try {
            return DecisionImpl.Companion.of(this.wrapped.checkHeadsUp(notificationEntry, true));
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#makeUnloggedFullScreenIntentDecision");
        }
        try {
            NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision = this.wrapped.getFullScreenIntentDecision(notificationEntry);
            fullScreenIntentDecision.getClass();
            return new FullScreenIntentDecisionImpl(notificationEntry, fullScreenIntentDecision);
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
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#makeUnloggedHeadsUpDecision");
        }
        try {
            return DecisionImpl.Companion.of(this.wrapped.checkHeadsUp(notificationEntry, false));
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void removeCondition(VisualInterruptionCondition visualInterruptionCondition) {
        visualInterruptionCondition.getClass();
        notValidInLegacyMode();
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void removeFilter(VisualInterruptionFilter visualInterruptionFilter) {
        visualInterruptionFilter.getClass();
        notValidInLegacyMode();
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public void removeLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor) {
        notificationInterruptSuppressor.getClass();
        this.wrapped.removeSuppressor(notificationInterruptSuppressor);
    }
}
