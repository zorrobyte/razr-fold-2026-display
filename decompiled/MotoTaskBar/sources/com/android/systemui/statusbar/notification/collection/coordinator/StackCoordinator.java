package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.Flags;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.NotifStats;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.RenderNotificationListInteractor;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: compiled from: StackCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class StackCoordinator implements Coordinator {
    private final ActiveNotificationsInteractor activeNotificationsInteractor;
    private final GroupExpansionManagerImpl groupExpansionManagerImpl;
    private final RenderNotificationListInteractor renderListInteractor;
    private final SensitiveNotificationProtectionController sensitiveNotificationProtectionController;

    public StackCoordinator(GroupExpansionManagerImpl groupExpansionManagerImpl, RenderNotificationListInteractor renderNotificationListInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController) {
        groupExpansionManagerImpl.getClass();
        renderNotificationListInteractor.getClass();
        activeNotificationsInteractor.getClass();
        sensitiveNotificationProtectionController.getClass();
        this.groupExpansionManagerImpl = groupExpansionManagerImpl;
        this.renderListInteractor = renderNotificationListInteractor;
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        this.sensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
    }

    private final NotifStats calculateNotifStats(List list) {
        boolean z = FlagsFake.screenshareNotificationHiding() && Flags.screenshareNotificationHidingBugFix() && this.sensitiveNotificationProtectionController.isSensitiveStateActive();
        Iterator it = list.iterator();
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            NotifSection section = listEntry.getSection();
            if (section == null) {
                throw new IllegalStateException(("Null section for " + listEntry.getKey()).toString());
            }
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry == null) {
                throw new IllegalStateException(("Null notif entry for " + listEntry.getKey()).toString());
            }
            boolean z6 = section.getBucket() == 6;
            boolean z7 = !z && representativeEntry.isClearable();
            if (z6 && z7) {
                z5 = true;
            } else if (z6 && !z7) {
                z4 = true;
            } else if (!z6 && z7) {
                z3 = true;
            } else if (!z6 && !z7) {
                z2 = true;
            }
        }
        return new NotifStats(list.size(), z2, z3, z4, z5);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addOnAfterRenderListListener(new OnAfterRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener
            public final void onAfterRenderList(List list, NotifStackController notifStackController) {
                list.getClass();
                notifStackController.getClass();
                StackCoordinator.this.onAfterRenderList(list, notifStackController);
            }
        });
        this.groupExpansionManagerImpl.attach(notifPipeline);
    }

    public final void onAfterRenderList(List list, NotifStackController notifStackController) {
        list.getClass();
        notifStackController.getClass();
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("StackCoordinator.onAfterRenderList");
        }
        try {
            NotifStats notifStatsCalculateNotifStats = calculateNotifStats(list);
            if (Flags.notificationsFooterViewRefactor()) {
                this.activeNotificationsInteractor.setNotifStats(notifStatsCalculateNotifStats);
            } else {
                notifStackController.setNotifStats(notifStatsCalculateNotifStats);
            }
            if (Flags.notificationsFooterViewRefactor()) {
                this.renderListInteractor.setRenderedList(list);
            }
            Unit unit = Unit.INSTANCE;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
