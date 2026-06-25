package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.render.NotifRowController;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: RowAlertTimeCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RowAlertTimeCoordinator implements Coordinator {
    private final ArrayMap latestAlertTimeBySummary = new ArrayMap();

    private final long calculateLatestAlertTime(GroupEntry groupEntry) {
        Long l;
        List children = groupEntry.getChildren();
        children.getClass();
        Iterator it = children.iterator();
        if (it.hasNext()) {
            Long lValueOf = Long.valueOf(((NotificationEntry) it.next()).getLastAudiblyAlertedMs());
            while (it.hasNext()) {
                Long lValueOf2 = Long.valueOf(((NotificationEntry) it.next()).getLastAudiblyAlertedMs());
                if (lValueOf.compareTo(lValueOf2) < 0) {
                    lValueOf = lValueOf2;
                }
            }
            l = lValueOf;
        } else {
            l = null;
        }
        long jLongValue = l != null ? l.longValue() : 0L;
        NotificationEntry summary = groupEntry.getSummary();
        if (summary != null) {
            return Math.max(jLongValue, summary.getLastAudiblyAlertedMs());
        }
        throw new IllegalStateException("Required value was null.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
        Long l = (Long) this.latestAlertTimeBySummary.get(notificationEntry);
        notifRowController.setLastAudibleMs(l != null ? l.longValue() : notificationEntry.getLastAudiblyAlertedMs());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilterListener(List list) {
        this.latestAlertTimeBySummary.clear();
        Sequence<GroupEntry> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator$onBeforeFinalizeFilterListener$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof GroupEntry);
            }
        });
        sequenceFilter.getClass();
        for (GroupEntry groupEntry : sequenceFilter) {
            NotificationEntry summary = groupEntry.getSummary();
            if (summary == null) {
                throw new IllegalStateException("Required value was null.");
            }
            this.latestAlertTimeBySummary.put(summary, Long.valueOf(calculateLatestAlertTime(groupEntry)));
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                list.getClass();
                RowAlertTimeCoordinator.this.onBeforeFinalizeFilterListener(list);
            }
        });
        notifPipeline.addOnAfterRenderEntryListener(new OnAfterRenderEntryListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAlertTimeCoordinator.attach.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener
            public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
                notificationEntry.getClass();
                notifRowController.getClass();
                RowAlertTimeCoordinator.this.onAfterRenderEntry(notificationEntry, notifRowController);
            }
        });
    }
}
