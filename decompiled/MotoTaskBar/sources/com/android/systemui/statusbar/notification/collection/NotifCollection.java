package com.android.systemui.statusbar.notification.collection;

import android.app.NotificationChannel;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coalescer.CoalescedEvent;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.statusbar.notification.collection.notifcollection.BindEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.ChannelChangedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.CleanUpEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.CollectionReadyForBuildListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryAddedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryRemovedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryUpdatedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.InitEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLoggerKt;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import com.android.systemui.statusbar.notification.collection.notifcollection.RankingAppliedEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.RankingUpdatedEvent;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider;
import com.android.systemui.util.Assert;
import com.android.systemui.util.NamedListenerSet;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import kotlin.jvm.functions.Function0;

/* JADX INFO: loaded from: classes.dex */
public class NotifCollection implements Dumpable {
    private static final long INITIALIZATION_FORGIVENESS_WINDOW = TimeUnit.SECONDS.toMillis(5);
    private boolean mAmDispatchingToOtherCode;
    private boolean mAttached;
    private final Executor mBgExecutor;
    private CollectionReadyForBuildListener mBuildListener;
    private final SystemClock mClock;
    private final List mDismissInterceptors;
    private final NotificationDismissibilityProvider mDismissibilityProvider;
    private final DumpManager mDumpManager;
    private final LogBufferEulogizer mEulogizer;
    private Queue mEventQueue;
    private final HashMap mFutureDismissals;
    private final NotifCollectionInconsistencyTracker mInconsistencyTracker;
    private long mInitializedTimestamp;
    private final List mLifetimeExtenders;
    private final NotifCollectionLogger mLogger;
    private final Handler mMainHandler;
    private final NamedListenerSet mNotifCollectionListeners;
    private final GroupCoalescer.BatchableNotificationHandler mNotifHandler;
    private final NotifPipelineFlags mNotifPipelineFlags;
    private final Map mNotificationSet;
    private final Collection mReadOnlyNotificationSet;
    private final Runnable mRebuildListRunnable;
    private final IStatusBarService mStatusBarService;

    public interface DismissedByUserStatsCreator {
        DismissedByUserStats createDismissedByUserStats(NotificationEntry notificationEntry);
    }

    public class FutureDismissal implements Runnable {
        private boolean mDidRun;
        private boolean mDidSystemServerCancel;
        private final NotificationEntry mEntry;
        private final String mLabel;
        private final DismissedByUserStatsCreator mStatsCreator;
        private final NotificationEntry mSummaryToDismiss;

        private FutureDismissal(NotificationEntry notificationEntry, int i, DismissedByUserStatsCreator dismissedByUserStatsCreator) {
            this.mEntry = notificationEntry;
            this.mStatsCreator = dismissedByUserStatsCreator;
            NotificationEntry notificationEntryFetchSummaryToDismiss = fetchSummaryToDismiss(notificationEntry);
            this.mSummaryToDismiss = notificationEntryFetchSummaryToDismiss;
            this.mLabel = "<FutureDismissal@" + Integer.toHexString(hashCode()) + " entry=" + NotificationUtils.logKey(notificationEntry) + " reason=" + NotifCollectionLoggerKt.cancellationReasonDebugString(i) + " summary=" + NotificationUtils.logKey(notificationEntryFetchSummaryToDismiss) + ">";
        }

        private NotificationEntry fetchSummaryToDismiss(NotificationEntry notificationEntry) {
            if (!NotifCollection.this.isOnlyChildInGroup(notificationEntry)) {
                return null;
            }
            NotificationEntry groupSummary = NotifCollection.this.getGroupSummary(notificationEntry.getSbn().getGroupKey());
            if (groupSummary == null || !NotifCollection.this.isDismissable(groupSummary)) {
                return null;
            }
            return groupSummary;
        }

        private void onUiCancel() {
            NotifCollection.this.mFutureDismissals.remove(this.mEntry.getKey());
            NotificationEntry entry = NotifCollection.this.getEntry(this.mEntry.getKey());
            DismissedByUserStats dismissedByUserStatsCreateDismissedByUserStats = this.mStatsCreator.createDismissedByUserStats(this.mEntry);
            NotificationEntry notificationEntry = this.mSummaryToDismiss;
            if (notificationEntry != null) {
                NotificationEntry entry2 = NotifCollection.this.getEntry(notificationEntry.getKey());
                if (entry2 == this.mSummaryToDismiss) {
                    NotifCollection.this.mLogger.logFutureDismissalDismissing(this, "summary");
                    NotifCollection notifCollection = NotifCollection.this;
                    NotificationEntry notificationEntry2 = this.mSummaryToDismiss;
                    notifCollection.dismissNotification(notificationEntry2, this.mStatsCreator.createDismissedByUserStats(notificationEntry2));
                } else {
                    NotifCollection.this.mLogger.logFutureDismissalMismatchedEntry(this, "summary", entry2);
                }
            }
            if (this.mDidSystemServerCancel) {
                NotifCollection.this.mLogger.logFutureDismissalAlreadyCancelledByServer(this);
            } else if (entry != this.mEntry) {
                NotifCollection.this.mLogger.logFutureDismissalMismatchedEntry(this, "entry", entry);
            } else {
                NotifCollection.this.mLogger.logFutureDismissalDismissing(this, "entry");
                NotifCollection.this.dismissNotification(this.mEntry, dismissedByUserStatsCreateDismissedByUserStats);
            }
        }

        public String getLabel() {
            return this.mLabel;
        }

        public void onSystemServerCancel(int i) {
            Assert.isMainThread();
            if (this.mDidSystemServerCancel) {
                NotifCollection.this.mLogger.logFutureDismissalDoubleCancelledByServer(this);
            } else {
                NotifCollection.this.mLogger.logFutureDismissalGotSystemServerCancel(this, i);
                this.mDidSystemServerCancel = true;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            Assert.isMainThread();
            if (this.mDidRun) {
                NotifCollection.this.mLogger.logFutureDismissalDoubleRun(this);
            } else {
                this.mDidRun = true;
                onUiCancel();
            }
        }
    }

    public static /* synthetic */ boolean $r8$lambda$Wfrm0rdZGRocFrUQ4Md4rzICOXg(NotificationEntry notificationEntry) {
        return !notificationEntry.getSbn().getNotification().isGroupSummary();
    }

    public NotifCollection(IStatusBarService iStatusBarService, SystemClock systemClock, NotifPipelineFlags notifPipelineFlags, NotifCollectionLogger notifCollectionLogger, Handler handler, Executor executor, LogBufferEulogizer logBufferEulogizer, DumpManager dumpManager, NotificationDismissibilityProvider notificationDismissibilityProvider) {
        ArrayMap arrayMap = new ArrayMap();
        this.mNotificationSet = arrayMap;
        this.mReadOnlyNotificationSet = Collections.unmodifiableCollection(arrayMap.values());
        this.mFutureDismissals = new HashMap();
        this.mNotifCollectionListeners = new NamedListenerSet();
        this.mLifetimeExtenders = new ArrayList();
        this.mDismissInterceptors = new ArrayList();
        this.mEventQueue = new ArrayDeque();
        this.mRebuildListRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        };
        this.mAttached = false;
        this.mInitializedTimestamp = 0L;
        this.mNotifHandler = new GroupCoalescer.BatchableNotificationHandler() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection.1
            @Override // com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer.BatchableNotificationHandler
            public void onNotificationBatchPosted(List list) {
                NotifCollection.this.onNotificationGroupPosted(list);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
                NotifCollection.this.onNotificationChannelModified(str, userHandle, notificationChannel, i);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
                NotifCollection.this.onNotificationPosted(statusBarNotification, rankingMap);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                NotifCollection.this.onNotificationRankingUpdate(rankingMap);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
                NotifCollection.this.onNotificationRemoved(statusBarNotification, rankingMap, i);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationsInitialized() {
                NotifCollection.this.onNotificationsInitialized();
            }
        };
        this.mStatusBarService = iStatusBarService;
        this.mClock = systemClock;
        this.mNotifPipelineFlags = notifPipelineFlags;
        this.mLogger = notifCollectionLogger;
        this.mMainHandler = handler;
        this.mBgExecutor = executor;
        this.mEulogizer = logBufferEulogizer;
        this.mDumpManager = dumpManager;
        this.mInconsistencyTracker = new NotifCollectionInconsistencyTracker(notifCollectionLogger);
        this.mDismissibilityProvider = notificationDismissibilityProvider;
    }

    private void applyRanking(NotificationListenerService.RankingMap rankingMap) {
        ArrayMap arrayMap = null;
        for (NotificationEntry notificationEntry : this.mNotificationSet.values()) {
            if (!notificationEntry.isCanceled()) {
                NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
                if (rankingMap.getRanking(notificationEntry.getKey(), ranking)) {
                    notificationEntry.setRanking(ranking);
                    String overrideGroupKey = ranking.getOverrideGroupKey();
                    if (!Objects.equals(notificationEntry.getSbn().getOverrideGroupKey(), overrideGroupKey)) {
                        notificationEntry.getSbn().setOverrideGroupKey(overrideGroupKey);
                    }
                } else {
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                    }
                    arrayMap.put(notificationEntry.getKey(), notificationEntry);
                }
            }
        }
        this.mInconsistencyTracker.logNewMissingNotifications(rankingMap);
        this.mInconsistencyTracker.logNewInconsistentRankings(arrayMap, rankingMap);
        if (arrayMap != null) {
            for (NotificationEntry notificationEntry2 : arrayMap.values()) {
                notificationEntry2.mCancellationReason = 0;
                tryRemoveNotification(notificationEntry2);
            }
        }
        this.mEventQueue.add(new RankingAppliedEvent());
    }

    private void cancelDismissInterception(NotificationEntry notificationEntry) {
        this.mAmDispatchingToOtherCode = true;
        Iterator it = notificationEntry.mDismissInterceptors.iterator();
        if (it.hasNext()) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
        this.mAmDispatchingToOtherCode = false;
        notificationEntry.mDismissInterceptors.clear();
    }

    private void cancelLifetimeExtension(NotificationEntry notificationEntry) {
        this.mAmDispatchingToOtherCode = true;
        Iterator it = notificationEntry.mLifetimeExtenders.iterator();
        while (it.hasNext()) {
            ((NotifLifetimeExtender) it.next()).cancelLifetimeExtension(notificationEntry);
        }
        this.mAmDispatchingToOtherCode = false;
        notificationEntry.mLifetimeExtenders.clear();
    }

    private void cancelLocalDismissal(NotificationEntry notificationEntry) {
        NotificationEntry.DismissState dismissState = notificationEntry.getDismissState();
        NotificationEntry.DismissState dismissState2 = NotificationEntry.DismissState.NOT_DISMISSED;
        if (dismissState == dismissState2) {
            this.mLogger.logCancelLocalDismissalNotDismissedNotif(notificationEntry);
            return;
        }
        notificationEntry.setDismissState(dismissState2);
        if (notificationEntry.getSbn().getNotification().isGroupSummary()) {
            for (NotificationEntry notificationEntry2 : this.mNotificationSet.values()) {
                if (notificationEntry2.getSbn().getGroupKey().equals(notificationEntry.getSbn().getGroupKey()) && notificationEntry2.getDismissState() == NotificationEntry.DismissState.PARENT_DISMISSED) {
                    notificationEntry2.setDismissState(NotificationEntry.DismissState.NOT_DISMISSED);
                }
            }
        }
    }

    private boolean cannotBeLifetimeExtended(NotificationEntry notificationEntry) {
        boolean z = notificationEntry.getDismissState() != NotificationEntry.DismissState.NOT_DISMISSED;
        int i = notificationEntry.mCancellationReason;
        return z || (i == 1 || i == 2);
    }

    private void checkForReentrantCall() {
        if (this.mAmDispatchingToOtherCode) {
            throw ((IllegalStateException) this.mEulogizer.record(new IllegalStateException("Reentrant call detected")));
        }
    }

    private void dispatchEvents() {
        Trace.beginSection("NotifCollection.dispatchEvents");
        this.mAmDispatchingToOtherCode = true;
        while (!this.mEventQueue.isEmpty()) {
            ((NotifEvent) this.mEventQueue.remove()).dispatchTo(this.mNotifCollectionListeners);
        }
        this.mAmDispatchingToOtherCode = false;
        Trace.endSection();
    }

    private void dispatchEventsAndAsynchronouslyRebuildList() {
        Trace.beginSection("NotifCollection.dispatchEventsAndAsynchronouslyRebuildList");
        dispatchEvents();
        if (!this.mMainHandler.hasCallbacks(this.mRebuildListRunnable)) {
            this.mMainHandler.postDelayed(this.mRebuildListRunnable, 1000L);
        }
        Trace.endSection();
    }

    private void dispatchEventsAndRebuildList(String str) {
        Trace.beginSection("NotifCollection.dispatchEventsAndRebuildList");
        if (this.mMainHandler.hasCallbacks(this.mRebuildListRunnable)) {
            this.mMainHandler.removeCallbacks(this.mRebuildListRunnable);
        }
        dispatchEvents();
        CollectionReadyForBuildListener collectionReadyForBuildListener = this.mBuildListener;
        if (collectionReadyForBuildListener != null) {
            collectionReadyForBuildListener.onBuildList(this.mReadOnlyNotificationSet, str);
        }
        Trace.endSection();
    }

    private void handleFutureDismissal(NotificationEntry notificationEntry) {
        FutureDismissal futureDismissal = (FutureDismissal) this.mFutureDismissals.remove(notificationEntry.getKey());
        if (futureDismissal != null) {
            futureDismissal.onSystemServerCancel(notificationEntry.mCancellationReason);
        }
    }

    private static boolean hasFlag(NotificationEntry notificationEntry, int i) {
        return (notificationEntry.getSbn().getNotification().flags & i) != 0;
    }

    private boolean isDismissIntercepted(NotificationEntry notificationEntry) {
        return notificationEntry.mDismissInterceptors.size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDismissable(NotificationEntry notificationEntry) {
        return this.mDismissibilityProvider.isDismissable(notificationEntry);
    }

    private boolean isLifetimeExtended(NotificationEntry notificationEntry) {
        return notificationEntry.mLifetimeExtenders.size() > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dismissNotifications$1(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats, int i, int i2) {
        try {
            this.mStatusBarService.onNotificationClear(notificationEntry.getSbn().getPackageName(), notificationEntry.getSbn().getUser().getIdentifier(), notificationEntry.getSbn().getKey(), dismissedByUserStats.dismissalSurface, dismissedByUserStats.dismissalSentiment, dismissedByUserStats.notificationVisibility);
        } catch (RemoteException e) {
            this.mLogger.logRemoteExceptionOnNotificationClear(notificationEntry, i, i2, e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getInternalNotifUpdater$7(final String str, final StatusBarNotification statusBarNotification, final String str2) {
        this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getInternalNotifUpdater$6(statusBarNotification, str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        CollectionReadyForBuildListener collectionReadyForBuildListener = this.mBuildListener;
        if (collectionReadyForBuildListener != null) {
            collectionReadyForBuildListener.onBuildList(this.mReadOnlyNotificationSet, "asynchronousUpdate");
        }
    }

    private void locallyDismissNotifications(List list) {
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            NotificationEntry notificationEntry = (NotificationEntry) list.get(i2);
            NotificationEntry notificationEntry2 = (NotificationEntry) this.mNotificationSet.get(notificationEntry.getKey());
            if (notificationEntry2 == null) {
                this.mLogger.logLocallyDismissNonExistentNotif(notificationEntry, i2, size);
            } else if (notificationEntry2 != notificationEntry) {
                this.mLogger.logLocallyDismissMismatchedEntry(notificationEntry, i2, size, notificationEntry2);
            }
            NotificationEntry.DismissState dismissState = notificationEntry.getDismissState();
            NotificationEntry.DismissState dismissState2 = NotificationEntry.DismissState.DISMISSED;
            if (dismissState == dismissState2) {
                this.mLogger.logLocallyDismissAlreadyDismissedNotif(notificationEntry, i2, size);
            } else if (notificationEntry.getDismissState() == NotificationEntry.DismissState.PARENT_DISMISSED) {
                this.mLogger.logLocallyDismissAlreadyParentDismissedNotif(notificationEntry, i2, size);
            }
            notificationEntry.setDismissState(dismissState2);
            this.mLogger.logLocallyDismissed(notificationEntry, i2, size);
            if (notificationEntry.isCanceled()) {
                arrayList.add(notificationEntry);
            } else if (notificationEntry.getSbn().getNotification().isGroupSummary()) {
                for (NotificationEntry notificationEntry3 : this.mNotificationSet.values()) {
                    if (shouldAutoDismissChildren(notificationEntry3, notificationEntry.getSbn().getGroupKey())) {
                        if (notificationEntry3.getDismissState() == NotificationEntry.DismissState.DISMISSED) {
                            this.mLogger.logLocallyDismissAlreadyDismissedChild(notificationEntry3, notificationEntry, i2, size);
                        } else if (notificationEntry3.getDismissState() == NotificationEntry.DismissState.PARENT_DISMISSED) {
                            this.mLogger.logLocallyDismissAlreadyParentDismissedChild(notificationEntry3, notificationEntry, i2, size);
                        }
                        notificationEntry3.setDismissState(NotificationEntry.DismissState.PARENT_DISMISSED);
                        this.mLogger.logLocallyDismissedChild(notificationEntry3, notificationEntry, i2, size);
                        if (notificationEntry3.isCanceled()) {
                            arrayList.add(notificationEntry3);
                        }
                    }
                }
            }
        }
        int size2 = arrayList.size();
        while (i < size2) {
            Object obj = arrayList.get(i);
            i++;
            NotificationEntry notificationEntry4 = (NotificationEntry) obj;
            this.mLogger.logLocallyDismissedAlreadyCanceledEntry(notificationEntry4);
            tryRemoveNotification(notificationEntry4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onEndLifetimeExtension(NotifLifetimeExtender notifLifetimeExtender, NotificationEntry notificationEntry) {
        Assert.isMainThread();
        if (this.mAttached) {
            checkForReentrantCall();
            NotificationEntry entry = getEntry(notificationEntry.getKey());
            String strLogKey = NotificationUtils.logKey(notificationEntry);
            String str = entry == null ? "null" : notificationEntry == entry ? "same" : "different";
            if (notificationEntry != entry) {
                this.mLogger.logEntryBeingExtendedNotInCollection(notificationEntry, notifLifetimeExtender, str);
            }
            if (!notificationEntry.mLifetimeExtenders.remove(notifLifetimeExtender)) {
                throw ((IllegalStateException) this.mEulogizer.record(new IllegalStateException(String.format("Cannot end lifetime extension for extender \"%s\" of entry %s (collection entry is %s)", notifLifetimeExtender.getName(), strLogKey, str))));
            }
            this.mLogger.logLifetimeExtensionEnded(notificationEntry, notifLifetimeExtender, notificationEntry.mLifetimeExtenders.size());
            if (isLifetimeExtended(notificationEntry) || !tryRemoveNotification(notificationEntry)) {
                return;
            }
            dispatchEventsAndRebuildList("onEndLifetimeExtension");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        Assert.isMainThread();
        this.mEventQueue.add(new ChannelChangedEvent(str, userHandle, notificationChannel, i));
        dispatchEventsAndAsynchronouslyRebuildList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationGroupPosted(List list) {
        Assert.isMainThread();
        this.mLogger.logNotifGroupPosted(((CoalescedEvent) list.get(0)).getSbn().getGroupKey(), list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            CoalescedEvent coalescedEvent = (CoalescedEvent) it.next();
            postNotification(coalescedEvent.getSbn(), coalescedEvent.getRanking());
        }
        dispatchEventsAndRebuildList("onNotificationGroupPosted");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        Assert.isMainThread();
        postNotification(statusBarNotification, requireRanking(rankingMap, statusBarNotification.getKey()));
        applyRanking(rankingMap);
        dispatchEventsAndRebuildList("onNotificationPosted");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        Assert.isMainThread();
        this.mEventQueue.add(new RankingUpdatedEvent(rankingMap));
        applyRanking(rankingMap);
        dispatchEventsAndRebuildList("onNotificationRankingUpdate");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        Assert.isMainThread();
        this.mLogger.logNotifRemoved(statusBarNotification, i);
        NotificationEntry notificationEntry = (NotificationEntry) this.mNotificationSet.get(statusBarNotification.getKey());
        if (notificationEntry == null) {
            this.mLogger.logNoNotificationToRemoveWithKey(statusBarNotification, i);
            return;
        }
        notificationEntry.mCancellationReason = i;
        tryRemoveNotification(notificationEntry);
        applyRanking(rankingMap);
        dispatchEventsAndRebuildList("onNotificationRemoved");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotificationsInitialized() {
        this.mInitializedTimestamp = this.mClock.uptimeMillis();
    }

    private void postNotification(StatusBarNotification statusBarNotification, NotificationListenerService.Ranking ranking) {
        NotificationEntry notificationEntry = (NotificationEntry) this.mNotificationSet.get(statusBarNotification.getKey());
        if (notificationEntry == null) {
            NotificationEntry notificationEntry2 = new NotificationEntry(statusBarNotification, ranking, this.mClock.uptimeMillis());
            this.mEventQueue.add(new InitEntryEvent(notificationEntry2));
            this.mEventQueue.add(new BindEntryEvent(notificationEntry2, statusBarNotification));
            this.mNotificationSet.put(statusBarNotification.getKey(), notificationEntry2);
            this.mLogger.logNotifPosted(notificationEntry2);
            this.mEventQueue.add(new EntryAddedEvent(notificationEntry2));
            return;
        }
        cancelLocalDismissal(notificationEntry);
        cancelLifetimeExtension(notificationEntry);
        cancelDismissInterception(notificationEntry);
        notificationEntry.mCancellationReason = -1;
        notificationEntry.setSbn(statusBarNotification);
        this.mEventQueue.add(new BindEntryEvent(notificationEntry, statusBarNotification));
        this.mLogger.logNotifUpdated(notificationEntry);
        this.mEventQueue.add(new EntryUpdatedEvent(notificationEntry, true));
    }

    private static NotificationListenerService.Ranking requireRanking(NotificationListenerService.RankingMap rankingMap, String str) {
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        if (rankingMap.getRanking(str, ranking)) {
            return ranking;
        }
        throw new IllegalArgumentException("Ranking map doesn't contain key: " + str);
    }

    static boolean shouldAutoDismissChildren(NotificationEntry notificationEntry, String str) {
        if (!notificationEntry.getSbn().getGroupKey().equals(str) || notificationEntry.getSbn().getNotification().isGroupSummary() || hasFlag(notificationEntry, 2) || hasFlag(notificationEntry, 4096) || hasFlag(notificationEntry, 32)) {
            return false;
        }
        return (notificationEntry.getChannel() == null || !notificationEntry.getChannel().isImportantConversation()) && notificationEntry.getDismissState() != NotificationEntry.DismissState.DISMISSED;
    }

    private static boolean shouldDismissOnClearAll(NotificationEntry notificationEntry, int i) {
        return userIdMatches(notificationEntry, i) && notificationEntry.isClearable() && !hasFlag(notificationEntry, 4096) && notificationEntry.getDismissState() != NotificationEntry.DismissState.DISMISSED;
    }

    private boolean tryRemoveNotification(NotificationEntry notificationEntry) {
        NotificationEntry notificationEntry2 = (NotificationEntry) this.mNotificationSet.get(notificationEntry.getKey());
        if (notificationEntry2 == null) {
            Log.wtf("NotifCollection", "TRY REMOVE non-existent notification " + NotificationUtils.logKey(notificationEntry));
            return false;
        }
        if (notificationEntry2 != notificationEntry) {
            throw ((IllegalStateException) this.mEulogizer.record(new IllegalStateException("Mismatched stored and tryRemoved entries for key " + NotificationUtils.logKey(notificationEntry) + ": stored=@" + Integer.toHexString(notificationEntry2.hashCode()) + " tryRemoved=@" + Integer.toHexString(notificationEntry.hashCode()))));
        }
        if (!notificationEntry.isCanceled()) {
            throw ((IllegalStateException) this.mEulogizer.record(new IllegalStateException("Cannot remove notification " + NotificationUtils.logKey(notificationEntry) + ": has not been marked for removal")));
        }
        if (cannotBeLifetimeExtended(notificationEntry)) {
            cancelLifetimeExtension(notificationEntry);
        } else {
            updateLifetimeExtension(notificationEntry);
        }
        if (isLifetimeExtended(notificationEntry)) {
            return false;
        }
        this.mLogger.logNotifReleased(notificationEntry);
        this.mNotificationSet.remove(notificationEntry.getKey());
        cancelDismissInterception(notificationEntry);
        this.mEventQueue.add(new EntryRemovedEvent(notificationEntry, notificationEntry.mCancellationReason));
        this.mEventQueue.add(new CleanUpEntryEvent(notificationEntry));
        handleFutureDismissal(notificationEntry);
        return true;
    }

    private void updateDismissInterceptors(NotificationEntry notificationEntry) {
        notificationEntry.mDismissInterceptors.clear();
        this.mAmDispatchingToOtherCode = true;
        Iterator it = this.mDismissInterceptors.iterator();
        if (it.hasNext()) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
            throw null;
        }
        this.mAmDispatchingToOtherCode = false;
    }

    private void updateLifetimeExtension(NotificationEntry notificationEntry) {
        notificationEntry.mLifetimeExtenders.clear();
        this.mAmDispatchingToOtherCode = true;
        for (NotifLifetimeExtender notifLifetimeExtender : this.mLifetimeExtenders) {
            if (notifLifetimeExtender.maybeExtendLifetime(notificationEntry, notificationEntry.mCancellationReason)) {
                this.mLogger.logLifetimeExtended(notificationEntry, notifLifetimeExtender);
                notificationEntry.mLifetimeExtenders.add(notifLifetimeExtender);
            }
        }
        this.mAmDispatchingToOtherCode = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: updateNotificationInternally, reason: merged with bridge method [inline-methods] */
    public void lambda$getInternalNotifUpdater$6(StatusBarNotification statusBarNotification, String str, String str2) {
        Assert.isMainThread();
        checkForReentrantCall();
        NotificationEntry notificationEntry = (NotificationEntry) this.mNotificationSet.get(statusBarNotification.getKey());
        if (notificationEntry == null) {
            this.mLogger.logNotifInternalUpdateFailed(statusBarNotification, str, str2);
            return;
        }
        this.mLogger.logNotifInternalUpdate(notificationEntry, str, str2);
        notificationEntry.setSbn(statusBarNotification);
        this.mEventQueue.add(new BindEntryEvent(notificationEntry, statusBarNotification));
        this.mLogger.logNotifUpdated(notificationEntry);
        this.mEventQueue.add(new EntryUpdatedEvent(notificationEntry, false));
        dispatchEventsAndRebuildList("updateNotificationInternally");
    }

    private static boolean userIdMatches(NotificationEntry notificationEntry, int i) {
        return i == -1 || notificationEntry.getSbn().getUser().getIdentifier() == -1 || notificationEntry.getSbn().getUser().getIdentifier() == i;
    }

    void addCollectionListener(NotifCollectionListener notifCollectionListener) {
        Assert.isMainThread();
        this.mNotifCollectionListeners.addIfAbsent(notifCollectionListener);
    }

    void addNotificationLifetimeExtender(NotifLifetimeExtender notifLifetimeExtender) {
        Assert.isMainThread();
        checkForReentrantCall();
        if (!this.mLifetimeExtenders.contains(notifLifetimeExtender)) {
            this.mLifetimeExtenders.add(notifLifetimeExtender);
            notifLifetimeExtender.setCallback(new NotifLifetimeExtender.OnEndLifetimeExtensionCallback() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda10
                @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender.OnEndLifetimeExtensionCallback
                public final void onEndLifetimeExtension(NotifLifetimeExtender notifLifetimeExtender2, NotificationEntry notificationEntry) {
                    this.f$0.onEndLifetimeExtension(notifLifetimeExtender2, notificationEntry);
                }
            });
        } else {
            throw new IllegalArgumentException("Extender " + notifLifetimeExtender + " already added.");
        }
    }

    public void attach(final GroupCoalescer groupCoalescer) {
        Assert.isMainThread();
        if (this.mAttached) {
            throw new RuntimeException("attach() called twice");
        }
        this.mAttached = true;
        this.mDumpManager.registerDumpable("NotifCollection", this);
        groupCoalescer.setNotificationHandler(this.mNotifHandler);
        NotifCollectionInconsistencyTracker notifCollectionInconsistencyTracker = this.mInconsistencyTracker;
        final Map map = this.mNotificationSet;
        map.getClass();
        notifCollectionInconsistencyTracker.attach(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return map.keySet();
            }
        }, new Function0() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return groupCoalescer.getCoalescedKeySet();
            }
        });
    }

    public void dismissAllNotifications(int i) {
        Assert.isMainThread();
        checkForReentrantCall();
        this.mLogger.logDismissAll(i);
        try {
            this.mStatusBarService.onClearAllNotifications(i);
        } catch (RemoteException e) {
            this.mLogger.logRemoteExceptionOnClearAllNotifications(e);
        }
        ArrayList arrayList = new ArrayList(getAllNotifs());
        int size = arrayList.size();
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(size2);
            if (!shouldDismissOnClearAll(notificationEntry, i)) {
                updateDismissInterceptors(notificationEntry);
                if (isDismissIntercepted(notificationEntry)) {
                    this.mLogger.logNotifClearAllDismissalIntercepted(notificationEntry, size2, size);
                }
                arrayList.remove(size2);
            }
        }
        locallyDismissNotifications(arrayList);
        dispatchEventsAndRebuildList("dismissAllNotifications");
    }

    public void dismissNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats) {
        dismissNotifications(List.of(new Pair(notificationEntry, dismissedByUserStats)));
    }

    public void dismissNotifications(List list) {
        final NotifCollection notifCollection;
        Assert.isMainThread();
        checkForReentrantCall();
        final int size = list.size();
        ArrayList arrayList = new ArrayList();
        final int i = 0;
        while (i < list.size()) {
            final NotificationEntry notificationEntry = (NotificationEntry) ((Pair) list.get(i)).first;
            final DismissedByUserStats dismissedByUserStats = (DismissedByUserStats) ((Pair) list.get(i)).second;
            dismissedByUserStats.getClass();
            NotificationEntry notificationEntry2 = (NotificationEntry) this.mNotificationSet.get(notificationEntry.getKey());
            if (notificationEntry2 == null) {
                this.mLogger.logDismissNonExistentNotif(notificationEntry, i, size);
            } else {
                if (notificationEntry != notificationEntry2) {
                    throw ((IllegalStateException) this.mEulogizer.record(new IllegalStateException("Invalid entry: different stored and dismissed entries for " + NotificationUtils.logKey(notificationEntry) + " (" + i + "/" + size + ") dismissed=@" + Integer.toHexString(notificationEntry.hashCode()) + " stored=@" + Integer.toHexString(notificationEntry2.hashCode()))));
                }
                if (notificationEntry.getDismissState() == NotificationEntry.DismissState.DISMISSED) {
                    this.mLogger.logDismissAlreadyDismissedNotif(notificationEntry, i, size);
                } else {
                    if (notificationEntry.getDismissState() == NotificationEntry.DismissState.PARENT_DISMISSED) {
                        this.mLogger.logDismissAlreadyParentDismissedNotif(notificationEntry, i, size);
                    }
                    this.updateDismissInterceptors(notificationEntry);
                    if (this.isDismissIntercepted(notificationEntry)) {
                        this.mLogger.logNotifDismissedIntercepted(notificationEntry, i, size);
                    } else {
                        arrayList.add(notificationEntry);
                        if (!notificationEntry.isCanceled()) {
                            notifCollection = this;
                            this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$dismissNotifications$1(notificationEntry, dismissedByUserStats, i, size);
                                }
                            });
                        }
                        i++;
                        this = notifCollection;
                    }
                }
            }
            notifCollection = this;
            i++;
            this = notifCollection;
        }
        NotifCollection notifCollection2 = this;
        notifCollection2.locallyDismissNotifications(arrayList);
        notifCollection2.dispatchEventsAndRebuildList("dismissNotifications");
    }

    Collection getAllNotifs() {
        Assert.isMainThread();
        return this.mReadOnlyNotificationSet;
    }

    public NotificationEntry getEntry(String str) {
        return (NotificationEntry) this.mNotificationSet.get(str);
    }

    public NotificationEntry getGroupSummary(final String str) {
        return (NotificationEntry) this.mNotificationSet.values().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals(((NotificationEntry) obj).getSbn().getGroupKey(), str);
            }
        }).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((NotificationEntry) obj).getSbn().getNotification().isGroupSummary();
            }
        }).findFirst().orElse(null);
    }

    public InternalNotifUpdater getInternalNotifUpdater(final String str) {
        return new InternalNotifUpdater() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda5
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater
            public final void onInternalNotificationUpdate(StatusBarNotification statusBarNotification, String str2) {
                this.f$0.lambda$getInternalNotifUpdater$7(str, statusBarNotification, str2);
            }
        };
    }

    public boolean isOnlyChildInGroup(NotificationEntry notificationEntry) {
        final String groupKey = notificationEntry.getSbn().getGroupKey();
        return this.mNotificationSet.get(notificationEntry.getKey()) == notificationEntry && this.mNotificationSet.values().stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.equals(((NotificationEntry) obj).getSbn().getGroupKey(), groupKey);
            }
        }).filter(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NotifCollection.$r8$lambda$Wfrm0rdZGRocFrUQ4Md4rzICOXg((NotificationEntry) obj);
            }
        }).count() == 1;
    }

    public Runnable registerFutureDismissal(NotificationEntry notificationEntry, int i, DismissedByUserStatsCreator dismissedByUserStatsCreator) {
        FutureDismissal futureDismissal = (FutureDismissal) this.mFutureDismissals.get(notificationEntry.getKey());
        if (futureDismissal != null) {
            this.mLogger.logFutureDismissalReused(futureDismissal);
            return futureDismissal;
        }
        FutureDismissal futureDismissal2 = new FutureDismissal(notificationEntry, i, dismissedByUserStatsCreator);
        this.mFutureDismissals.put(notificationEntry.getKey(), futureDismissal2);
        this.mLogger.logFutureDismissalRegistered(futureDismissal2);
        return futureDismissal2;
    }

    void setBuildListener(CollectionReadyForBuildListener collectionReadyForBuildListener) {
        Assert.isMainThread();
        this.mBuildListener = collectionReadyForBuildListener;
    }
}
