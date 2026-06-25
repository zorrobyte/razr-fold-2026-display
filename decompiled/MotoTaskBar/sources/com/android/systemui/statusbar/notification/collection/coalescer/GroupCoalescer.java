package com.android.systemui.statusbar.notification.collection.coalescer;

import android.app.NotificationChannel;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class GroupCoalescer implements Dumpable {
    private final Map mBatches;
    private final SystemClock mClock;
    private final Map mCoalescedEvents;
    private final Comparator mEventComparator;
    private BatchableNotificationHandler mHandler;
    private final NotificationListener.NotificationHandler mListener;
    private final GroupCoalescerLogger mLogger;
    private final DelayableExecutor mMainExecutor;
    private final long mMaxGroupLingerDuration;
    private final long mMinGroupLingerDuration;

    public interface BatchableNotificationHandler extends NotificationListener.NotificationHandler {
        void onNotificationBatchPosted(List list);
    }

    /* JADX INFO: renamed from: $r8$lambda$Rx6C6EJwYyXvj71uk-whuwi5HIM, reason: not valid java name */
    public static /* synthetic */ int m1697$r8$lambda$Rx6C6EJwYyXvj71ukwhuwi5HIM(CoalescedEvent coalescedEvent, CoalescedEvent coalescedEvent2) {
        int iCompare = Boolean.compare(coalescedEvent2.getSbn().getNotification().isGroupSummary(), coalescedEvent.getSbn().getNotification().isGroupSummary());
        return iCompare == 0 ? coalescedEvent.getPosition() - coalescedEvent2.getPosition() : iCompare;
    }

    public GroupCoalescer(DelayableExecutor delayableExecutor, SystemClock systemClock, GroupCoalescerLogger groupCoalescerLogger) {
        this(delayableExecutor, systemClock, groupCoalescerLogger, 200L, 500L);
    }

    GroupCoalescer(DelayableExecutor delayableExecutor, SystemClock systemClock, GroupCoalescerLogger groupCoalescerLogger, long j, long j2) {
        this.mCoalescedEvents = new ArrayMap();
        this.mBatches = new ArrayMap();
        this.mListener = new NotificationListener.NotificationHandler() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer.1
            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
                GroupCoalescer.this.mHandler.onNotificationChannelModified(str, userHandle, notificationChannel, i);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
                GroupCoalescer.this.maybeEmitBatch(statusBarNotification);
                GroupCoalescer.this.applyRanking(rankingMap);
                if (!GroupCoalescer.this.handleNotificationPosted(statusBarNotification, rankingMap)) {
                    GroupCoalescer.this.mHandler.onNotificationPosted(statusBarNotification, rankingMap);
                } else {
                    GroupCoalescer.this.mLogger.logEventCoalesced(statusBarNotification.getKey());
                    GroupCoalescer.this.mHandler.onNotificationRankingUpdate(rankingMap);
                }
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                GroupCoalescer.this.applyRanking(rankingMap);
                GroupCoalescer.this.mHandler.onNotificationRankingUpdate(rankingMap);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
                GroupCoalescer.this.maybeEmitBatch(statusBarNotification);
                GroupCoalescer.this.applyRanking(rankingMap);
                GroupCoalescer.this.mHandler.onNotificationRemoved(statusBarNotification, rankingMap, i);
            }

            @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
            public void onNotificationsInitialized() {
                GroupCoalescer.this.mHandler.onNotificationsInitialized();
            }
        };
        this.mEventComparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return GroupCoalescer.m1697$r8$lambda$Rx6C6EJwYyXvj71ukwhuwi5HIM((CoalescedEvent) obj, (CoalescedEvent) obj2);
            }
        };
        this.mMainExecutor = delayableExecutor;
        this.mClock = systemClock;
        this.mLogger = groupCoalescerLogger;
        this.mMinGroupLingerDuration = j;
        this.mMaxGroupLingerDuration = j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void applyRanking(NotificationListenerService.RankingMap rankingMap) {
        for (CoalescedEvent coalescedEvent : this.mCoalescedEvents.values()) {
            NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
            if (rankingMap.getRanking(coalescedEvent.getKey(), ranking)) {
                coalescedEvent.setRanking(ranking);
            } else {
                this.mLogger.logMissingRanking(coalescedEvent.getKey());
            }
        }
    }

    private void emitBatch(EventBatch eventBatch) {
        if (eventBatch != this.mBatches.get(eventBatch.mGroupKey)) {
            throw new IllegalStateException("Cannot emit out-of-date batch " + eventBatch.mGroupKey);
        }
        if (eventBatch.mMembers.isEmpty()) {
            throw new IllegalStateException("Batch " + eventBatch.mGroupKey + " cannot be empty");
        }
        Runnable runnable = eventBatch.mCancelShortTimeout;
        if (runnable != null) {
            runnable.run();
            eventBatch.mCancelShortTimeout = null;
        }
        this.mBatches.remove(eventBatch.mGroupKey);
        ArrayList arrayList = new ArrayList(eventBatch.mMembers);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CoalescedEvent coalescedEvent = (CoalescedEvent) obj;
            this.mCoalescedEvents.remove(coalescedEvent.getKey());
            coalescedEvent.setBatch(null);
        }
        arrayList.sort(this.mEventComparator);
        this.mLogger.logEmitBatch(eventBatch.mGroupKey, eventBatch.mMembers.size(), this.mClock.uptimeMillis() - eventBatch.mCreatedTimestamp);
        this.mHandler.onNotificationBatchPosted(arrayList);
    }

    private EventBatch getOrBuildBatch(String str) {
        EventBatch eventBatch = (EventBatch) this.mBatches.get(str);
        if (eventBatch != null) {
            return eventBatch;
        }
        EventBatch eventBatch2 = new EventBatch(this.mClock.uptimeMillis(), str);
        this.mBatches.put(str, eventBatch2);
        return eventBatch2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        if (this.mCoalescedEvents.containsKey(statusBarNotification.getKey())) {
            throw new IllegalStateException("Notification has already been coalesced: " + statusBarNotification.getKey());
        }
        if (!statusBarNotification.isGroup()) {
            return false;
        }
        EventBatch orBuildBatch = getOrBuildBatch(statusBarNotification.getGroupKey());
        CoalescedEvent coalescedEvent = new CoalescedEvent(statusBarNotification.getKey(), orBuildBatch.mMembers.size(), statusBarNotification, requireRanking(rankingMap, statusBarNotification.getKey()), orBuildBatch);
        this.mCoalescedEvents.put(coalescedEvent.getKey(), coalescedEvent);
        orBuildBatch.mMembers.add(coalescedEvent);
        resetShortTimeout(orBuildBatch);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetShortTimeout$0(EventBatch eventBatch) {
        eventBatch.mCancelShortTimeout = null;
        emitBatch(eventBatch);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeEmitBatch(StatusBarNotification statusBarNotification) {
        CoalescedEvent coalescedEvent = (CoalescedEvent) this.mCoalescedEvents.get(statusBarNotification.getKey());
        EventBatch eventBatch = (EventBatch) this.mBatches.get(statusBarNotification.getGroupKey());
        if (coalescedEvent == null) {
            if (eventBatch == null || this.mClock.uptimeMillis() - eventBatch.mCreatedTimestamp < this.mMaxGroupLingerDuration) {
                return;
            }
            this.mLogger.logMaxBatchTimeout(statusBarNotification.getKey(), eventBatch.mGroupKey);
            emitBatch(eventBatch);
            return;
        }
        GroupCoalescerLogger groupCoalescerLogger = this.mLogger;
        String key = statusBarNotification.getKey();
        EventBatch batch = coalescedEvent.getBatch();
        batch.getClass();
        groupCoalescerLogger.logEarlyEmit(key, batch.mGroupKey);
        EventBatch batch2 = coalescedEvent.getBatch();
        batch2.getClass();
        emitBatch(batch2);
    }

    private NotificationListenerService.Ranking requireRanking(NotificationListenerService.RankingMap rankingMap, String str) {
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        if (rankingMap.getRanking(str, ranking)) {
            return ranking;
        }
        throw new IllegalArgumentException("Ranking map does not contain key " + str);
    }

    private void resetShortTimeout(final EventBatch eventBatch) {
        Runnable runnable = eventBatch.mCancelShortTimeout;
        if (runnable != null) {
            runnable.run();
        }
        eventBatch.mCancelShortTimeout = this.mMainExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$resetShortTimeout$0(eventBatch);
            }
        }, this.mMinGroupLingerDuration);
    }

    public void attach(NotificationListener notificationListener) {
        notificationListener.addNotificationHandler(this.mListener);
    }

    public Set getCoalescedKeySet() {
        return Collections.unmodifiableSet(this.mCoalescedEvents.keySet());
    }

    public void setNotificationHandler(BatchableNotificationHandler batchableNotificationHandler) {
        this.mHandler = batchableNotificationHandler;
    }
}
