package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: DataStoreCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DataStoreCoordinator implements CoreCoordinator {
    private final NotifLiveDataStoreImpl notifLiveDataStoreImpl;

    public DataStoreCoordinator(NotifLiveDataStoreImpl notifLiveDataStoreImpl) {
        notifLiveDataStoreImpl.getClass();
        this.notifLiveDataStoreImpl = notifLiveDataStoreImpl;
    }

    private final List flattenedEntryList(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof NotificationEntry) {
                arrayList.add(listEntry);
            } else {
                if (!(listEntry instanceof GroupEntry)) {
                    throw new IllegalStateException(("Unexpected entry " + listEntry).toString());
                }
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry summary = groupEntry.getSummary();
                if (summary == null) {
                    throw new IllegalStateException(("No Summary: " + groupEntry).toString());
                }
                arrayList.add(summary);
                List children = groupEntry.getChildren();
                children.getClass();
                arrayList.addAll(children);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderList(List list) {
        this.notifLiveDataStoreImpl.setActiveNotifList(flattenedEntryList(list));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list, NotifStackController notifStackController) {
                list.getClass();
                notifStackController.getClass();
                DataStoreCoordinator.this.onAfterRenderList(list);
            }
        });
    }
}
