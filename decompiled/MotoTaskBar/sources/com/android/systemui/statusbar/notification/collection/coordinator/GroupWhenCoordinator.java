package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.render.NotifGroupController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: GroupWhenCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GroupWhenCoordinator implements Coordinator {
    private Runnable cancelInvalidateListRunnable;
    private final DelayableExecutor delayableExecutor;
    private final Runnable invalidateListRunnable;
    private final GroupWhenCoordinator$invalidator$1 invalidator;
    private final ArrayMap notificationGroupTimes;
    private final SystemClock systemClock;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$invalidator$1] */
    public GroupWhenCoordinator(DelayableExecutor delayableExecutor, SystemClock systemClock) {
        delayableExecutor.getClass();
        systemClock.getClass();
        this.delayableExecutor = delayableExecutor;
        this.systemClock = systemClock;
        this.invalidator = new Invalidator() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$invalidator$1
        };
        this.notificationGroupTimes = new ArrayMap();
        this.invalidateListRunnable = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$invalidateListRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                invalidateList("future notification invalidation");
            }
        };
    }

    private final long calculateGroupNotificationTime(GroupEntry groupEntry, long j) {
        List children = groupEntry.getChildren();
        children.getClass();
        Iterator it = SequencesKt.mapNotNull(CollectionsKt.asSequence(children), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return GroupWhenCoordinator.calculateGroupNotificationTime$lambda$2((NotificationEntry) obj);
            }
        }).iterator();
        long jMax = Long.MIN_VALUE;
        long jMin = Long.MAX_VALUE;
        while (it.hasNext()) {
            long jLongValue = ((Number) it.next()).longValue();
            if (j - jLongValue > 0) {
                jMax = Math.max(jMax, jLongValue);
            } else {
                jMin = Math.min(jMin, jLongValue);
            }
        }
        if (jMax != Long.MIN_VALUE || jMin != Long.MAX_VALUE) {
            return jMin != Long.MAX_VALUE ? jMin : jMax;
        }
        NotificationEntry summary = groupEntry.getSummary();
        if (summary != null) {
            return summary.getCreationTime();
        }
        throw new IllegalStateException("Required value was null.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Long calculateGroupNotificationTime$lambda$2(NotificationEntry notificationEntry) {
        Long lValueOf = Long.valueOf(notificationEntry.getSbn().getNotification().when);
        if (lValueOf.longValue() > 0) {
            return lValueOf;
        }
        return null;
    }

    private final void cancelListInvalidation() {
        Runnable runnable = this.cancelInvalidateListRunnable;
        if (runnable != null) {
            runnable.run();
        }
        this.cancelInvalidateListRunnable = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderGroupListener(GroupEntry groupEntry, NotifGroupController notifGroupController) {
        Long l = (Long) this.notificationGroupTimes.get(groupEntry);
        if (l != null) {
            notifGroupController.setNotificationGroupWhen(l.longValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilterListener(List list) {
        cancelListInvalidation();
        this.notificationGroupTimes.clear();
        long jCurrentTimeMillis = this.systemClock.currentTimeMillis();
        Sequence<GroupEntry> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator$onBeforeFinalizeFilterListener$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof GroupEntry);
            }
        });
        sequenceFilter.getClass();
        long jMin = Long.MAX_VALUE;
        for (GroupEntry groupEntry : sequenceFilter) {
            long jCalculateGroupNotificationTime = calculateGroupNotificationTime(groupEntry, jCurrentTimeMillis);
            this.notificationGroupTimes.put(groupEntry, Long.valueOf(jCalculateGroupNotificationTime));
            if (jCalculateGroupNotificationTime > jCurrentTimeMillis) {
                jMin = Math.min(jMin, jCalculateGroupNotificationTime);
            }
        }
        if (jMin != Long.MAX_VALUE) {
            this.cancelInvalidateListRunnable = this.delayableExecutor.executeDelayed(this.invalidateListRunnable, jMin - jCurrentTimeMillis);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                list.getClass();
                GroupWhenCoordinator.this.onBeforeFinalizeFilterListener(list);
            }
        });
        notifPipeline.addOnAfterRenderGroupListener(new OnAfterRenderGroupListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupWhenCoordinator.attach.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener
            public final void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController) {
                groupEntry.getClass();
                notifGroupController.getClass();
                GroupWhenCoordinator.this.onAfterRenderGroupListener(groupEntry, notifGroupController);
            }
        });
        notifPipeline.addPreRenderInvalidator(this.invalidator);
    }
}
