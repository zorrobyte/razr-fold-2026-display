package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: HeadsUpCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpCoordinator$mNotifCollectionListener$1 implements NotifCollectionListener {
    final /* synthetic */ HeadsUpCoordinator this$0;

    HeadsUpCoordinator$mNotifCollectionListener$1(HeadsUpCoordinator headsUpCoordinator) {
        this.this$0 = headsUpCoordinator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final HeadsUpCoordinator.PostedEntry onEntryUpdated$lambda$1(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, String str, HeadsUpCoordinator.PostedEntry postedEntry) {
        str.getClass();
        if (postedEntry == null) {
            return new HeadsUpCoordinator.PostedEntry(notificationEntry, false, true, z, z2, z3, z4);
        }
        boolean z5 = true;
        postedEntry.setWasUpdated(true);
        postedEntry.setShouldHeadsUpEver(z);
        if (!postedEntry.getShouldHeadsUpAgain() && !z2) {
            z5 = false;
        }
        postedEntry.setShouldHeadsUpAgain(z5);
        postedEntry.setHeadsUpEntry(z3);
        postedEntry.setBinding(z4);
        return postedEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryAdded(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        VisualInterruptionDecisionProvider.FullScreenIntentDecision fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision = this.this$0.mVisualInterruptionDecisionProvider.makeUnloggedFullScreenIntentDecision(notificationEntry);
        this.this$0.mVisualInterruptionDecisionProvider.logFullScreenIntentDecision(fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision);
        if (fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision.getShouldInterrupt()) {
            this.this$0.mLaunchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
        } else if (fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision.getWouldInterruptWithoutDnd()) {
            HeadsUpCoordinator headsUpCoordinator = this.this$0;
            headsUpCoordinator.addForFSIReconsideration(notificationEntry, headsUpCoordinator.mSystemClock.currentTimeMillis());
        }
        this.this$0.mPostedEntries.put(notificationEntry.getKey(), new HeadsUpCoordinator.PostedEntry(notificationEntry, true, false, this.this$0.mVisualInterruptionDecisionProvider.makeAndLogHeadsUpDecision(notificationEntry).getShouldInterrupt(), true, false, false));
        HeadsUpCoordinator headsUpCoordinator2 = this.this$0;
        headsUpCoordinator2.setUpdateTime(notificationEntry, headsUpCoordinator2.mSystemClock.currentTimeMillis());
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryCleanUp(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        this.this$0.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        notificationEntry.getClass();
        this.this$0.mPostedEntries.remove(notificationEntry.getKey());
        this.this$0.mEntriesUpdateTimes.remove(notificationEntry.getKey());
        this.this$0.cancelHeadsUpBind(notificationEntry);
        String key = notificationEntry.getKey();
        key.getClass();
        if (this.this$0.mHeadsUpManager.isHeadsUpEntry(key)) {
            boolean z = this.this$0.mRemoteInputManager.isSpinning(key) && !NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY;
            HeadsUpManager headsUpManager = this.this$0.mHeadsUpManager;
            String key2 = notificationEntry.getKey();
            key2.getClass();
            headsUpManager.removeNotification(key2, z);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onEntryUpdated(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        final boolean shouldInterrupt = this.this$0.mVisualInterruptionDecisionProvider.makeAndLogHeadsUpDecision(notificationEntry).getShouldInterrupt();
        final boolean zShouldHunAgain = this.this$0.shouldHunAgain(notificationEntry);
        HeadsUpManager headsUpManager = this.this$0.mHeadsUpManager;
        String key = notificationEntry.getKey();
        key.getClass();
        final boolean zIsHeadsUpEntry = headsUpManager.isHeadsUpEntry(key);
        final boolean zIsEntryBinding = this.this$0.isEntryBinding(notificationEntry);
        LinkedHashMap linkedHashMap = this.this$0.mPostedEntries;
        String key2 = notificationEntry.getKey();
        final Function2 function2 = new Function2() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifCollectionListener$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return HeadsUpCoordinator$mNotifCollectionListener$1.onEntryUpdated$lambda$1(notificationEntry, shouldInterrupt, zShouldHunAgain, zIsHeadsUpEntry, zIsEntryBinding, (String) obj, (HeadsUpCoordinator.PostedEntry) obj2);
            }
        };
        HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) linkedHashMap.compute(key2, new BiFunction(function2) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorKt$sam$java_util_function_BiFunction$0
            private final /* synthetic */ Function2 function;

            {
                function2.getClass();
                this.function = function2;
            }

            @Override // java.util.function.BiFunction
            public final /* synthetic */ Object apply(Object obj, Object obj2) {
                return this.function.invoke(obj, obj2);
            }
        });
        if (postedEntry != null && !postedEntry.getShouldHeadsUpEver()) {
            if (postedEntry.isHeadsUpEntry()) {
                this.this$0.mHeadsUpManager.removeNotification(postedEntry.getKey(), false);
            } else if (postedEntry.isBinding()) {
                this.this$0.cancelHeadsUpBind(postedEntry.getEntry());
            }
        }
        HeadsUpCoordinator headsUpCoordinator = this.this$0;
        headsUpCoordinator.setUpdateTime(notificationEntry, headsUpCoordinator.mSystemClock.currentTimeMillis());
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
    public void onRankingApplied() {
        NotifPipeline notifPipeline = this.this$0.mNotifPipeline;
        if (notifPipeline == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNotifPipeline");
            notifPipeline = null;
        }
        for (NotificationEntry notificationEntry : notifPipeline.getAllNotifs()) {
            if (this.this$0.isNewEnoughForRankingUpdate(notificationEntry) && !notificationEntry.hasInterrupted()) {
                if (this.this$0.isCandidateForFSIReconsideration(notificationEntry)) {
                    VisualInterruptionDecisionProvider.FullScreenIntentDecision fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision = this.this$0.mVisualInterruptionDecisionProvider.makeUnloggedFullScreenIntentDecision(notificationEntry);
                    if (fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision.getShouldInterrupt()) {
                        HeadsUpCoordinatorLogger headsUpCoordinatorLogger = this.this$0.mLogger;
                        String key = notificationEntry.getKey();
                        key.getClass();
                        headsUpCoordinatorLogger.logEntryUpdatedToFullScreen(key, fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision.getLogReason());
                        this.this$0.mVisualInterruptionDecisionProvider.logFullScreenIntentDecision(fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision);
                        this.this$0.mLaunchFullScreenIntentProvider.launchFullScreenIntent(notificationEntry);
                        this.this$0.mFSIUpdateCandidates.remove(notificationEntry.getKey());
                    } else if (!fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision.getWouldInterruptWithoutDnd()) {
                        HeadsUpCoordinatorLogger headsUpCoordinatorLogger2 = this.this$0.mLogger;
                        String key2 = notificationEntry.getKey();
                        key2.getClass();
                        headsUpCoordinatorLogger2.logEntryDisqualifiedFromFullScreen(key2, fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision.getLogReason());
                        this.this$0.mVisualInterruptionDecisionProvider.logFullScreenIntentDecision(fullScreenIntentDecisionMakeUnloggedFullScreenIntentDecision);
                        this.this$0.mFSIUpdateCandidates.remove(notificationEntry.getKey());
                    }
                }
                VisualInterruptionDecisionProvider.Decision decisionMakeUnloggedHeadsUpDecision = this.this$0.mVisualInterruptionDecisionProvider.makeUnloggedHeadsUpDecision(notificationEntry);
                boolean shouldInterrupt = decisionMakeUnloggedHeadsUpDecision.getShouldInterrupt();
                HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) this.this$0.mPostedEntries.get(notificationEntry.getKey());
                if ((postedEntry != null ? postedEntry.getShouldHeadsUpEver() : false) != shouldInterrupt) {
                    HeadsUpCoordinatorLogger headsUpCoordinatorLogger3 = this.this$0.mLogger;
                    String key3 = notificationEntry.getKey();
                    key3.getClass();
                    headsUpCoordinatorLogger3.logEntryUpdatedByRanking(key3, shouldInterrupt, decisionMakeUnloggedHeadsUpDecision.getLogReason());
                    onEntryUpdated(notificationEntry);
                }
            }
        }
    }
}
