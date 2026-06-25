package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.OnUserInteractionCallback;
import com.android.systemui.statusbar.policy.HeadsUpManager;

/* JADX INFO: loaded from: classes.dex */
public class OnUserInteractionCallbackImpl implements OnUserInteractionCallback {
    private final HeadsUpManager mHeadsUpManager;
    private final NotifCollection mNotifCollection;
    private final NotificationVisibilityProvider mVisibilityProvider;
    private final VisualStabilityCoordinator mVisualStabilityCoordinator;

    public OnUserInteractionCallbackImpl(NotificationVisibilityProvider notificationVisibilityProvider, NotifCollection notifCollection, HeadsUpManager headsUpManager, VisualStabilityCoordinator visualStabilityCoordinator) {
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mNotifCollection = notifCollection;
        this.mHeadsUpManager = headsUpManager;
        this.mVisualStabilityCoordinator = visualStabilityCoordinator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DismissedByUserStats getDismissedByUserStats(NotificationEntry notificationEntry) {
        return new DismissedByUserStats(this.mHeadsUpManager.isHeadsUpEntry(notificationEntry.getKey()) ? 1 : 3, 1, this.mVisibilityProvider.obtain(notificationEntry, true));
    }

    @Override // com.android.systemui.statusbar.notification.row.OnUserInteractionCallback
    public Runnable registerFutureDismissal(NotificationEntry notificationEntry, int i) {
        return this.mNotifCollection.registerFutureDismissal(notificationEntry, i, new NotifCollection.DismissedByUserStatsCreator() { // from class: com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.collection.NotifCollection.DismissedByUserStatsCreator
            public final DismissedByUserStats createDismissedByUserStats(NotificationEntry notificationEntry2) {
                return this.f$0.getDismissedByUserStats(notificationEntry2);
            }
        });
    }
}
