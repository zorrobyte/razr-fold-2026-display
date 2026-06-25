package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProviderImpl;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: DismissibilityCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DismissibilityCoordinator implements Coordinator {
    private final NotificationDismissibilityProviderImpl provider;

    public DismissibilityCoordinator(NotificationDismissibilityProviderImpl notificationDismissibilityProviderImpl) {
        notificationDismissibilityProviderImpl.getClass();
        this.provider = notificationDismissibilityProviderImpl;
    }

    private final boolean markNonDismissibleEntries(Set set, List list, boolean z) {
        Iterator it = list.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry != null && !representativeEntry.isDismissableForState(z)) {
                String key = representativeEntry.getKey();
                key.getClass();
                set.add(key);
                z2 = true;
            }
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                List children = groupEntry.getChildren();
                children.getClass();
                if (markNonDismissibleEntries(set, children, z)) {
                    NotificationEntry representativeEntry2 = groupEntry.getRepresentativeEntry();
                    if (representativeEntry2 != null) {
                        String key2 = representativeEntry2.getKey();
                        key2.getClass();
                        set.add(key2);
                    }
                    z2 = true;
                }
            }
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeRenderListListener(List list) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        markNonDismissibleEntries(linkedHashSet, list, false);
        this.provider.update(linkedHashSet);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DismissibilityCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                list.getClass();
                DismissibilityCoordinator.this.onBeforeRenderListListener(list);
            }
        });
    }
}
