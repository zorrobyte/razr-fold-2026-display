package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.Pair;

/* JADX INFO: compiled from: HeadsUpCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
final class HunMutatorImpl implements HunMutator {
    private final List deferred;
    private final HeadsUpManager headsUpManager;

    public HunMutatorImpl(HeadsUpManager headsUpManager) {
        headsUpManager.getClass();
        this.headsUpManager = headsUpManager;
        this.deferred = new ArrayList();
    }

    public final void commitModifications() {
        for (Pair pair : this.deferred) {
            this.headsUpManager.removeNotification((String) pair.component1(), ((Boolean) pair.component2()).booleanValue());
        }
        this.deferred.clear();
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.HunMutator
    public void removeNotification(String str, boolean z) {
        str.getClass();
        this.deferred.add(new Pair(str, Boolean.valueOf(z)));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.HunMutator
    public void updateNotification(NotificationEntry notificationEntry, boolean z) {
        notificationEntry.getClass();
        this.headsUpManager.updateNotification(notificationEntry, z);
    }
}
