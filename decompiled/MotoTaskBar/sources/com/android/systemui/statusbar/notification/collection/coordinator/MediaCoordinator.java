package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.Flags;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.icon.IconManager;

/* JADX INFO: loaded from: classes.dex */
public class MediaCoordinator implements Coordinator {
    private final IconManager mIconManager;
    private final Boolean mIsMediaFeatureEnabled;
    private final IStatusBarService mStatusBarService;
    private final ArrayMap mIconsState = new ArrayMap();
    private final NotifFilter mMediaFilter = new NotifFilter("MediaCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.MediaCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            if (!MediaCoordinator.this.mIsMediaFeatureEnabled.booleanValue() || !MediaDataManager.isMediaNotification(notificationEntry.getSbn())) {
                return false;
            }
            if (Flags.notificationsBackgroundIcons()) {
                return true;
            }
            MediaCoordinator.this.inflateOrUpdateIcons(notificationEntry);
            return true;
        }
    };
    private final NotifCollectionListener mCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.MediaCoordinator.2
        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryAdded(NotificationEntry notificationEntry) {
            if (Flags.notificationsBackgroundIcons() && MediaDataManager.isMediaNotification(notificationEntry.getSbn())) {
                MediaCoordinator.this.inflateOrUpdateIcons(notificationEntry);
            }
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryCleanUp(NotificationEntry notificationEntry) {
            MediaCoordinator.this.mIconsState.remove(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryInit(NotificationEntry notificationEntry) {
            if (Flags.notificationsBackgroundIcons()) {
                return;
            }
            MediaCoordinator.this.mIconsState.put(notificationEntry, 0);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
        public void onEntryUpdated(NotificationEntry notificationEntry) {
            if (((Integer) MediaCoordinator.this.mIconsState.getOrDefault(notificationEntry, 0)).intValue() == 2) {
                MediaCoordinator.this.mIconsState.put(notificationEntry, 0);
            }
            if (Flags.notificationsBackgroundIcons() && MediaDataManager.isMediaNotification(notificationEntry.getSbn())) {
                MediaCoordinator.this.inflateOrUpdateIcons(notificationEntry);
            }
        }
    };

    public MediaCoordinator(MediaFeatureFlag mediaFeatureFlag, IStatusBarService iStatusBarService, IconManager iconManager) {
        this.mIsMediaFeatureEnabled = Boolean.valueOf(mediaFeatureFlag.getEnabled());
        this.mStatusBarService = iStatusBarService;
        this.mIconManager = iconManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inflateOrUpdateIcons(NotificationEntry notificationEntry) {
        int iIntValue = ((Integer) this.mIconsState.getOrDefault(notificationEntry, 0)).intValue();
        if (iIntValue == 0) {
            try {
                this.mIconManager.createIcons(notificationEntry);
                this.mIconsState.put(notificationEntry, 1);
                return;
            } catch (InflationException e) {
                reportInflationError(notificationEntry, e);
                this.mIconsState.put(notificationEntry, 2);
                return;
            }
        }
        if (iIntValue != 1) {
            return;
        }
        try {
            this.mIconManager.updateIcons(notificationEntry, false);
        } catch (InflationException e2) {
            reportInflationError(notificationEntry, e2);
            this.mIconsState.put(notificationEntry, 2);
        }
    }

    private void reportInflationError(NotificationEntry notificationEntry, Exception exc) {
        try {
            StatusBarNotification sbn = notificationEntry.getSbn();
            this.mStatusBarService.onNotificationError(sbn.getPackageName(), sbn.getTag(), sbn.getId(), sbn.getUid(), sbn.getInitialPid(), exc.getMessage(), sbn.getUser().getIdentifier());
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mMediaFilter);
        notifPipeline.addCollectionListener(this.mCollectionListener);
    }
}
