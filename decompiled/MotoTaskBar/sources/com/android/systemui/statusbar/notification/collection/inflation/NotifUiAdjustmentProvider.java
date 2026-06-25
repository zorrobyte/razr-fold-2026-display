package com.android.systemui.statusbar.notification.collection.inflation;

import android.app.Notification;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.HandlerExecutor;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.settings.SecureSettings;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotifUiAdjustmentProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifUiAdjustmentProvider {
    private final ListenerSet dirtyListeners;
    private final GroupMembershipManager groupMembershipManager;
    private final Handler handler;
    private boolean isSnoozeSettingsEnabled;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private final NotificationLockscreenUserManager.NotificationStateChangedListener notifStateChangedListener;
    private final Runnable onSensitiveStateChangedListener;
    private final SectionStyleProvider sectionStyleProvider;
    private final SecureSettings secureSettings;
    private final SensitiveNotificationProtectionController sensitiveNotifProtectionController;
    private final NotifUiAdjustmentProvider$settingsObserver$1 settingsObserver;
    private final UserTracker userTracker;
    private final NotifUiAdjustmentProvider$userTrackerCallback$1 userTrackerCallback;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.settings.UserTracker$Callback, com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$userTrackerCallback$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$settingsObserver$1] */
    public NotifUiAdjustmentProvider(final Handler handler, SecureSettings secureSettings, NotificationLockscreenUserManager notificationLockscreenUserManager, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, SectionStyleProvider sectionStyleProvider, UserTracker userTracker, GroupMembershipManager groupMembershipManager) {
        handler.getClass();
        secureSettings.getClass();
        notificationLockscreenUserManager.getClass();
        sensitiveNotificationProtectionController.getClass();
        sectionStyleProvider.getClass();
        userTracker.getClass();
        groupMembershipManager.getClass();
        this.handler = handler;
        this.secureSettings = secureSettings;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.sensitiveNotifProtectionController = sensitiveNotificationProtectionController;
        this.sectionStyleProvider = sectionStyleProvider;
        this.userTracker = userTracker;
        this.groupMembershipManager = groupMembershipManager;
        this.dirtyListeners = new ListenerSet();
        ?? r2 = new UserTracker.Callback() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$userTrackerCallback$1
            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onUserChanged(int i, Context context) {
                context.getClass();
                this.this$0.updateSnoozeEnabled();
            }
        };
        this.userTrackerCallback = r2;
        userTracker.addCallback(r2, new HandlerExecutor(handler));
        this.notifStateChangedListener = new NotificationLockscreenUserManager.NotificationStateChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$notifStateChangedListener$1
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.NotificationStateChangedListener
            public final void onNotificationStateChanged() {
                Iterator<E> it = this.this$0.dirtyListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        };
        this.onSensitiveStateChangedListener = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$onSensitiveStateChangedListener$1
            @Override // java.lang.Runnable
            public final void run() {
                Iterator<E> it = this.this$0.dirtyListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        };
        this.settingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider$settingsObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                this.this$0.updateSnoozeEnabled();
                Iterator<E> it = this.this$0.dirtyListeners.iterator();
                while (it.hasNext()) {
                    ((Runnable) it.next()).run();
                }
            }
        };
    }

    private final boolean isEntryMinimized(NotificationEntry notificationEntry) {
        NotifSection section = notificationEntry.getSection();
        if (section == null) {
            throw new IllegalStateException("Entry must have a section to determine if minimized");
        }
        GroupEntry parent = notificationEntry.getParent();
        if (parent == null) {
            throw new IllegalStateException("Entry must have a parent to determine if minimized");
        }
        boolean zIsMinimizedSection = this.sectionStyleProvider.isMinimizedSection(section);
        boolean zAreEqual = Intrinsics.areEqual(parent, GroupEntry.ROOT_ENTRY);
        boolean zAreEqual2 = Intrinsics.areEqual(parent.getSummary(), notificationEntry);
        if (zIsMinimizedSection) {
            return zAreEqual || zAreEqual2;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateSnoozeEnabled() {
        this.isSnoozeSettingsEnabled = this.secureSettings.getIntForUser("show_notification_snooze", 0, -2) == 1;
    }

    public final void addDirtyListener(Runnable runnable) {
        runnable.getClass();
        if (this.dirtyListeners.isEmpty()) {
            this.lockscreenUserManager.addNotificationStateChangedListener(this.notifStateChangedListener);
            if (FlagsFake.screenshareNotificationHiding()) {
                this.sensitiveNotifProtectionController.registerSensitiveStateListener(this.onSensitiveStateChangedListener);
            }
            updateSnoozeEnabled();
            this.secureSettings.registerContentObserverForUser("show_notification_snooze", this.settingsObserver, -1);
        }
        this.dirtyListeners.addIfAbsent(runnable);
    }

    public final NotifUiAdjustment calculateAdjustment(NotificationEntry notificationEntry) {
        boolean z;
        boolean z2;
        notificationEntry.getClass();
        String key = notificationEntry.getKey();
        key.getClass();
        List<Notification.Action> smartActions = notificationEntry.getRanking().getSmartActions();
        smartActions.getClass();
        List<CharSequence> smartReplies = notificationEntry.getRanking().getSmartReplies();
        smartReplies.getClass();
        boolean zIsConversation = notificationEntry.getRanking().isConversation();
        boolean z3 = true;
        if (!this.isSnoozeSettingsEnabled || notificationEntry.isCanceled()) {
            z = false;
            z2 = false;
        } else {
            z2 = false;
            z = true;
        }
        boolean zIsEntryMinimized = isEntryMinimized(notificationEntry);
        if (!this.lockscreenUserManager.needsRedaction(notificationEntry) && (!FlagsFake.screenshareNotificationHiding() || !this.sensitiveNotifProtectionController.shouldProtectNotification(notificationEntry))) {
            z3 = z2;
        }
        return new NotifUiAdjustment(key, smartActions, smartReplies, zIsConversation, z, zIsEntryMinimized, z3, notificationEntry.hasEverBeenGroupChild(), notificationEntry.hasEverBeenGroupSummary());
    }
}
