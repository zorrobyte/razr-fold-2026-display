package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: HeadsUpCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class HeadsUpCoordinator implements Coordinator {
    public static final Companion Companion = new Companion(null);
    private final Consumer mActionPressListener;
    private NotifLifetimeExtender.OnEndLifetimeExtensionCallback mEndLifetimeExtension;
    private final ArrayMap mEntriesBindingUntil;
    private final ArrayMap mEntriesUpdateTimes;
    private final DelayableExecutor mExecutor;
    private final ArrayMap mFSIUpdateCandidates;
    private final NotifPipelineFlags mFlags;
    private final HeadsUpManager mHeadsUpManager;
    private final HeadsUpViewBinder mHeadsUpViewBinder;
    private final NodeController mIncomingHeaderController;
    private final LaunchFullScreenIntentProvider mLaunchFullScreenIntentProvider;
    private final HeadsUpCoordinator$mLifetimeExtender$1 mLifetimeExtender;
    private final HeadsUpCoordinatorLogger mLogger;
    private final HeadsUpCoordinator$mNotifCollectionListener$1 mNotifCollectionListener;
    private NotifPipeline mNotifPipeline;
    private final HeadsUpCoordinator$mNotifPromoter$1 mNotifPromoter;
    private final ArrayMap mNotifsExtendingLifetime;
    private long mNow;
    private final HeadsUpCoordinator$mOnHeadsUpChangedListener$1 mOnHeadsUpChangedListener;
    private final LinkedHashMap mPostedEntries;
    private final NotificationRemoteInputManager mRemoteInputManager;
    private final SystemClock mSystemClock;
    private final VisualInterruptionDecisionProvider mVisualInterruptionDecisionProvider;
    private final NotifSectioner sectioner;

    /* JADX INFO: compiled from: HeadsUpCoordinator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: HeadsUpCoordinator.kt */
    public final class PostedEntry {
        private final NotificationEntry entry;
        private boolean isBinding;
        private boolean isHeadsUpEntry;
        private final String key;
        private boolean shouldHeadsUpAgain;
        private boolean shouldHeadsUpEver;
        private final boolean wasAdded;
        private boolean wasUpdated;

        public PostedEntry(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            notificationEntry.getClass();
            this.entry = notificationEntry;
            this.wasAdded = z;
            this.wasUpdated = z2;
            this.shouldHeadsUpEver = z3;
            this.shouldHeadsUpAgain = z4;
            this.isHeadsUpEntry = z5;
            this.isBinding = z6;
            String key = notificationEntry.getKey();
            key.getClass();
            this.key = key;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PostedEntry)) {
                return false;
            }
            PostedEntry postedEntry = (PostedEntry) obj;
            return Intrinsics.areEqual(this.entry, postedEntry.entry) && this.wasAdded == postedEntry.wasAdded && this.wasUpdated == postedEntry.wasUpdated && this.shouldHeadsUpEver == postedEntry.shouldHeadsUpEver && this.shouldHeadsUpAgain == postedEntry.shouldHeadsUpAgain && this.isHeadsUpEntry == postedEntry.isHeadsUpEntry && this.isBinding == postedEntry.isBinding;
        }

        public final boolean getCalculateShouldBeHeadsUpNoRetract() {
            if (isHeadsUpAlready()) {
                return true;
            }
            if (this.shouldHeadsUpEver) {
                return this.wasAdded || this.shouldHeadsUpAgain;
            }
            return false;
        }

        public final boolean getCalculateShouldBeHeadsUpStrict() {
            if (this.shouldHeadsUpEver) {
                return this.wasAdded || this.shouldHeadsUpAgain || isHeadsUpAlready();
            }
            return false;
        }

        public final NotificationEntry getEntry() {
            return this.entry;
        }

        public final String getKey() {
            return this.key;
        }

        public final boolean getShouldHeadsUpAgain() {
            return this.shouldHeadsUpAgain;
        }

        public final boolean getShouldHeadsUpEver() {
            return this.shouldHeadsUpEver;
        }

        public final boolean getWasAdded() {
            return this.wasAdded;
        }

        public int hashCode() {
            return (((((((((((this.entry.hashCode() * 31) + Boolean.hashCode(this.wasAdded)) * 31) + Boolean.hashCode(this.wasUpdated)) * 31) + Boolean.hashCode(this.shouldHeadsUpEver)) * 31) + Boolean.hashCode(this.shouldHeadsUpAgain)) * 31) + Boolean.hashCode(this.isHeadsUpEntry)) * 31) + Boolean.hashCode(this.isBinding);
        }

        public final boolean isBinding() {
            return this.isBinding;
        }

        public final boolean isHeadsUpAlready() {
            return this.isHeadsUpEntry || this.isBinding;
        }

        public final boolean isHeadsUpEntry() {
            return this.isHeadsUpEntry;
        }

        public final void setBinding(boolean z) {
            this.isBinding = z;
        }

        public final void setHeadsUpEntry(boolean z) {
            this.isHeadsUpEntry = z;
        }

        public final void setShouldHeadsUpAgain(boolean z) {
            this.shouldHeadsUpAgain = z;
        }

        public final void setShouldHeadsUpEver(boolean z) {
            this.shouldHeadsUpEver = z;
        }

        public final void setWasUpdated(boolean z) {
            this.wasUpdated = z;
        }

        public String toString() {
            return "PostedEntry(entry=" + this.entry + ", wasAdded=" + this.wasAdded + ", wasUpdated=" + this.wasUpdated + ", shouldHeadsUpEver=" + this.shouldHeadsUpEver + ", shouldHeadsUpAgain=" + this.shouldHeadsUpAgain + ", isHeadsUpEntry=" + this.isHeadsUpEntry + ", isBinding=" + this.isBinding + ")";
        }
    }

    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifPromoter$1] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mOnHeadsUpChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1] */
    public HeadsUpCoordinator(HeadsUpCoordinatorLogger headsUpCoordinatorLogger, SystemClock systemClock, HeadsUpManager headsUpManager, HeadsUpViewBinder headsUpViewBinder, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, NotificationRemoteInputManager notificationRemoteInputManager, LaunchFullScreenIntentProvider launchFullScreenIntentProvider, NotifPipelineFlags notifPipelineFlags, NodeController nodeController, DelayableExecutor delayableExecutor) {
        headsUpCoordinatorLogger.getClass();
        systemClock.getClass();
        headsUpManager.getClass();
        headsUpViewBinder.getClass();
        visualInterruptionDecisionProvider.getClass();
        notificationRemoteInputManager.getClass();
        launchFullScreenIntentProvider.getClass();
        notifPipelineFlags.getClass();
        nodeController.getClass();
        delayableExecutor.getClass();
        this.mLogger = headsUpCoordinatorLogger;
        this.mSystemClock = systemClock;
        this.mHeadsUpManager = headsUpManager;
        this.mHeadsUpViewBinder = headsUpViewBinder;
        this.mVisualInterruptionDecisionProvider = visualInterruptionDecisionProvider;
        this.mRemoteInputManager = notificationRemoteInputManager;
        this.mLaunchFullScreenIntentProvider = launchFullScreenIntentProvider;
        this.mFlags = notifPipelineFlags;
        this.mIncomingHeaderController = nodeController;
        this.mExecutor = delayableExecutor;
        this.mEntriesBindingUntil = new ArrayMap();
        this.mEntriesUpdateTimes = new ArrayMap();
        this.mFSIUpdateCandidates = new ArrayMap();
        this.mNow = -1L;
        this.mPostedEntries = new LinkedHashMap();
        this.mNotifsExtendingLifetime = new ArrayMap();
        this.mNotifCollectionListener = new HeadsUpCoordinator$mNotifCollectionListener$1(this);
        this.mActionPressListener = new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1
            @Override // java.util.function.Consumer
            public final void accept(final NotificationEntry notificationEntry) {
                HeadsUpManager headsUpManager2 = this.this$0.mHeadsUpManager;
                notificationEntry.getClass();
                headsUpManager2.setUserActionMayIndirectlyRemove(notificationEntry);
                DelayableExecutor delayableExecutor2 = this.this$0.mExecutor;
                final HeadsUpCoordinator headsUpCoordinator = this.this$0;
                delayableExecutor2.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mActionPressListener$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        HeadsUpCoordinator headsUpCoordinator2 = headsUpCoordinator;
                        NotificationEntry notificationEntry2 = notificationEntry;
                        notificationEntry2.getClass();
                        headsUpCoordinator2.endNotifLifetimeExtensionIfExtended(notificationEntry2);
                    }
                });
            }
        };
        this.mLifetimeExtender = new NotifLifetimeExtender() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public void cancelLifetimeExtension(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                Runnable runnable = (Runnable) this.this$0.mNotifsExtendingLifetime.remove(notificationEntry);
                if (runnable != null) {
                    runnable.run();
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public String getName() {
                return "HeadsUpCoordinator";
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public boolean maybeExtendLifetime(final NotificationEntry notificationEntry, int i) {
                notificationEntry.getClass();
                HeadsUpManager headsUpManager2 = this.this$0.mHeadsUpManager;
                String key = notificationEntry.getKey();
                key.getClass();
                if (headsUpManager2.canRemoveImmediately(key)) {
                    return false;
                }
                if (!this.this$0.isSticky(notificationEntry)) {
                    DelayableExecutor delayableExecutor2 = this.this$0.mExecutor;
                    final HeadsUpCoordinator headsUpCoordinator = this.this$0;
                    delayableExecutor2.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            HeadsUpManager headsUpManager3 = headsUpCoordinator.mHeadsUpManager;
                            String key2 = notificationEntry.getKey();
                            key2.getClass();
                            headsUpManager3.removeNotification(key2, false);
                        }
                    });
                    this.this$0.mNotifsExtendingLifetime.put(notificationEntry, null);
                    return true;
                }
                long earliestRemovalTime = this.this$0.mHeadsUpManager.getEarliestRemovalTime(notificationEntry.getKey());
                ArrayMap arrayMap = this.this$0.mNotifsExtendingLifetime;
                DelayableExecutor delayableExecutor3 = this.this$0.mExecutor;
                final HeadsUpCoordinator headsUpCoordinator2 = this.this$0;
                arrayMap.put(notificationEntry, delayableExecutor3.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mLifetimeExtender$1$maybeExtendLifetime$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        HeadsUpManager headsUpManager3 = headsUpCoordinator2.mHeadsUpManager;
                        String key2 = notificationEntry.getKey();
                        key2.getClass();
                        headsUpManager3.removeNotification(key2, true);
                    }
                }, earliestRemovalTime));
                return true;
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
            public void setCallback(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback) {
                onEndLifetimeExtensionCallback.getClass();
                this.this$0.mEndLifetimeExtension = onEndLifetimeExtensionCallback;
            }
        };
        this.mNotifPromoter = new NotifPromoter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mNotifPromoter$1
            {
                super("HeadsUpCoordinator");
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter
            public boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                return this.this$0.isGoingToShowHunNoRetract(notificationEntry);
            }
        };
        this.sectioner = new NotifSectioner() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$sectioner$1
            {
                super("HeadsUp", 2);
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NotifComparator getComparator() {
                final HeadsUpCoordinator headsUpCoordinator = this.this$0;
                return new NotifComparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$sectioner$1$getComparator$1
                    {
                        super("HeadsUp");
                    }

                    @Override // java.util.Comparator
                    public int compare(ListEntry listEntry, ListEntry listEntry2) {
                        listEntry.getClass();
                        listEntry2.getClass();
                        return headsUpCoordinator.mHeadsUpManager.compare(listEntry.getRepresentativeEntry(), listEntry2.getRepresentativeEntry());
                    }
                };
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return null;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(ListEntry listEntry) {
                listEntry.getClass();
                return this.this$0.isGoingToShowHunNoRetract(listEntry);
            }
        };
        this.mOnHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$mOnHeadsUpChangedListener$1
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
                notificationEntry.getClass();
                if (z) {
                    return;
                }
                invalidateList("headsUpEnded: " + NotificationUtilsKt.getLogKey(notificationEntry));
                this.this$0.mHeadsUpViewBinder.unbindHeadsUpView(notificationEntry);
                this.this$0.endNotifLifetimeExtensionIfExtended(notificationEntry);
            }
        };
    }

    private final void bindForAsyncHeadsUp(PostedEntry postedEntry) {
        this.mEntriesBindingUntil.put(postedEntry.getKey(), Long.valueOf(this.mNow + 1000));
        this.mHeadsUpViewBinder.bindHeadsUpView(postedEntry.getEntry(), new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.bindForAsyncHeadsUp.1
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                HeadsUpCoordinator.this.onHeadsUpViewBound(notificationEntry);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void cancelHeadsUpBind(NotificationEntry notificationEntry) {
        this.mEntriesBindingUntil.remove(notificationEntry.getKey());
        this.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
    }

    private final void cleanUpEntryTimes() {
        long jCurrentTimeMillis = this.mSystemClock.currentTimeMillis() - 2000;
        ArraySet arraySet = new ArraySet();
        for (Map.Entry entry : this.mEntriesUpdateTimes.entrySet()) {
            String str = (String) entry.getKey();
            Long l = (Long) entry.getValue();
            if (l == null || jCurrentTimeMillis > l.longValue()) {
                arraySet.add(str);
            }
        }
        this.mEntriesUpdateTimes.removeAll(arraySet);
        ArraySet arraySet2 = new ArraySet();
        for (Map.Entry entry2 : this.mFSIUpdateCandidates.entrySet()) {
            String str2 = (String) entry2.getKey();
            Long l2 = (Long) entry2.getValue();
            if (l2 == null || jCurrentTimeMillis > l2.longValue()) {
                arraySet2.add(str2);
            }
        }
        this.mFSIUpdateCandidates.removeAll(arraySet2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void endNotifLifetimeExtensionIfExtended(NotificationEntry notificationEntry) {
        if (this.mNotifsExtendingLifetime.containsKey(notificationEntry)) {
            Runnable runnable = (Runnable) this.mNotifsExtendingLifetime.remove(notificationEntry);
            if (runnable != null) {
                runnable.run();
            }
            NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback = this.mEndLifetimeExtension;
            if (onEndLifetimeExtensionCallback != null) {
                onEndLifetimeExtensionCallback.onEndLifetimeExtension(this.mLifetimeExtender, notificationEntry);
            }
        }
    }

    private final NotificationEntry findBestTransferChild(List list, final Function1 function1) {
        return (NotificationEntry) SequencesKt.firstOrNull(SequencesKt.sortedWith(SequencesKt.filter(SequencesKt.filter(CollectionsKt.asSequence(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(HeadsUpCoordinator.findBestTransferChild$lambda$20((NotificationEntry) obj));
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(HeadsUpCoordinator.findBestTransferChild$lambda$21(function1, (NotificationEntry) obj));
            }
        }), ComparisonsKt.compareBy(new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinator.findBestTransferChild$lambda$22(this.f$0, (NotificationEntry) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinator.findBestTransferChild$lambda$23((NotificationEntry) obj);
            }
        })));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean findBestTransferChild$lambda$20(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return !notificationEntry.getSbn().getNotification().isGroupSummary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean findBestTransferChild$lambda$21(Function1 function1, NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        String key = notificationEntry.getKey();
        key.getClass();
        return function1.invoke(key) != GroupLocation.Detached;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable findBestTransferChild$lambda$22(HeadsUpCoordinator headsUpCoordinator, NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return Boolean.valueOf(!headsUpCoordinator.mPostedEntries.containsKey(notificationEntry.getKey()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparable findBestTransferChild$lambda$23(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return Long.valueOf(-notificationEntry.getSbn().getNotification().when);
    }

    private final NotificationEntry findHeadsUpOverride(List list, Function1 function1) {
        PostedEntry postedEntry = (PostedEntry) SequencesKt.firstOrNull(SequencesKt.sortedWith(SequencesKt.filter(CollectionsKt.asSequence(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(HeadsUpCoordinator.findHeadsUpOverride$lambda$16((HeadsUpCoordinator.PostedEntry) obj));
            }
        }), new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$findHeadsUpOverride$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt.compareValues(Long.valueOf(-((HeadsUpCoordinator.PostedEntry) obj).getEntry().getSbn().getNotification().when), Long.valueOf(-((HeadsUpCoordinator.PostedEntry) obj2).getEntry().getSbn().getNotification().when));
            }
        }));
        if (postedEntry != null) {
            NotificationEntry entry = postedEntry.getEntry();
            String key = entry.getKey();
            key.getClass();
            if (function1.invoke(key) == GroupLocation.Isolated && entry.getSbn().getNotification().getGroupAlertBehavior() == 1) {
                return entry;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean findHeadsUpOverride$lambda$16(PostedEntry postedEntry) {
        postedEntry.getClass();
        return !postedEntry.getEntry().getSbn().getNotification().isGroupSummary();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map getGroupLocationsByKey(List list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof NotificationEntry) {
                linkedHashMap.put(((NotificationEntry) listEntry).getKey(), GroupLocation.Isolated);
            } else {
                if (!(listEntry instanceof GroupEntry)) {
                    throw new IllegalStateException(("unhandled type " + listEntry).toString());
                }
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry summary = groupEntry.getSummary();
                if (summary != null) {
                    linkedHashMap.put(summary.getKey(), GroupLocation.Summary);
                }
                List children = groupEntry.getChildren();
                children.getClass();
                Iterator it2 = children.iterator();
                while (it2.hasNext()) {
                    linkedHashMap.put(((NotificationEntry) it2.next()).getKey(), GroupLocation.Child);
                }
            }
        }
        return linkedHashMap;
    }

    private final void handlePostedEntry(PostedEntry postedEntry, HunMutator hunMutator, String str) {
        this.mLogger.logPostedEntryWillEvaluate(postedEntry, str);
        if (postedEntry.getWasAdded()) {
            if (postedEntry.getShouldHeadsUpEver()) {
                bindForAsyncHeadsUp(postedEntry);
                return;
            }
            return;
        }
        if (!postedEntry.isHeadsUpAlready()) {
            if (postedEntry.getShouldHeadsUpEver() && postedEntry.getShouldHeadsUpAgain()) {
                bindForAsyncHeadsUp(postedEntry);
                return;
            }
            return;
        }
        if (postedEntry.getShouldHeadsUpEver()) {
            if (postedEntry.isHeadsUpEntry()) {
                hunMutator.updateNotification(postedEntry.getEntry(), postedEntry.getShouldHeadsUpAgain());
            }
        } else if (postedEntry.isHeadsUpEntry()) {
            hunMutator.removeNotification(postedEntry.getKey(), false);
        } else {
            cancelHeadsUpBind(postedEntry.getEntry());
        }
    }

    private final boolean isAttemptingToShowHun(ListEntry listEntry) {
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        String key = listEntry.getKey();
        key.getClass();
        return headsUpManager.isHeadsUpEntry(key) || isEntryBinding(listEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isCandidateForFSIReconsideration(NotificationEntry notificationEntry) {
        Long l = (Long) this.mFSIUpdateCandidates.get(notificationEntry.getKey());
        if (l != null) {
            if (this.mSystemClock.currentTimeMillis() - l.longValue() <= 2000) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isEntryBinding(ListEntry listEntry) {
        Long l = (Long) this.mEntriesBindingUntil.get(listEntry.getKey());
        return l != null && l.longValue() >= this.mNow;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isGoingToShowHunNoRetract(ListEntry listEntry) {
        PostedEntry postedEntry = (PostedEntry) this.mPostedEntries.get(listEntry.getKey());
        return postedEntry != null ? postedEntry.getCalculateShouldBeHeadsUpNoRetract() : isAttemptingToShowHun(listEntry);
    }

    private final boolean isGoingToShowHunStrict(ListEntry listEntry) {
        PostedEntry postedEntry = (PostedEntry) this.mPostedEntries.get(listEntry.getKey());
        return postedEntry != null ? postedEntry.getCalculateShouldBeHeadsUpStrict() : isAttemptingToShowHun(listEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isNewEnoughForRankingUpdate(NotificationEntry notificationEntry) {
        Long l;
        if (this.mEntriesUpdateTimes.containsKey(notificationEntry.getKey()) && (l = (Long) this.mEntriesUpdateTimes.get(notificationEntry.getKey())) != null) {
            if (this.mSystemClock.currentTimeMillis() - l.longValue() <= 2000) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isSticky(NotificationEntry notificationEntry) {
        return this.mHeadsUpManager.isSticky(notificationEntry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onBeforeFinalizeFilter$lambda$15(final HeadsUpCoordinator headsUpCoordinator, final List list, HunMutator hunMutator) {
        Object next;
        boolean z;
        boolean z2;
        hunMutator.getClass();
        if (headsUpCoordinator.mPostedEntries.isEmpty()) {
            return Unit.INSTANCE;
        }
        Collection collectionValues = headsUpCoordinator.mPostedEntries.values();
        collectionValues.getClass();
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : collectionValues) {
            String groupKey = ((PostedEntry) obj).getEntry().getSbn().getGroupKey();
            Object arrayList = linkedHashMap.get(groupKey);
            if (arrayList == null) {
                arrayList = new ArrayList();
                linkedHashMap.put(groupKey, arrayList);
            }
            ((List) arrayList).add(obj);
        }
        NotifPipeline notifPipeline = headsUpCoordinator.mNotifPipeline;
        if (notifPipeline == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNotifPipeline");
            notifPipeline = null;
        }
        Sequence sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(notifPipeline.getAllNotifs()), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return Boolean.valueOf(HeadsUpCoordinator.onBeforeFinalizeFilter$lambda$15$lambda$3(linkedHashMap, (NotificationEntry) obj2));
            }
        });
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj2 : sequenceFilter) {
            String groupKey2 = ((NotificationEntry) obj2).getSbn().getGroupKey();
            Object arrayList2 = linkedHashMap2.get(groupKey2);
            if (arrayList2 == null) {
                arrayList2 = new ArrayList();
                linkedHashMap2.put(groupKey2, arrayList2);
            }
            ((List) arrayList2).add(obj2);
        }
        Lazy lazy = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return this.f$0.getGroupLocationsByKey(list);
            }
        });
        headsUpCoordinator.mLogger.logEvaluatingGroups(linkedHashMap.size());
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String str = (String) entry.getKey();
            List<PostedEntry> list2 = (List) entry.getValue();
            List listEmptyList = (List) linkedHashMap2.get(str);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            Iterator it = listEmptyList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (((NotificationEntry) next).getSbn().getNotification().isGroupSummary()) {
                    break;
                }
            }
            final NotificationEntry notificationEntry = (NotificationEntry) next;
            HeadsUpCoordinatorLogger headsUpCoordinatorLogger = headsUpCoordinator.mLogger;
            str.getClass();
            headsUpCoordinatorLogger.logEvaluatingGroup(str, list2.size(), listEmptyList.size());
            if (notificationEntry == null) {
                for (PostedEntry postedEntry : list2) {
                    postedEntry.getClass();
                    headsUpCoordinator.handlePostedEntry(postedEntry, hunMutator, "logical-summary-missing");
                }
            } else if (headsUpCoordinator.isGoingToShowHunStrict(notificationEntry)) {
                NotificationEntry notificationEntryFindHeadsUpOverride = headsUpCoordinator.findHeadsUpOverride(list2, new HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$3(onBeforeFinalizeFilter$lambda$15$lambda$6(lazy)));
                String str2 = notificationEntryFindHeadsUpOverride != null ? "headsUpOverride" : "undefined";
                boolean zContainsKey = onBeforeFinalizeFilter$lambda$15$lambda$6(lazy).containsKey(notificationEntry.getKey());
                if (!zContainsKey && notificationEntryFindHeadsUpOverride == null && (notificationEntryFindHeadsUpOverride = headsUpCoordinator.findBestTransferChild(listEmptyList, new HeadsUpCoordinator$onBeforeFinalizeFilter$1$1$4(onBeforeFinalizeFilter$lambda$15$lambda$6(lazy)))) != null) {
                    str2 = "bestChild";
                }
                if (notificationEntryFindHeadsUpOverride == null) {
                    for (PostedEntry postedEntry2 : list2) {
                        postedEntry2.getClass();
                        headsUpCoordinator.handlePostedEntry(postedEntry2, hunMutator, "no-transfer-target");
                    }
                } else {
                    PostedEntry postedEntry3 = (PostedEntry) headsUpCoordinator.mPostedEntries.get(notificationEntry.getKey());
                    notificationEntry.setInterruption();
                    HeadsUpCoordinatorLogger headsUpCoordinatorLogger2 = headsUpCoordinator.mLogger;
                    String key = notificationEntry.getKey();
                    key.getClass();
                    String key2 = notificationEntryFindHeadsUpOverride.getKey();
                    key2.getClass();
                    headsUpCoordinatorLogger2.logSummaryMarkedInterrupted(key, key2);
                    if (zContainsKey) {
                        z = false;
                        if (postedEntry3 != null) {
                            headsUpCoordinator.mLogger.logPostedEntryWillNotEvaluate(postedEntry3, "attached-summary-transferred");
                        }
                    } else {
                        if (postedEntry3 != null) {
                            postedEntry3.setShouldHeadsUpEver(false);
                            z = false;
                        } else {
                            HeadsUpManager headsUpManager = headsUpCoordinator.mHeadsUpManager;
                            String key3 = notificationEntry.getKey();
                            key3.getClass();
                            z = false;
                            postedEntry3 = new PostedEntry(notificationEntry, false, false, false, false, headsUpManager.isHeadsUpEntry(key3), headsUpCoordinator.isEntryBinding(notificationEntry));
                        }
                        headsUpCoordinator.handlePostedEntry(postedEntry3, hunMutator, "detached-summary-remove-heads-up");
                    }
                    while (true) {
                        z2 = z;
                        for (PostedEntry postedEntry4 : SequencesKt.filter(CollectionsKt.asSequence(list2), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda2
                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj3) {
                                return Boolean.valueOf(HeadsUpCoordinator.onBeforeFinalizeFilter$lambda$15$lambda$14$lambda$12(notificationEntry, (HeadsUpCoordinator.PostedEntry) obj3));
                            }
                        })) {
                            if (Intrinsics.areEqual(notificationEntryFindHeadsUpOverride.getKey(), postedEntry4.getKey())) {
                                break;
                            }
                            headsUpCoordinator.handlePostedEntry(postedEntry4, hunMutator, "child-heads-up-non-target");
                        }
                        z = true;
                        postedEntry4.setShouldHeadsUpEver(true);
                        postedEntry4.setShouldHeadsUpAgain(true);
                        headsUpCoordinator.handlePostedEntry(postedEntry4, hunMutator, "child-heads-up-transfer-target-" + ((Object) str2));
                    }
                    if (!z2) {
                        HeadsUpManager headsUpManager2 = headsUpCoordinator.mHeadsUpManager;
                        String key4 = notificationEntryFindHeadsUpOverride.getKey();
                        key4.getClass();
                        headsUpCoordinator.handlePostedEntry(new PostedEntry(notificationEntryFindHeadsUpOverride, false, false, true, true, headsUpManager2.isHeadsUpEntry(key4), headsUpCoordinator.isEntryBinding(notificationEntryFindHeadsUpOverride)), hunMutator, "non-posted-child-heads-up-transfer-target-" + ((Object) str2));
                    }
                }
            } else {
                for (PostedEntry postedEntry5 : list2) {
                    postedEntry5.getClass();
                    headsUpCoordinator.handlePostedEntry(postedEntry5, hunMutator, "logical-summary-not-heads-up");
                }
            }
        }
        headsUpCoordinator.mPostedEntries.clear();
        headsUpCoordinator.cleanUpEntryTimes();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBeforeFinalizeFilter$lambda$15$lambda$14$lambda$12(NotificationEntry notificationEntry, PostedEntry postedEntry) {
        return !Intrinsics.areEqual(postedEntry.getKey(), notificationEntry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBeforeFinalizeFilter$lambda$15$lambda$3(Map map, NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return map.containsKey(notificationEntry.getSbn().getGroupKey());
    }

    private static final Map onBeforeFinalizeFilter$lambda$15$lambda$6(Lazy lazy) {
        return (Map) lazy.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onBeforeTransformGroups$lambda$1(HeadsUpCoordinator headsUpCoordinator, HunMutator hunMutator) {
        hunMutator.getClass();
        Collection collectionValues = headsUpCoordinator.mPostedEntries.values();
        collectionValues.getClass();
        for (PostedEntry postedEntry : CollectionsKt.toList(collectionValues)) {
            if (!postedEntry.getEntry().getSbn().isGroup()) {
                headsUpCoordinator.handlePostedEntry(postedEntry, hunMutator, "non-group");
                headsUpCoordinator.mPostedEntries.remove(postedEntry.getKey());
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onHeadsUpViewBound(NotificationEntry notificationEntry) {
        this.mHeadsUpManager.showNotification(notificationEntry);
        this.mEntriesBindingUntil.remove(notificationEntry.getKey());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldHunAgain(NotificationEntry notificationEntry) {
        return !notificationEntry.hasInterrupted() || (notificationEntry.getSbn().getNotification().flags & 8) == 0;
    }

    public final void addForFSIReconsideration(NotificationEntry notificationEntry, long j) {
        notificationEntry.getClass();
        this.mFSIUpdateCandidates.put(notificationEntry.getKey(), Long.valueOf(j));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        this.mNotifPipeline = notifPipeline;
        this.mHeadsUpManager.addListener(this.mOnHeadsUpChangedListener);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeTransformGroupsListener(new OnBeforeTransformGroupsListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener
            public final void onBeforeTransformGroups(List list) {
                list.getClass();
                HeadsUpCoordinator.this.onBeforeTransformGroups(list);
            }
        });
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator.attach.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                list.getClass();
                HeadsUpCoordinator.this.onBeforeFinalizeFilter(list);
            }
        });
        this.mRemoteInputManager.addActionPressListener(this.mActionPressListener);
    }

    public final void onBeforeFinalizeFilter(final List list) {
        list.getClass();
        HeadsUpCoordinatorKt.modifyHuns(this.mHeadsUpManager, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinator.onBeforeFinalizeFilter$lambda$15(this.f$0, list, (HunMutator) obj);
            }
        });
    }

    public final void onBeforeTransformGroups(List list) {
        list.getClass();
        this.mNow = this.mSystemClock.currentTimeMillis();
        if (this.mPostedEntries.isEmpty()) {
            return;
        }
        HeadsUpCoordinatorKt.modifyHuns(this.mHeadsUpManager, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return HeadsUpCoordinator.onBeforeTransformGroups$lambda$1(this.f$0, (HunMutator) obj);
            }
        });
    }

    public final void setUpdateTime(NotificationEntry notificationEntry, long j) {
        notificationEntry.getClass();
        this.mEntriesUpdateTimes.put(notificationEntry.getKey(), Long.valueOf(j));
    }
}
