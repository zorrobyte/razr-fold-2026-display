package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Handler;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.InternalNotifUpdater;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RemoteInputCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputCoordinator implements Coordinator, NotificationRemoteInputManager.RemoteInputListener, Dumpable {
    private final NotifCollectionListener mCollectionListener;
    private final Handler mMainHandler;
    private InternalNotifUpdater mNotifUpdater;
    private final NotificationRemoteInputManager mNotificationRemoteInputManager;
    private final RemoteInputNotificationRebuilder mRebuilder;
    private final RemoteInputActiveExtender mRemoteInputActiveExtender;
    private final RemoteInputHistoryExtender mRemoteInputHistoryExtender;
    private final List mRemoteInputLifetimeExtenders;
    private final SmartReplyController mSmartReplyController;
    private final SmartReplyHistoryExtender mSmartReplyHistoryExtender;

    /* JADX INFO: compiled from: RemoteInputCoordinator.kt */
    public final class RemoteInputActiveExtender extends SelfTrackingLifetimeExtender {
        public RemoteInputActiveExtender() {
            super("RemoteInputCoordinator", "RemoteInputActive", RemoteInputCoordinatorKt.getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            return RemoteInputCoordinator.this.mNotificationRemoteInputManager.isRemoteInputActive(notificationEntry);
        }
    }

    /* JADX INFO: compiled from: RemoteInputCoordinator.kt */
    public final class RemoteInputHistoryExtender extends SelfTrackingLifetimeExtender {
        public RemoteInputHistoryExtender() {
            super("RemoteInputCoordinator", "RemoteInputHistory", RemoteInputCoordinatorKt.getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            StatusBarNotification statusBarNotificationRebuildForRemoteInputReply = RemoteInputCoordinator.this.mRebuilder.rebuildForRemoteInputReply(notificationEntry);
            statusBarNotificationRebuildForRemoteInputReply.getClass();
            notificationEntry.onRemoteInputInserted();
            InternalNotifUpdater internalNotifUpdater = RemoteInputCoordinator.this.mNotifUpdater;
            if (internalNotifUpdater == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mNotifUpdater");
                internalNotifUpdater = null;
            }
            internalNotifUpdater.onInternalNotificationUpdate(statusBarNotificationRebuildForRemoteInputReply, "Extending lifetime of notification with remote input");
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            return RemoteInputCoordinator.this.mNotificationRemoteInputManager.shouldKeepForRemoteInputHistory(notificationEntry);
        }
    }

    /* JADX INFO: compiled from: RemoteInputCoordinator.kt */
    public final class SmartReplyHistoryExtender extends SelfTrackingLifetimeExtender {
        public SmartReplyHistoryExtender() {
            super("RemoteInputCoordinator", "SmartReplyHistory", RemoteInputCoordinatorKt.getDEBUG(), RemoteInputCoordinator.this.mMainHandler);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onCanceledLifetimeExtension(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            StatusBarNotification statusBarNotificationRebuildForCanceledSmartReplies = RemoteInputCoordinator.this.mRebuilder.rebuildForCanceledSmartReplies(notificationEntry);
            statusBarNotificationRebuildForCanceledSmartReplies.getClass();
            RemoteInputCoordinator.this.mSmartReplyController.stopSending(notificationEntry);
            InternalNotifUpdater internalNotifUpdater = RemoteInputCoordinator.this.mNotifUpdater;
            if (internalNotifUpdater == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mNotifUpdater");
                internalNotifUpdater = null;
            }
            internalNotifUpdater.onInternalNotificationUpdate(statusBarNotificationRebuildForCanceledSmartReplies, "Extending lifetime of notification with smart reply");
        }

        @Override // com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender
        public boolean queryShouldExtendLifetime(NotificationEntry notificationEntry) {
            notificationEntry.getClass();
            return RemoteInputCoordinator.this.mNotificationRemoteInputManager.shouldKeepForSmartReplyHistory(notificationEntry);
        }
    }

    public RemoteInputCoordinator(DumpManager dumpManager, RemoteInputNotificationRebuilder remoteInputNotificationRebuilder, NotificationRemoteInputManager notificationRemoteInputManager, Handler handler, SmartReplyController smartReplyController) {
        dumpManager.getClass();
        remoteInputNotificationRebuilder.getClass();
        notificationRemoteInputManager.getClass();
        handler.getClass();
        smartReplyController.getClass();
        this.mRebuilder = remoteInputNotificationRebuilder;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mMainHandler = handler;
        this.mSmartReplyController = smartReplyController;
        RemoteInputHistoryExtender remoteInputHistoryExtender = new RemoteInputHistoryExtender();
        this.mRemoteInputHistoryExtender = remoteInputHistoryExtender;
        SmartReplyHistoryExtender smartReplyHistoryExtender = new SmartReplyHistoryExtender();
        this.mSmartReplyHistoryExtender = smartReplyHistoryExtender;
        RemoteInputActiveExtender remoteInputActiveExtender = new RemoteInputActiveExtender();
        this.mRemoteInputActiveExtender = remoteInputActiveExtender;
        this.mRemoteInputLifetimeExtenders = CollectionsKt.listOf((Object[]) new SelfTrackingLifetimeExtender[]{remoteInputHistoryExtender, smartReplyHistoryExtender, remoteInputActiveExtender});
        dumpManager.registerDumpable(this);
        this.mCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator$mCollectionListener$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                notificationEntry.getClass();
                if (RemoteInputCoordinatorKt.getDEBUG()) {
                    Log.d("RemoteInputCoordinator", "mCollectionListener.onEntryRemoved(entry=" + notificationEntry.getKey() + ")");
                }
                this.this$0.mSmartReplyController.stopSending(notificationEntry);
                if (i == 1 || i == 2) {
                    this.this$0.mNotificationRemoteInputManager.cleanUpRemoteInputForUserRemoval(notificationEntry);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
                notificationEntry.getClass();
                if (RemoteInputCoordinatorKt.getDEBUG()) {
                    Log.d("RemoteInputCoordinator", "mCollectionListener.onEntryUpdated(entry=" + notificationEntry.getKey() + ", fromSystem=" + z + ")");
                }
                if (z) {
                    InternalNotifUpdater internalNotifUpdater = null;
                    if ((notificationEntry.getSbn().getNotification().flags & 65536) <= 0) {
                        notificationEntry.remoteInputs = null;
                        return;
                    }
                    if (this.this$0.mNotificationRemoteInputManager.shouldKeepForRemoteInputHistory(notificationEntry)) {
                        StatusBarNotification statusBarNotificationRebuildForRemoteInputReply = this.this$0.mRebuilder.rebuildForRemoteInputReply(notificationEntry);
                        statusBarNotificationRebuildForRemoteInputReply.getClass();
                        notificationEntry.onRemoteInputInserted();
                        InternalNotifUpdater internalNotifUpdater2 = this.this$0.mNotifUpdater;
                        if (internalNotifUpdater2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mNotifUpdater");
                        } else {
                            internalNotifUpdater = internalNotifUpdater2;
                        }
                        internalNotifUpdater.onInternalNotificationUpdate(statusBarNotificationRebuildForRemoteInputReply, "Extending lifetime of notification with remote input");
                        return;
                    }
                    if (!this.this$0.mNotificationRemoteInputManager.shouldKeepForSmartReplyHistory(notificationEntry)) {
                        StatusBarNotification statusBarNotificationRebuildWithExistingReplies = this.this$0.mRebuilder.rebuildWithExistingReplies(notificationEntry);
                        statusBarNotificationRebuildWithExistingReplies.getClass();
                        InternalNotifUpdater internalNotifUpdater3 = this.this$0.mNotifUpdater;
                        if (internalNotifUpdater3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("mNotifUpdater");
                        } else {
                            internalNotifUpdater = internalNotifUpdater3;
                        }
                        internalNotifUpdater.onInternalNotificationUpdate(statusBarNotificationRebuildWithExistingReplies, "Extending lifetime of notification that has already been lifetime extended.");
                        return;
                    }
                    StatusBarNotification statusBarNotificationRebuildForCanceledSmartReplies = this.this$0.mRebuilder.rebuildForCanceledSmartReplies(notificationEntry);
                    statusBarNotificationRebuildForCanceledSmartReplies.getClass();
                    this.this$0.mSmartReplyController.stopSending(notificationEntry);
                    InternalNotifUpdater internalNotifUpdater4 = this.this$0.mNotifUpdater;
                    if (internalNotifUpdater4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("mNotifUpdater");
                    } else {
                        internalNotifUpdater = internalNotifUpdater4;
                    }
                    internalNotifUpdater.onInternalNotificationUpdate(statusBarNotificationRebuildForCanceledSmartReplies, "Extending lifetime of notification with smart reply");
                }
            }
        };
    }

    public static /* synthetic */ void getMRemoteInputActiveExtender$annotations() {
    }

    public static /* synthetic */ void getMRemoteInputHistoryExtender$annotations() {
    }

    public static /* synthetic */ void getMSmartReplyHistoryExtender$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence) {
        if (RemoteInputCoordinatorKt.getDEBUG()) {
            Log.d("RemoteInputCoordinator", "onSmartReplySent(entry=" + notificationEntry.getKey() + ")");
        }
        StatusBarNotification statusBarNotificationRebuildForSendingSmartReply = this.mRebuilder.rebuildForSendingSmartReply(notificationEntry, charSequence);
        statusBarNotificationRebuildForSendingSmartReply.getClass();
        InternalNotifUpdater internalNotifUpdater = this.mNotifUpdater;
        if (internalNotifUpdater == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mNotifUpdater");
            internalNotifUpdater = null;
        }
        internalNotifUpdater.onInternalNotificationUpdate(statusBarNotificationRebuildForSendingSmartReply, "Adding smart reply spinner for sent");
        RemoteInputActiveExtender remoteInputActiveExtender = this.mRemoteInputActiveExtender;
        String key = notificationEntry.getKey();
        key.getClass();
        remoteInputActiveExtender.endLifetimeExtensionAfterDelay(key, 500L);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        this.mNotificationRemoteInputManager.setRemoteInputListener(this);
        notifPipeline.addNotificationLifetimeExtender(this.mRemoteInputActiveExtender);
        this.mNotifUpdater = notifPipeline.getInternalNotifUpdater("RemoteInputCoordinator");
        notifPipeline.addCollectionListener(this.mCollectionListener);
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.RemoteInputListener
    public boolean isNotificationKeptForRemoteInputHistory(String str) {
        str.getClass();
        return false;
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.RemoteInputListener
    public void onRemoteInputSent(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        if (RemoteInputCoordinatorKt.getDEBUG()) {
            Log.d("RemoteInputCoordinator", "onRemoteInputSent(entry=" + notificationEntry.getKey() + ")");
        }
        RemoteInputActiveExtender remoteInputActiveExtender = this.mRemoteInputActiveExtender;
        String key = notificationEntry.getKey();
        key.getClass();
        remoteInputActiveExtender.endLifetimeExtensionAfterDelay(key, 500L);
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.RemoteInputListener
    public void releaseNotificationIfKeptForRemoteInputHistory(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        if (RemoteInputCoordinatorKt.getDEBUG()) {
            Log.d("RemoteInputCoordinator", "releaseNotificationIfKeptForRemoteInputHistory(entry=" + notificationEntry.getKey() + ")");
        }
        RemoteInputActiveExtender remoteInputActiveExtender = this.mRemoteInputActiveExtender;
        String key = notificationEntry.getKey();
        key.getClass();
        remoteInputActiveExtender.endLifetimeExtensionAfterDelay(key, 200L);
    }

    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.RemoteInputListener
    public void setRemoteInputController(RemoteInputController remoteInputController) {
        remoteInputController.getClass();
        this.mSmartReplyController.setCallback(new SmartReplyController.Callback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator.setRemoteInputController.1
            @Override // com.android.systemui.statusbar.SmartReplyController.Callback
            public final void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence) {
                notificationEntry.getClass();
                charSequence.getClass();
                RemoteInputCoordinator.this.onSmartReplySent(notificationEntry, charSequence);
            }
        });
    }
}
