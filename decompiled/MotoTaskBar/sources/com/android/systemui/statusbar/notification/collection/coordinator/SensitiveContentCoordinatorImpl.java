package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.NotificationChannel;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* JADX INFO: compiled from: SensitiveContentCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SensitiveContentCoordinatorImpl extends Invalidator implements SensitiveContentCoordinator, DynamicPrivacyController.Listener, OnBeforeRenderListListener {
    private final DynamicPrivacyController dynamicPrivacyController;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final Runnable onSensitiveStateChanged;
    private final SensitiveContentCoordinatorImpl$screenshareSecretFilter$1 screenshareSecretFilter;
    private final SelectedUserInteractor selectedUserInteractor;
    private final SensitiveNotificationProtectionController sensitiveNotificationProtectionController;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$screenshareSecretFilter$1] */
    public SensitiveContentCoordinatorImpl(DynamicPrivacyController dynamicPrivacyController, NotificationLockscreenUserManager notificationLockscreenUserManager, SelectedUserInteractor selectedUserInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        super("SensitiveContentInvalidator");
        dynamicPrivacyController.getClass();
        notificationLockscreenUserManager.getClass();
        selectedUserInteractor.getClass();
        sensitiveNotificationProtectionController.getClass();
        this.dynamicPrivacyController = dynamicPrivacyController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.selectedUserInteractor = selectedUserInteractor;
        this.sensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
        this.onSensitiveStateChanged = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$onSensitiveStateChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.invalidateList("onSensitiveStateChanged");
            }
        };
        this.screenshareSecretFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$screenshareSecretFilter$1
            {
                super("ScreenshareSecretFilter");
            }

            public final boolean isSecret(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                NotificationChannel channel = notificationEntry.getChannel();
                if (channel != null && channel.getLockscreenVisibility() == -1) {
                    return true;
                }
                Notification notification = notificationEntry.getSbn().getNotification();
                return notification != null && notification.visibility == -1;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                notificationEntry.getClass();
                return FlagsFake.screenshareNotificationHiding() && this.this$0.sensitiveNotificationProtectionController.isSensitiveStateActive() && isSecret(notificationEntry);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onBeforeRenderList$lambda$0(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return notificationEntry.rowExists();
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        this.dynamicPrivacyController.addListener(this);
        if (FlagsFake.screenshareNotificationHiding()) {
            this.sensitiveNotificationProtectionController.registerSensitiveStateListener(this.onSensitiveStateChanged);
        }
        notifPipeline.addOnBeforeRenderListListener(this);
        notifPipeline.addPreRenderInvalidator(this);
        if (FlagsFake.screenshareNotificationHiding()) {
            notifPipeline.addFinalizeFilter(this.screenshareSecretFilter);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006b  */
    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onBeforeRenderList(java.util.List r11) {
        /*
            r10 = this;
            r11.getClass()
            boolean r0 = com.android.systemui.fake.FlagsFake.screenshareNotificationHiding()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L15
            com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController r0 = r10.sensitiveNotificationProtectionController
            boolean r0 = r0.isSensitiveStateActive()
            if (r0 == 0) goto L15
            r0 = r1
            goto L16
        L15:
            r0 = r2
        L16:
            com.android.systemui.statusbar.NotificationLockscreenUserManager r3 = r10.lockscreenUserManager
            int r3 = r3.getCurrentUserId()
            com.android.systemui.statusbar.NotificationLockscreenUserManager r4 = r10.lockscreenUserManager
            boolean r4 = r4.isLockscreenPublicMode(r3)
            if (r4 == 0) goto L2c
            com.android.systemui.statusbar.NotificationLockscreenUserManager r5 = r10.lockscreenUserManager
            boolean r5 = r5.userAllowsPrivateNotificationsInPublic(r3)
            if (r5 == 0) goto L2e
        L2c:
            if (r0 == 0) goto L30
        L2e:
            r0 = r1
            goto L31
        L30:
            r0 = r2
        L31:
            com.android.systemui.statusbar.notification.DynamicPrivacyController r5 = r10.dynamicPrivacyController
            boolean r5 = r5.isDynamicallyUnlocked()
            kotlin.sequences.Sequence r11 = com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt.access$extractAllRepresentativeEntries(r11)
            com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$$ExternalSyntheticLambda0 r6 = new com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$$ExternalSyntheticLambda0
            r6.<init>()
            kotlin.sequences.Sequence r11 = kotlin.sequences.SequencesKt.filter(r11, r6)
            java.util.Iterator r11 = r11.iterator()
        L48:
            boolean r6 = r11.hasNext()
            if (r6 == 0) goto Lb4
            java.lang.Object r6 = r11.next()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r6
            android.service.notification.StatusBarNotification r7 = r6.getSbn()
            android.os.UserHandle r7 = r7.getUser()
            int r7 = r7.getIdentifier()
            if (r4 != 0) goto L6d
            com.android.systemui.statusbar.NotificationLockscreenUserManager r8 = r10.lockscreenUserManager
            boolean r8 = r8.isLockscreenPublicMode(r7)
            if (r8 == 0) goto L6b
            goto L6d
        L6b:
            r7 = r2
            goto L7e
        L6d:
            if (r5 != 0) goto L71
            r7 = r1
            goto L7e
        L71:
            if (r7 != r3) goto L74
            goto L6b
        L74:
            r8 = -1
            if (r7 != r8) goto L78
            goto L6b
        L78:
            com.android.systemui.statusbar.NotificationLockscreenUserManager r8 = r10.lockscreenUserManager
            boolean r7 = r8.needsSeparateWorkChallenge(r7)
        L7e:
            boolean r8 = com.android.systemui.fake.FlagsFake.screenshareNotificationHiding()
            if (r8 == 0) goto L8e
            com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController r8 = r10.sensitiveNotificationProtectionController
            boolean r8 = r8.shouldProtectNotification(r6)
            if (r8 == 0) goto L8e
            r8 = r1
            goto L8f
        L8e:
            r8 = r2
        L8f:
            com.android.systemui.statusbar.NotificationLockscreenUserManager r9 = r10.lockscreenUserManager
            boolean r9 = r9.needsRedaction(r6)
            if (r7 == 0) goto L9a
            if (r9 == 0) goto L9a
            goto L9c
        L9a:
            if (r8 == 0) goto L9e
        L9c:
            r7 = r1
            goto L9f
        L9e:
            r7 = r2
        L9f:
            r6.setSensitive(r7, r0)
            boolean r7 = com.android.systemui.fake.FlagsFake.screenshareNotificationHiding()
            if (r7 == 0) goto L48
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r6 = r6.getRow()
            if (r6 == 0) goto L48
            r7 = r8 ^ 1
            r6.setPublicExpanderVisible(r7)
            goto L48
        Lb4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl.onBeforeRenderList(java.util.List):void");
    }
}
