package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;

/* JADX INFO: compiled from: VisualInterruptionDecisionProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public interface VisualInterruptionDecisionProvider extends Dumpable {

    /* JADX INFO: compiled from: VisualInterruptionDecisionProvider.kt */
    public interface Decision {
        String getLogReason();

        boolean getShouldInterrupt();
    }

    /* JADX INFO: compiled from: VisualInterruptionDecisionProvider.kt */
    public interface FullScreenIntentDecision extends Decision {
        boolean getWouldInterruptWithoutDnd();
    }

    void logFullScreenIntentDecision(FullScreenIntentDecision fullScreenIntentDecision);

    Decision makeAndLogHeadsUpDecision(NotificationEntry notificationEntry);

    FullScreenIntentDecision makeUnloggedFullScreenIntentDecision(NotificationEntry notificationEntry);

    Decision makeUnloggedHeadsUpDecision(NotificationEntry notificationEntry);

    void removeCondition(VisualInterruptionCondition visualInterruptionCondition);

    void removeFilter(VisualInterruptionFilter visualInterruptionFilter);

    void removeLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor);

    default void start() {
    }
}
