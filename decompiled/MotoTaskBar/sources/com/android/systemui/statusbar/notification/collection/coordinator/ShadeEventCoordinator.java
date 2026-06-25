package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: ShadeEventCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShadeEventCoordinator implements Coordinator {
    private boolean mEntryRemoved;
    private boolean mEntryRemovedByUser;
    private final ShadeEventCoordinatorLogger mLogger;
    private final Executor mMainExecutor;
    private final ShadeEventCoordinator$mNotifCollectionListener$1 mNotifCollectionListener;
    private Runnable mNotifRemovedByUserCallback;
    private Runnable mShadeEmptiedCallback;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator$mNotifCollectionListener$1] */
    public ShadeEventCoordinator(Executor executor, ShadeEventCoordinatorLogger shadeEventCoordinatorLogger) {
        executor.getClass();
        shadeEventCoordinatorLogger.getClass();
        this.mMainExecutor = executor;
        this.mLogger = shadeEventCoordinatorLogger;
        this.mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator$mNotifCollectionListener$1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                notificationEntry.getClass();
                boolean z = true;
                this.this$0.mEntryRemoved = true;
                ShadeEventCoordinator shadeEventCoordinator = this.this$0;
                if (i != 1 && i != 3 && i != 2) {
                    z = false;
                }
                shadeEventCoordinator.mEntryRemovedByUser = z;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeRenderList(List list) {
        if (this.mEntryRemoved && list.isEmpty()) {
            this.mLogger.logShadeEmptied();
            Runnable runnable = this.mShadeEmptiedCallback;
            if (runnable != null) {
                this.mMainExecutor.execute(runnable);
            }
        }
        if (this.mEntryRemoved && this.mEntryRemovedByUser) {
            this.mLogger.logNotifRemovedByUser();
            Runnable runnable2 = this.mNotifRemovedByUserCallback;
            if (runnable2 != null) {
                this.mMainExecutor.execute(runnable2);
            }
        }
        this.mEntryRemoved = false;
        this.mEntryRemovedByUser = false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                list.getClass();
                ShadeEventCoordinator.this.onBeforeRenderList(list);
            }
        });
    }
}
