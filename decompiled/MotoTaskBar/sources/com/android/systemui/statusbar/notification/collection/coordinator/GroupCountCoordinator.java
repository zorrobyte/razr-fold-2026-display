package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.render.NotifGroupController;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: GroupCountCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GroupCountCoordinator implements Coordinator {
    private final ArrayMap untruncatedChildCounts = new ArrayMap();

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController) {
        Integer num = (Integer) this.untruncatedChildCounts.get(groupEntry);
        if (num != null) {
            notifGroupController.setUntruncatedChildCount(num.intValue());
            return;
        }
        throw new IllegalStateException(("No untruncated child count for group: " + groupEntry.getKey()).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeFinalizeFilter(List list) {
        this.untruncatedChildCounts.clear();
        Sequence<GroupEntry> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(list), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator$onBeforeFinalizeFilter$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof GroupEntry);
            }
        });
        sequenceFilter.getClass();
        for (GroupEntry groupEntry : sequenceFilter) {
            this.untruncatedChildCounts.put(groupEntry, Integer.valueOf(groupEntry.getChildren().size()));
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                list.getClass();
                GroupCountCoordinator.this.onBeforeFinalizeFilter(list);
            }
        });
        notifPipeline.addOnAfterRenderGroupListener(new OnAfterRenderGroupListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator.attach.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderGroupListener
            public final void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController) {
                groupEntry.getClass();
                notifGroupController.getClass();
                GroupCountCoordinator.this.onAfterRenderGroup(groupEntry, notifGroupController);
            }
        });
    }
}
