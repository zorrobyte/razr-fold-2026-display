package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifGroupController;

/* JADX INFO: loaded from: classes.dex */
public interface OnAfterRenderGroupListener {
    void onAfterRenderGroup(GroupEntry groupEntry, NotifGroupController notifGroupController);
}
