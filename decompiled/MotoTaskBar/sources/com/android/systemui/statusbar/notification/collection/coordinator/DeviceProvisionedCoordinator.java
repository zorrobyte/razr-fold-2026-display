package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.IPackageManager;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;

/* JADX INFO: loaded from: classes.dex */
public class DeviceProvisionedCoordinator implements Coordinator {
    private final DeviceProvisionedController mDeviceProvisionedController;
    private final IPackageManager mIPackageManager;
    private final NotifFilter mNotifFilter = new NotifFilter("DeviceProvisionedCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return (DeviceProvisionedCoordinator.this.mDeviceProvisionedController.isDeviceProvisioned() || DeviceProvisionedCoordinator.this.showNotificationEvenIfUnprovisioned(notificationEntry.getSbn())) ? false : true;
        }
    };
    private final DeviceProvisionedController.DeviceProvisionedListener mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator.2
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public void onDeviceProvisionedChanged() {
            DeviceProvisionedCoordinator.this.mNotifFilter.invalidateList("onDeviceProvisionedChanged");
        }
    };

    public DeviceProvisionedCoordinator(DeviceProvisionedController deviceProvisionedController, IPackageManager iPackageManager) {
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mIPackageManager = iPackageManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showNotificationEvenIfUnprovisioned(StatusBarNotification statusBarNotification) {
        return statusBarNotification.getNotification().extras.getBoolean("android.allowDuringSetup");
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mDeviceProvisionedController.addCallback(this.mDeviceProvisionedListener);
        notifPipeline.addPreGroupFilter(this.mNotifFilter);
    }
}
