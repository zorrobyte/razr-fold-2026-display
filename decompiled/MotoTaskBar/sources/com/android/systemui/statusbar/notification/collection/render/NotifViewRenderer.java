package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.List;

/* JADX INFO: compiled from: NotifViewRenderer.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotifViewRenderer {
    NotifGroupController getGroupController(GroupEntry groupEntry);

    NotifRowController getRowController(NotificationEntry notificationEntry);

    NotifStackController getStackController();

    default void onDispatchComplete() {
    }

    void onRenderList(List list);
}
